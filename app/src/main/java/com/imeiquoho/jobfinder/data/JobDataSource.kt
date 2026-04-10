package com.imeiquoho.jobfinder.data

object JobDataSource {

    fun getSampleJobs(): List<JobPosting> {
        return listOf(
            JobPosting(
                id = 1,
                title = "Junior Android Developer",
                company = "Prairie Tech Labs",
                location = "Calgary, AB",
                salary = "$55,000 - $68,000",
                summary = "Build and improve Android screens using Kotlin and Jetpack Compose.",
                requirements = listOf(
                    "Basic knowledge of Kotlin",
                    "Familiarity with Android Studio",
                    "Willingness to learn UI design principles"
                ),
                responsibilities = listOf(
                    "Support feature development",
                    "Work with reusable composables",
                    "Fix small UI issues and test layouts"
                ),
                isSaved = false
            ),
            JobPosting(
                id = 2,
                title = "IT Support Assistant",
                company = "Maple Business Services",
                location = "Edmonton, AB",
                salary = "$23/hour",
                summary = "Help staff with device setup, password resets, and software troubleshooting.",
                requirements = listOf(
                    "Good communication skills",
                    "Basic troubleshooting experience",
                    "Customer service mindset"
                ),
                responsibilities = listOf(
                    "Respond to support requests",
                    "Document recurring issues",
                    "Assist with laptop and software setup"
                ),
                isSaved = false
            ),
            JobPosting(
                id = 3,
                title = "Frontend Web Intern",
                company = "NorthGrid Creative",
                location = "Remote",
                salary = "Internship",
                summary = "Assist with building clean and responsive web interfaces for client projects.",
                requirements = listOf(
                    "Basic HTML and CSS knowledge",
                    "Interest in UI/UX",
                    "Willingness to learn frontend tools"
                ),
                responsibilities = listOf(
                    "Update web page sections",
                    "Test responsive layouts",
                    "Support design implementation"
                ),
                isSaved = false
            )
        )
    }
}