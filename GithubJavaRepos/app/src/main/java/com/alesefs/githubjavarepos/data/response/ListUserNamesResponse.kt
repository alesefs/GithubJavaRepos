package com.alesefs.githubjavarepos.data.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ListUserNamesResponse (
    @Json(name = "login") val login: String,
    @Json(name = "name") val name: String
)
