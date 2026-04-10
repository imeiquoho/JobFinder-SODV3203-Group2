package com.imeiquoho.jobfinder.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.imeiquoho.jobfinder.data.JobPosting

@Composable
fun JobCard(
    job: JobPosting,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioHighBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
                .padding(16.dp)
        ) {

            // HEADER
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = job.title,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.semantics { heading() }
                )

                Spacer(modifier = Modifier.weight(1f))

                JobExpandButton(
                    expanded = expanded,
                    onClick = { expanded = !expanded }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Always visible summary
            Text(
                text = job.summary,
                style = MaterialTheme.typography.bodyMedium
            )

            // 🔹 EXPANDED CONTENT
            if (expanded) {

                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(12.dp))

                Column {

                    Text(
                        text = job.company,
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Text(
                        text = job.location,
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Salary: ${job.salary}",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Requirements",
                        style = MaterialTheme.typography.titleSmall
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    job.requirements.forEach {
                        Text("• $it")
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Responsibilities",
                        style = MaterialTheme.typography.titleSmall
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    job.responsibilities.forEach {
                        Text("• $it")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = onSaveClick,
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Icon(
                            imageVector = if (job.isSaved)
                                Icons.Filled.Bookmark
                            else
                                Icons.Filled.BookmarkBorder,
                            contentDescription = if (job.isSaved)
                                "Remove saved job"
                            else
                                "Save job posting"
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = if (job.isSaved)
                                "Remove from Saved Jobs"
                            else
                                "Save Job"
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun JobExpandButton(
    expanded: Boolean,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier.size(48.dp)
    ) {
        Icon(
            imageVector = if (expanded)
                Icons.Filled.ExpandLess
            else
                Icons.Filled.ExpandMore,
            contentDescription = if (expanded)
                "Collapse job details"
            else
                "Expand job details",
            tint = MaterialTheme.colorScheme.onSurface
        )
    }
}