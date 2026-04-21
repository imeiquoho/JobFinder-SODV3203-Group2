package com.imeiquoho.jobfinder.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface JobDao {

    @Query("SELECT * FROM jobs ORDER BY id")
    suspend fun getAllJobs(): List<JobEntity>

    @Query("SELECT COUNT(*) FROM jobs")
    suspend fun getJobCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(jobs: List<JobEntity>)

    @Query("UPDATE jobs SET isSaved = :isSaved WHERE id = :jobId")
    suspend fun updateSavedState(jobId: Int, isSaved: Boolean)
}
