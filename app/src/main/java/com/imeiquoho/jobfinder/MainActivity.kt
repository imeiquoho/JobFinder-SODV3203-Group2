package com.imeiquoho.jobfinder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import com.imeiquoho.jobfinder.ui.screens.JobListScreen
import com.imeiquoho.jobfinder.viewmodel.JobViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val jobViewModel = JobViewModel()

        setContent {
            MaterialTheme {
                JobListScreen(viewModel = jobViewModel)
            }
        }
    }
}