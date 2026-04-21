package com.imeiquoho.jobfinder

import com.imeiquoho.jobfinder.data.JobDataSource
import com.imeiquoho.jobfinder.data.JobPosting
import com.imeiquoho.jobfinder.data.JobRepository
import com.imeiquoho.jobfinder.viewmodel.JobSection
import com.imeiquoho.jobfinder.viewmodel.JobUiState
import com.imeiquoho.jobfinder.viewmodel.JobViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class JobFinderUnitTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun sampleJobs_containsSixListings() {
        assertEquals(6, JobDataSource.getSampleJobs().size)
    }

    @Test
    fun sampleJobs_haveUniqueIds() {
        val ids = JobDataSource.getSampleJobs().map { it.id }
        assertEquals(ids.size, ids.distinct().size)
    }

    @Test
    fun searchQuery_filtersJobsByTitle() = runTest {
        val viewModel = createViewModel()
        advanceUntilIdle()

        viewModel.onSearchQueryChange("android")

        assertEquals(1, viewModel.uiState.value.browseJobs.size)
        assertEquals("Junior Android Developer", viewModel.uiState.value.browseJobs.first().title)
    }

    @Test
    fun searchQuery_filtersJobsByCompany() = runTest {
        val viewModel = createViewModel()
        advanceUntilIdle()

        viewModel.onSearchQueryChange("northgrid")

        assertEquals(1, viewModel.uiState.value.browseJobs.size)
        assertEquals("NorthGrid Creative", viewModel.uiState.value.browseJobs.first().company)
    }

    @Test
    fun locationFilter_limitsVisibleJobs() = runTest {
        val viewModel = createViewModel()
        advanceUntilIdle()

        viewModel.onLocationSelected("Remote")

        assertTrue(viewModel.uiState.value.browseJobs.all { it.location == "Remote" })
    }

    @Test
    fun clearFilters_restoresAllLocations() = runTest {
        val viewModel = createViewModel()
        advanceUntilIdle()

        viewModel.onSearchQueryChange("android")
        viewModel.onLocationSelected("Calgary, AB")
        viewModel.clearFilters()

        assertEquals("", viewModel.uiState.value.searchQuery)
        assertEquals(JobUiState.ALL_LOCATIONS, viewModel.uiState.value.selectedLocation)
        assertEquals(6, viewModel.uiState.value.browseJobs.size)
    }

    @Test
    fun saveToggle_updatesSavedCount() = runTest {
        val viewModel = createViewModel()
        advanceUntilIdle()

        viewModel.onSaveToggle(1)
        advanceUntilIdle()
        viewModel.onSaveToggle(2)
        advanceUntilIdle()

        assertEquals(2, viewModel.uiState.value.savedJobsCount)
    }

    @Test
    fun saveToggle_addsJobToSavedList() = runTest {
        val viewModel = createViewModel()
        advanceUntilIdle()

        viewModel.onSaveToggle(3)
        advanceUntilIdle()
        viewModel.onSectionSelected(JobSection.Saved)

        assertEquals(1, viewModel.uiState.value.savedJobs.size)
        assertEquals(3, viewModel.uiState.value.savedJobs.first().id)
    }

    @Test
    fun openJobDetails_selectsJob() = runTest {
        val viewModel = createViewModel()
        advanceUntilIdle()

        viewModel.openJobDetails(4)

        assertNotNull(viewModel.uiState.value.selectedJob)
        assertEquals(4, viewModel.uiState.value.selectedJob?.id)
    }

    @Test
    fun closeJobDetails_clearsSelection() = runTest {
        val viewModel = createViewModel()
        advanceUntilIdle()

        viewModel.openJobDetails(4)
        viewModel.closeJobDetails()

        assertEquals(null, viewModel.uiState.value.selectedJobId)
    }

    @Test
    fun sectionSelection_updatesCurrentSection() = runTest {
        val viewModel = createViewModel()
        advanceUntilIdle()

        viewModel.onSectionSelected(JobSection.Saved)

        assertEquals(JobSection.Saved, viewModel.uiState.value.activeSection)
    }

    @Test
    fun expandToggle_expandsAndCollapsesRequestedJob() = runTest {
        val viewModel = createViewModel()
        advanceUntilIdle()

        viewModel.onExpandToggle(5)
        assertTrue(viewModel.uiState.value.jobs.first { it.id == 5 }.isExpanded)

        viewModel.onExpandToggle(5)
        assertFalse(viewModel.uiState.value.jobs.first { it.id == 5 }.isExpanded)
    }

    private fun createViewModel(): JobViewModel {
        return JobViewModel(FakeJobRepository())
    }
}

private class FakeJobRepository : JobRepository {
    private var jobs: List<JobPosting> = emptyList()

    override suspend fun seedJobsIfEmpty() {
        if (jobs.isEmpty()) {
            jobs = JobDataSource.getSampleJobs()
        }
    }

    override suspend fun getJobs(): List<JobPosting> {
        return jobs
    }

    override suspend fun updateSavedState(jobId: Int, isSaved: Boolean) {
        jobs = jobs.map { job ->
            if (job.id == jobId) {
                job.copy(isSaved = isSaved)
            } else {
                job
            }
        }
    }
}
