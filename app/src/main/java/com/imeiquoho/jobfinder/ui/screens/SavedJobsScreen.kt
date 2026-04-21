package com.imeiquoho.jobfinder.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.imeiquoho.jobfinder.ui.components.JobCard
import com.imeiquoho.jobfinder.ui.components.JobsFilterPanel
import com.imeiquoho.jobfinder.viewmodel.JobUiState

@Composable
fun SavedJobsScreen(
    uiState: JobUiState,
    onSearchQueryChange: (String) -> Unit,
    onLocationSelected: (String) -> Unit,
    onClearFilters: () -> Unit,
    onExpandToggle: (Int) -> Unit,
    onSaveToggle: (Int) -> Unit,
    onDetailsClick: (Int) -> Unit,
    onBrowseClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("saved_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            ScreenSummaryCard(
                title = "Saved jobs",
                supportingText = "Keep track of the roles you want to review later.",
                primaryValue = uiState.savedJobsCount.toString(),
                primaryLabel = "Saved total",
                secondaryValue = uiState.savedJobs.size.toString(),
                secondaryLabel = "Visible now"
            )
        }

        item {
            JobsFilterPanel(
                searchQuery = uiState.searchQuery,
                selectedLocation = uiState.selectedLocation,
                availableLocations = uiState.availableLocations,
                resultCount = uiState.savedJobs.size,
                onSearchQueryChange = onSearchQueryChange,
                onLocationSelected = onLocationSelected,
                onClearFilters = onClearFilters
            )
        }

        if (uiState.savedJobs.isEmpty()) {
            item {
                EmptyMessageCard(
                    title = if (uiState.savedJobsCount == 0) {
                        "No saved jobs yet"
                    } else {
                        "No saved jobs match these filters"
                    },
                    description = if (uiState.savedJobsCount == 0) {
                        "Save a role from the browse screen to build your shortlist."
                    } else {
                        "Try another keyword or location to find the saved role you want."
                    },
                    modifier = Modifier.testTag("saved_empty_state")
                )
            }

            item {
                FilledTonalButton(
                    onClick = onBrowseClick,
                    modifier = Modifier.testTag("browse_roles_button")
                ) {
                    Text("Browse roles")
                }
            }
        } else {
            item {
                Text(
                    text = "Your shortlist",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            items(
                items = uiState.savedJobs,
                key = { job -> job.id }
            ) { job ->
                JobCard(
                    job = job,
                    onExpandClick = { onExpandToggle(job.id) },
                    onSaveClick = { onSaveToggle(job.id) },
                    onDetailsClick = { onDetailsClick(job.id) }
                )
            }
        }
    }
}
