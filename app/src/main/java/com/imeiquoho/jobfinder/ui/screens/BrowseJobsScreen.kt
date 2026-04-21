package com.imeiquoho.jobfinder.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.imeiquoho.jobfinder.ui.components.JobCard
import com.imeiquoho.jobfinder.ui.components.JobsFilterPanel
import com.imeiquoho.jobfinder.viewmodel.JobUiState

@Composable
fun BrowseJobsScreen(
    uiState: JobUiState,
    onSearchQueryChange: (String) -> Unit,
    onLocationSelected: (String) -> Unit,
    onClearFilters: () -> Unit,
    onExpandToggle: (Int) -> Unit,
    onSaveToggle: (Int) -> Unit,
    onDetailsClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("browse_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            ScreenSummaryCard(
                title = "Browse roles",
                supportingText = "Student-friendly and entry-level opportunities in a clean, accessible layout.",
                primaryValue = uiState.totalJobsCount.toString(),
                primaryLabel = "Open roles",
                secondaryValue = uiState.savedJobsCount.toString(),
                secondaryLabel = "Saved jobs"
            )
        }

        item {
            JobsFilterPanel(
                searchQuery = uiState.searchQuery,
                selectedLocation = uiState.selectedLocation,
                availableLocations = uiState.availableLocations,
                resultCount = uiState.browseJobs.size,
                onSearchQueryChange = onSearchQueryChange,
                onLocationSelected = onLocationSelected,
                onClearFilters = onClearFilters
            )
        }

        if (uiState.browseJobs.isEmpty()) {
            item {
                EmptyMessageCard(
                    title = "No roles match your search",
                    description = "Try a different keyword or clear the location filter to see more results."
                )
            }
        } else {
            items(
                items = uiState.browseJobs,
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

@Composable
internal fun ScreenSummaryCard(
    title: String,
    supportingText: String,
    primaryValue: String,
    primaryLabel: String,
    secondaryValue: String,
    secondaryLabel: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .testTag("browse_roles_title")
                    .semantics { heading() }
            )

            Text(
                text = supportingText,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp)
            )

            Row(
                modifier = Modifier.padding(top = 18.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                StatColumn(
                    value = primaryValue,
                    label = primaryLabel,
                    modifier = Modifier.testTag("available_jobs_count")
                )
                StatColumn(
                    value = secondaryValue,
                    label = secondaryLabel,
                    modifier = Modifier.testTag("saved_jobs_count")
                )
            }
        }
    }
}

@Composable
private fun StatColumn(
    value: String,
    label: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = value,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
internal fun EmptyMessageCard(
    title: String,
    description: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}
