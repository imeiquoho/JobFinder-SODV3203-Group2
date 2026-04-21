package com.imeiquoho.jobfinder.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.imeiquoho.jobfinder.ui.screens.BrowseJobsScreen
import com.imeiquoho.jobfinder.ui.screens.JobDetailScreen
import com.imeiquoho.jobfinder.ui.screens.SavedJobsScreen
import com.imeiquoho.jobfinder.viewmodel.JobSection
import com.imeiquoho.jobfinder.viewmodel.JobUiState
import com.imeiquoho.jobfinder.viewmodel.JobViewModel

@Composable
fun JobFinderApp(
    viewModel: JobViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val selectedJob = uiState.selectedJob

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            JobFinderTopBar(
                uiState = uiState,
                onBackClick = viewModel::closeJobDetails,
                onSaveClick = {
                    selectedJob?.let { viewModel.onSaveToggle(it.id) }
                }
            )
        },
        bottomBar = {
            if (selectedJob == null) {
                JobFinderBottomBar(
                    currentSection = uiState.activeSection,
                    onSectionSelected = viewModel::onSectionSelected
                )
            }
        }
    ) { innerPadding ->
        when {
            selectedJob != null -> {
                JobDetailScreen(
                    job = selectedJob,
                    onSaveClick = { viewModel.onSaveToggle(selectedJob.id) },
                    modifier = Modifier.padding(innerPadding)
                )
            }

            uiState.activeSection == JobSection.Browse -> {
                BrowseJobsScreen(
                    uiState = uiState,
                    onSearchQueryChange = viewModel::onSearchQueryChange,
                    onLocationSelected = viewModel::onLocationSelected,
                    onClearFilters = viewModel::clearFilters,
                    onExpandToggle = viewModel::onExpandToggle,
                    onSaveToggle = viewModel::onSaveToggle,
                    onDetailsClick = viewModel::openJobDetails,
                    modifier = Modifier.padding(innerPadding)
                )
            }

            else -> {
                SavedJobsScreen(
                    uiState = uiState,
                    onSearchQueryChange = viewModel::onSearchQueryChange,
                    onLocationSelected = viewModel::onLocationSelected,
                    onClearFilters = viewModel::clearFilters,
                    onExpandToggle = viewModel::onExpandToggle,
                    onSaveToggle = viewModel::onSaveToggle,
                    onDetailsClick = viewModel::openJobDetails,
                    onBrowseClick = { viewModel.onSectionSelected(JobSection.Browse) },
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun JobFinderTopBar(
    uiState: JobUiState,
    onBackClick: () -> Unit,
    onSaveClick: () -> Unit
) {
    val selectedJob = uiState.selectedJob

    TopAppBar(
        title = {
            Text(
                text = if (selectedJob == null) {
                    if (uiState.activeSection == JobSection.Browse) {
                        "JobFinder"
                    } else {
                        "Saved Jobs"
                    }
                } else {
                    "Job Details"
                },
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            if (selectedJob != null) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier.testTag("back_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        },
        actions = {
            if (selectedJob != null) {
                IconButton(
                    onClick = onSaveClick,
                    modifier = Modifier.testTag("detail_save_action")
                ) {
                    Icon(
                        imageVector = Icons.Filled.Bookmark,
                        contentDescription = if (selectedJob.isSaved) {
                            "Remove saved job"
                        } else {
                            "Save job posting"
                        }
                    )
                }
            }
        }
    )
}

@Composable
private fun JobFinderBottomBar(
    currentSection: JobSection,
    onSectionSelected: (JobSection) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            shape = RoundedCornerShape(28.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 8.dp,
            shadowElevation = 12.dp
        ) {
            NavigationBar(
                containerColor = Color.Transparent
            ) {
                NavigationBarItem(
                    selected = currentSection == JobSection.Browse,
                    onClick = { onSectionSelected(JobSection.Browse) },
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = null
                        )
                    },
                    label = { Text("Browse") },
                    modifier = Modifier.testTag("browse_tab")
                )

                NavigationBarItem(
                    selected = currentSection == JobSection.Saved,
                    onClick = { onSectionSelected(JobSection.Saved) },
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.Bookmark,
                            contentDescription = null
                        )
                    },
                    label = { Text("Saved") },
                    modifier = Modifier.testTag("saved_tab")
                )
            }
        }
    }
}
