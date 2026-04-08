package com.imeiquoho.jobfinder.data

data class JobPosting(
    val id: Int,
    val title: String,
    val company: String,
    val location: String,
    val salary: String,
    val summary: String,
    val requirements: List<String>,
    val responsibilities: List<String>,
    val isSaved: Boolean = false,
    val isExpanded: Boolean = false
)

