package com.imeiquoho.jobfinder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.imeiquoho.jobfinder.data.RoomJobRepository
import com.imeiquoho.jobfinder.data.local.JobDatabase
import com.imeiquoho.jobfinder.ui.JobFinderApp
import com.imeiquoho.jobfinder.ui.theme.JobFinderTheme
import com.imeiquoho.jobfinder.viewmodel.JobViewModel
import com.imeiquoho.jobfinder.viewmodel.JobViewModelFactory

class MainActivity : ComponentActivity() {

    private val jobViewModel: JobViewModel by viewModels {
        JobViewModelFactory(
            repository = RoomJobRepository(
                JobDatabase.getDatabase(applicationContext).jobDao()
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            JobFinderTheme {
                JobFinderApp(viewModel = jobViewModel)
            }
        }
    }
}
