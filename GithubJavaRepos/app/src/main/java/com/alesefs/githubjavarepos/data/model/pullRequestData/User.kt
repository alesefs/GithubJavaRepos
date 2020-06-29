package com.alesefs.githubjavarepos.data.model.pullRequestData

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User (
    @Json(name = "login") val login: String,
    @Json(name = "avatar_url") val avatar_url: String,
    @Json(name = "url") val url: String
)
