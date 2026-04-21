package com.imeiquoho.jobfinder.data

import com.imeiquoho.jobfinder.data.local.JobDao
import com.imeiquoho.jobfinder.data.local.toEntity
import com.imeiquoho.jobfinder.data.local.toModel

interface JobRepository {
    suspend fun seedJobsIfEmpty()
    suspend fun getJobs(): List<JobPosting>
    suspend fun updateSavedState(jobId: Int, isSaved: Boolean)
}

class RoomJobRepository(
    private val jobDao: JobDao
) : JobRepository {

    override suspend fun seedJobsIfEmpty() {
        if (jobDao.getJobCount() == 0) {
            jobDao.insertAll(
                JobDataSource.getSampleJobs().map { it.toEntity() }
            )
        }
    }

    override suspend fun getJobs(): List<JobPosting> {
        return jobDao.getAllJobs().map { it.toModel() }
    }

    override suspend fun updateSavedState(jobId: Int, isSaved: Boolean) {
        jobDao.updateSavedState(jobId, isSaved)
    }
}
