package com.alesefs.githubjavarepos.data.response

import com.alesefs.githubjavarepos.data.model.pullRequestData.Head
import com.alesefs.githubjavarepos.data.model.pullRequestData.PullRequest
import com.alesefs.githubjavarepos.data.model.pullRequestData.User
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.text.SimpleDateFormat
import java.util.*

@JsonClass(generateAdapter = true)
data class ListDataPullRequestResponse (
    @Json(name = "title") val title: String,
    @Json(name = "body") val body: String?,
    @Json(name = "created_at") val created_at: String,
    @Json(name = "head") val head: Head,
    @Json(name = "user") val user: User
) {
    fun getListPullRequestModel() = PullRequest (
        titlePR = this.title,
        descriptionPR = this.body,
        userName = this.head.user?.login,
        createAt = getStringToDate(this.created_at),
        picture = this.user.avatar_url
    )

    private fun getStringToDate(createdAt: String): String? {
        val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(createdAt)
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        sdf.timeZone = TimeZone.getTimeZone("CET")
        return sdf.format(date)
    }
}
