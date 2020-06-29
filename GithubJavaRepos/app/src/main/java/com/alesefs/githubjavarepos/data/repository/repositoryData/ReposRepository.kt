package com.alesefs.githubjavarepos.data.repository.repositoryData

interface ReposRepository {
    fun getRepos(
        page: Int,
        perPage: Int,
        reposResultCallback: (result: ReposResult) -> Unit
    )
}