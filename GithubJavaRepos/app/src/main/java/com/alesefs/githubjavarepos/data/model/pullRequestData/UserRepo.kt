package com.alesefs.githubjavarepos.data.model.pullRequestData

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserRepo (
    @Json(name = "full_name") val full_name: String?,
    @Json(name = "description") val description: String?
)
