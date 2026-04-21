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
                summary = "Build polished Android screens with Kotlin and Jetpack Compose.",
                description = "Join a small product team building internal tools for local businesses. " +
                    "You will help shape reusable Compose components, improve accessibility, and support " +
                    "feature delivery across the Android app.",
                employmentType = "Full-time",
                workModel = "Hybrid",
                postedLabel = "Posted 2 days ago",
                tags = listOf("Compose", "Accessibility", "Entry level"),
                requirements = listOf(
                    "Comfort reading Kotlin code",
                    "Basic Android Studio workflow",
                    "Interest in modern UI patterns"
                ),
                responsibilities = listOf(
                    "Build and refine reusable composables",
                    "Test layouts across phone sizes",
                    "Fix UI bugs and support code reviews"
                )
            ),
            JobPosting(
                id = 2,
                title = "IT Support Assistant",
                company = "Maple Business Services",
                location = "Edmonton, AB",
                salary = "$23/hour",
                summary = "Support staff with everyday hardware and software issues.",
                description = "This role is ideal for someone who enjoys troubleshooting and helping people " +
                    "work through technical issues. You will handle tickets, set up devices, and keep " +
                    "simple support documentation up to date.",
                employmentType = "Part-time",
                workModel = "On-site",
                postedLabel = "Posted today",
                tags = listOf("Help desk", "Students welcome", "Office"),
                requirements = listOf(
                    "Good communication skills",
                    "Basic device troubleshooting experience",
                    "Comfort documenting recurring issues"
                ),
                responsibilities = listOf(
                    "Respond to support requests",
                    "Set up laptops and user accounts",
                    "Document common fixes for future reference"
                )
            ),
            JobPosting(
                id = 3,
                title = "Frontend Web Intern",
                company = "NorthGrid Creative",
                location = "Remote",
                salary = "Internship",
                summary = "Help build responsive web pages for client projects.",
                description = "Work with a small design-forward team shipping landing pages and campaign " +
                    "updates. This internship focuses on clean layouts, accessible markup, and practical " +
                    "feedback from designers and developers.",
                employmentType = "Internship",
                workModel = "Remote",
                postedLabel = "Posted 1 week ago",
                tags = listOf("HTML/CSS", "UI", "Portfolio building"),
                requirements = listOf(
                    "Basic HTML and CSS knowledge",
                    "Interest in responsive design",
                    "Willingness to learn frontend tooling"
                ),
                responsibilities = listOf(
                    "Update existing web sections",
                    "Test pages on multiple screen sizes",
                    "Support design implementation and QA"
                )
            ),
            JobPosting(
                id = 4,
                title = "Data Entry Clerk",
                company = "Cedar Health Group",
                location = "Airdrie, AB",
                salary = "$20/hour",
                summary = "Maintain accurate records and support basic reporting tasks.",
                description = "This position suits detail-oriented applicants who can follow established " +
                    "processes. You will organize incoming information, validate records, and support " +
                    "administrative reporting.",
                employmentType = "Part-time",
                workModel = "On-site",
                postedLabel = "Posted 3 days ago",
                tags = listOf("Admin", "Entry level", "Accuracy"),
                requirements = listOf(
                    "Strong attention to detail",
                    "Comfort using spreadsheets",
                    "Ability to follow written instructions"
                ),
                responsibilities = listOf(
                    "Enter and verify records",
                    "Review information for accuracy",
                    "Support weekly admin reporting"
                )
            ),
            JobPosting(
                id = 5,
                title = "Customer Service Representative",
                company = "WestPeak Retail",
                location = "Calgary, AB",
                salary = "$22/hour",
                summary = "Deliver friendly support and help customers resolve service questions.",
                description = "You will support customers across phone, email, and in-store follow-up. " +
                    "The team values patience, clear communication, and consistent note taking.",
                employmentType = "Part-time",
                workModel = "Hybrid",
                postedLabel = "Posted 5 days ago",
                tags = listOf("Customer support", "Communication", "Flexible shifts"),
                requirements = listOf(
                    "Strong communication skills",
                    "Patience and professionalism",
                    "Ability to work in a team environment"
                ),
                responsibilities = listOf(
                    "Answer customer questions clearly",
                    "Escalate complex concerns when needed",
                    "Keep service notes complete and accurate"
                )
            ),
            JobPosting(
                id = 6,
                title = "QA Tester Trainee",
                company = "Summit Digital",
                location = "Remote",
                salary = "$24/hour",
                summary = "Test app features, document bugs, and support release readiness.",
                description = "A good fit for someone curious about quality, checklists, and edge cases. " +
                    "You will verify UI flows, reproduce bugs, and help keep release notes organized.",
                employmentType = "Contract",
                workModel = "Remote",
                postedLabel = "Posted yesterday",
                tags = listOf("Testing", "Documentation", "Entry level"),
                requirements = listOf(
                    "Strong observation skills",
                    "Comfort writing clear bug reports",
                    "Basic understanding of app workflows"
                ),
                responsibilities = listOf(
                    "Run test cases on new builds",
                    "Capture reproducible bug steps",
                    "Support release checklists and validation"
                )
            )
        )
    }
}
