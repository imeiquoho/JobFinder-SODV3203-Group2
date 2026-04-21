package com.imeiquoho.jobfinder

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.imeiquoho.jobfinder.data.JobDataSource
import com.imeiquoho.jobfinder.data.JobPosting
import com.imeiquoho.jobfinder.data.JobRepository
import com.imeiquoho.jobfinder.ui.JobFinderApp
import com.imeiquoho.jobfinder.ui.theme.JobFinderTheme
import com.imeiquoho.jobfinder.viewmodel.JobViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class JobFinderInstrumentedTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Before
    fun setUp() {
        composeRule.setContent {
            JobFinderTheme {
                JobFinderApp(viewModel = JobViewModel(FakeJobRepository()))
            }
        }
    }

    @Test
    fun browseScreenLoads() {
        composeRule.onNodeWithTag("browse_screen").assertIsDisplayed()
    }

    @Test
    fun searchFieldDisplays() {
        composeRule.onNodeWithTag("search_field").assertIsDisplayed()
    }

    @Test
    fun locationChipDisplays() {
        composeRule.onNodeWithTag("location_chip_all_locations").assertIsDisplayed()
    }

    @Test
    fun jobCardDisplaysOnBrowseScreen() {
        composeRule.onNodeWithTag("job_card_1").assertIsDisplayed()
    }

    @Test
    fun expandButtonShowsPreviewContent() {
        composeRule.onNodeWithTag("expand_button_1").performClick()

        composeRule.onNodeWithText("About this role").assertIsDisplayed()
    }

    @Test
    fun detailsButtonOpensJobDetailsScreen() {
        composeRule.onNodeWithTag("details_button_1").performClick()

        composeRule.onNodeWithTag("job_detail_screen").assertIsDisplayed()
        composeRule.onNodeWithTag("job_detail_title").assertIsDisplayed()
    }

    @Test
    fun backButtonReturnsToBrowseScreen() {
        composeRule.onNodeWithTag("details_button_1").performClick()
        composeRule.onNodeWithTag("back_button").performClick()

        composeRule.onNodeWithTag("browse_screen").assertIsDisplayed()
    }

    @Test
    fun searchFiltersBrowseResults() {
        composeRule.onNodeWithTag("search_field").performTextInput("android")

        composeRule.onNodeWithText("Junior Android Developer").assertIsDisplayed()
    }

    @Test
    fun savedTabOpensEmptyStateBeforeSaving() {
        composeRule.onNodeWithTag("saved_tab").performClick()

        composeRule.onNodeWithTag("saved_empty_state").assertIsDisplayed()
    }

    @Test
    fun savingFromDetailsShowsRoleInSavedScreen() {
        composeRule.onNodeWithTag("details_button_1").performClick()
        composeRule.onNodeWithTag("detail_save_button_1").performClick()
        composeRule.onNodeWithTag("back_button").performClick()
        composeRule.onNodeWithTag("saved_tab").performClick()

        composeRule.onNodeWithText("Junior Android Developer").assertIsDisplayed()
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
