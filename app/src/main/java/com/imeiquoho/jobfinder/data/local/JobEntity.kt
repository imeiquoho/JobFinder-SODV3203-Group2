package com.imeiquoho.jobfinder.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.imeiquoho.jobfinder.data.JobPosting

@Entity(tableName = "jobs")
data class JobEntity(
    @field:PrimaryKey val id: Int,
    val title: String,
    val company: String,
    val location: String,
    val salary: String,
    val summary: String,
    val description: String,
    val employmentType: String,
    val workModel: String,
    val postedLabel: String,
    val tags: String,
    val requirements: String,
    val responsibilities: String,
    val isSaved: Boolean
)

private const val LIST_SEPARATOR = "||"

fun JobPosting.toEntity(): JobEntity {
    return JobEntity(
        id = id,
        title = title,
        company = company,
        location = location,
        salary = salary,
        summary = summary,
        description = description,
        employmentType = employmentType,
        workModel = workModel,
        postedLabel = postedLabel,
        tags = tags.joinToString(LIST_SEPARATOR),
        requirements = requirements.joinToString(LIST_SEPARATOR),
        responsibilities = responsibilities.joinToString(LIST_SEPARATOR),
        isSaved = isSaved
    )
}

fun JobEntity.toModel(): JobPosting {
    return JobPosting(
        id = id,
        title = title,
        company = company,
        location = location,
        salary = salary,
        summary = summary,
        description = description,
        employmentType = employmentType,
        workModel = workModel,
        postedLabel = postedLabel,
        tags = tags.toListValue(),
        requirements = requirements.toListValue(),
        responsibilities = responsibilities.toListValue(),
        isSaved = isSaved
    )
}

private fun String.toListValue(): List<String> {
    if (isBlank()) {
        return emptyList()
    }

    return split(LIST_SEPARATOR)
}
