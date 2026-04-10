package com.imeiquoho.jobfinder.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.imeiquoho.jobfinder.data.JobPosting

@Composable
fun JobCard(
    job: JobPosting,
    onExpandClick: () -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = job.title, style = MaterialTheme.typography.titleMedium)
                    Text(text = job.company, style = MaterialTheme.typography.bodyMedium)
                    Text(text = job.location, style = MaterialTheme.typography.bodySmall)
                }

                IconButton(onClick = onSaveClick) {
                    Icon(
                        imageVector = if (job.isSaved) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                        contentDescription = "Save Job"
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = job.summary, style = MaterialTheme.typography.bodyMedium)

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(onClick = onExpandClick) {
                Icon(
                    imageVector = if (job.isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = "Expand"
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(if (job.isExpanded) "Hide Details" else "View Details")
            }

            if (job.isExpanded) {
                Spacer(modifier = Modifier.height(8.dp))

                Text("Requirements:", style = MaterialTheme.typography.titleSmall)
                job.requirements.forEach {
                    Text("• $it", style = MaterialTheme.typography.bodySmall)
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text("Responsibilities:", style = MaterialTheme.typography.titleSmall)
                job.responsibilities.forEach {
                    Text("• $it", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}