package com.alesefs.githubjavarepos.data.repository.repositoryData

import com.alesefs.githubjavarepos.data.model.repositoryData.Repo

sealed class ReposResult {
    class Success(val repos: List<Repo>) : ReposResult()
    class ApiError(val statusCode: Int) : ReposResult()
    object ServerError : ReposResult()
}