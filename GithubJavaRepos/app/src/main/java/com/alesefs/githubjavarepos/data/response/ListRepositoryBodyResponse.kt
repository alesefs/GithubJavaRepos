package com.alesefs.githubjavarepos.data.response

import com.alesefs.githubjavarepos.data.model.repositoryData.Items
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ListRepositoryBodyResponse (
    @Json(name = "total_count") val total_count: Int,
    @Json(name = "incomplete_results") val incomplete_results: Boolean,
    @Json(name = "items") val items: List<Items>
)