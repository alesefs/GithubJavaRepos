package com.alesefs.githubjavarepos.data.model.repositoryData

data class Repo (
    val titleRepo: String?,
    val descriptionRepo: String?,
    val countForks: Int?,
    val countStars: Int?,
    val userName: String?,
    val realName: String?,
    val picture: String?,
    val userNameDisplay: String?
)