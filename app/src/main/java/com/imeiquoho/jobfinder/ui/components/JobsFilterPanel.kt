package com.imeiquoho.jobfinder.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.imeiquoho.jobfinder.viewmodel.JobUiState

@Composable
fun JobsFilterPanel(
    searchQuery: String,
    selectedLocation: String,
    availableLocations: List<String>,
    resultCount: Int,
    onSearchQueryChange: (String) -> Unit,
    onLocationSelected: (String) -> Unit,
    onClearFilters: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isFiltered = searchQuery.isNotBlank() || selectedLocation != JobUiState.ALL_LOCATIONS

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            modifier = Modifier
                .fillMaxWidth()
                .testTag("search_field"),
            singleLine = true,
            label = { Text("Search roles") },
            placeholder = { Text("Role, company, skill, or city") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = null
                )
            },
            trailingIcon = {
                if (searchQuery.isNotBlank()) {
                    TextButton(onClick = { onSearchQueryChange("") }) {
                        Text("Clear")
                    }
                }
            }
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$resultCount matching roles",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            androidx.compose.foundation.layout.Spacer(modifier = Modifier.weight(1f))

            if (isFiltered) {
                TextButton(
                    onClick = onClearFilters,
                    modifier = Modifier.testTag("clear_filters_button")
                ) {
                    Icon(
                        imageVector = Icons.Filled.Clear,
                        contentDescription = null
                    )
                    Text("Reset")
                }
            }
        }

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(availableLocations) { location ->
                FilterChip(
                    selected = selectedLocation == location,
                    onClick = { onLocationSelected(location) },
                    label = { Text(location) },
                    modifier = Modifier.testTag(location.toLocationChipTag())
                )
            }
        }
    }
}

private fun String.toLocationChipTag(): String {
    return buildString {
        append("location_chip_")
        append(
            lowercase()
                .replace(" ", "_")
                .replace(",", "")
                .replace("/", "_")
        )
    }
}
