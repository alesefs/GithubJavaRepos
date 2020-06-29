package com.alesefs.githubjavarepos.data.model.pullRequestData

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Head (
    @Json(name = "label") val label: String?,
    @Json(name = "user") val user: User?,
    @Json(name = "repo") val repo: UserRepo?
)
