package com.alesefs.githubjavarepos.data.repository.pullRequestData

import com.alesefs.githubjavarepos.data.model.pullRequestData.PullRequest

sealed class PullRequestResult {
    class Success(val pulls: List<PullRequest>) : PullRequestResult()
    class ApiError(val statusCode: Int) : PullRequestResult()
    object ServerError : PullRequestResult()
}