package com.alesefs.githubjavarepos.data.model.repositoryData

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Ownered (
    @Json(name = "login") val login: String?,
    @Json(name = "avatar_url") val avatar_url: String?,
    @Json(name = "html_url") val html_url: String?,
    @Json(name = "name") val name: String?,
    @Json(name = "url") val url: String?
)
