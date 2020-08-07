package com.alesefs.githubjavarepos.data.model.repositoryData

import com.alesefs.githubjavarepos.data.response.ListUserNamesResponse
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.net.URL

@JsonClass(generateAdapter = true)
data class Items (
    @Json(name = "name") val name: String?,
    @Json(name = "full_name") val full_name: String?,
    @Json(name = "owner") val owner: Ownered,
    @Json(name = "url") val url: String?,
    @Json(name = "html_url") val html_url: String?,
    @Json(name = "description") val description: String?,
    @Json(name = "stargazers_count") val stargazers_count: Int?,
    @Json(name = "forks_count") val forks_count: Int?
) {
    fun getitemModel() = Repo(
        titleRepo = this.full_name,
        descriptionRepo = this.description,
        countForks = this.forks_count,
        countStars = this.stargazers_count,
        userName = this.owner.login,
        realName = this.name,
        picture = this.owner.avatar_url,
        userNameDisplay = this.owner.url
    )
}