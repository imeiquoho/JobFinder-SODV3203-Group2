package com.imeiquoho.jobfinder.viewmodel

import com.imeiquoho.jobfinder.data.JobPosting

data class JobUiState(
    val jobs: List<JobPosting> = emptyList(),
    val savedJobsCount: Int = 0
)

