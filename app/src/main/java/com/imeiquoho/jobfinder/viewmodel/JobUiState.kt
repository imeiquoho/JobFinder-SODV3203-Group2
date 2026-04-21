package com.imeiquoho.jobfinder.viewmodel

import com.imeiquoho.jobfinder.data.JobPosting

enum class JobSection {
    Browse,
    Saved
}

data class JobUiState(
    val jobs: List<JobPosting> = emptyList(),
    val browseJobs: List<JobPosting> = emptyList(),
    val savedJobs: List<JobPosting> = emptyList(),
    val searchQuery: String = "",
    val selectedLocation: String = ALL_LOCATIONS,
    val availableLocations: List<String> = listOf(ALL_LOCATIONS),
    val activeSection: JobSection = JobSection.Browse,
    val selectedJobId: Int? = null
) {
    val totalJobsCount: Int
        get() = jobs.size

    val savedJobsCount: Int
        get() = jobs.count { it.isSaved }

    val selectedJob: JobPosting?
        get() = jobs.firstOrNull { it.id == selectedJobId }

    companion object {
        const val ALL_LOCATIONS = "All locations"
    }
}
