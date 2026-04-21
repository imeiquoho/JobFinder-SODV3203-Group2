package com.imeiquoho.jobfinder.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imeiquoho.jobfinder.data.JobPosting
import com.imeiquoho.jobfinder.data.JobRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class JobViewModel(
    private val repository: JobRepository
) : ViewModel() {

    private var rawJobs: List<JobPosting> = emptyList()
    private var expandedJobIds: Set<Int> = emptySet()

    private val _uiState = MutableStateFlow(JobUiState())
    val uiState: StateFlow<JobUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.seedJobsIfEmpty()
            refreshJobs()
        }
    }

    fun onSectionSelected(section: JobSection) {
        syncState(
            activeSection = section,
            selectedJobId = null
        )
    }

    fun onSearchQueryChange(query: String) {
        syncState(searchQuery = query)
    }

    fun onLocationSelected(location: String) {
        syncState(selectedLocation = location)
    }

    fun clearFilters() {
        syncState(
            searchQuery = "",
            selectedLocation = JobUiState.ALL_LOCATIONS
        )
    }

    fun openJobDetails(jobId: Int) {
        syncState(selectedJobId = jobId)
    }

    fun closeJobDetails() {
        syncState(selectedJobId = null)
    }

    fun onExpandToggle(jobId: Int) {
        expandedJobIds = if (jobId in expandedJobIds) {
            expandedJobIds - jobId
        } else {
            expandedJobIds + jobId
        }

        syncState()
    }

    fun onSaveToggle(jobId: Int) {
        val currentJob = rawJobs.firstOrNull { it.id == jobId } ?: return

        viewModelScope.launch {
            repository.updateSavedState(jobId, !currentJob.isSaved)
            refreshJobs()
        }
    }

    private suspend fun refreshJobs() {
        rawJobs = repository.getJobs()
        syncState()
    }

    private fun syncState(
        searchQuery: String = _uiState.value.searchQuery,
        selectedLocation: String = _uiState.value.selectedLocation,
        activeSection: JobSection = _uiState.value.activeSection,
        selectedJobId: Int? = _uiState.value.selectedJobId
    ) {
        _uiState.value = buildState(
            sourceJobs = rawJobs,
            searchQuery = searchQuery,
            selectedLocation = selectedLocation,
            activeSection = activeSection,
            selectedJobId = selectedJobId
        )
    }

    private fun buildState(
        sourceJobs: List<JobPosting>,
        searchQuery: String = "",
        selectedLocation: String = JobUiState.ALL_LOCATIONS,
        activeSection: JobSection = JobSection.Browse,
        selectedJobId: Int? = null
    ): JobUiState {
        val jobs = sourceJobs.map { job ->
            job.copy(isExpanded = job.id in expandedJobIds)
        }

        val availableLocations = buildList {
            add(JobUiState.ALL_LOCATIONS)
            addAll(jobs.map { it.location }.distinct().sorted())
        }

        val browseJobs = jobs.filter { job ->
            matchesSearch(job, searchQuery) && matchesLocation(job, selectedLocation)
        }

        val savedJobs = jobs.filter { job ->
            job.isSaved && matchesSearch(job, searchQuery) && matchesLocation(job, selectedLocation)
        }

        return JobUiState(
            jobs = jobs,
            browseJobs = browseJobs,
            savedJobs = savedJobs,
            searchQuery = searchQuery,
            selectedLocation = selectedLocation,
            availableLocations = availableLocations,
            activeSection = activeSection,
            selectedJobId = selectedJobId
        )
    }

    private fun matchesSearch(job: JobPosting, query: String): Boolean {
        if (query.isBlank()) {
            return true
        }

        val normalizedQuery = query.trim().lowercase()

        return listOf(
            job.title,
            job.company,
            job.location,
            job.summary,
            job.description,
            job.employmentType,
            job.workModel
        ).any { it.lowercase().contains(normalizedQuery) } ||
            job.tags.any { it.lowercase().contains(normalizedQuery) }
    }

    private fun matchesLocation(job: JobPosting, selectedLocation: String): Boolean {
        return selectedLocation == JobUiState.ALL_LOCATIONS || job.location == selectedLocation
    }
}
