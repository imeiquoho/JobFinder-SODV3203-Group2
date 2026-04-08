package com.imeiquoho.jobfinder.viewmodel

import androidx.lifecycle.ViewModel
import com.imeiquoho.jobfinder.data.JobDataSource
import com.imeiquoho.jobfinder.data.JobPosting
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class JobViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        JobUiState(jobs = JobDataSource.getSampleJobs())
    )
    val uiState: StateFlow<JobUiState> = _uiState.asStateFlow()

    fun onExpandToggle(jobId: Int) {
        val updatedJobs = _uiState.value.jobs.map { job ->
            if (job.id == jobId) {
                job.copy(isExpanded = !job.isExpanded)
            } else {
                job
            }
        }

        _uiState.value = _uiState.value.copy(
            jobs = updatedJobs,
            savedJobsCount = countSavedJobs(updatedJobs)
        )
    }

    fun onSaveToggle(jobId: Int) {
        val updatedJobs = _uiState.value.jobs.map { job ->
            if (job.id == jobId) {
                job.copy(isSaved = !job.isSaved)
            } else {
                job
            }
        }

        _uiState.value = _uiState.value.copy(
            jobs = updatedJobs,
            savedJobsCount = countSavedJobs(updatedJobs)
        )
    }

    private fun countSavedJobs(jobs: List<JobPosting>): Int {
        return jobs.count { it.isSaved }
    }
}

