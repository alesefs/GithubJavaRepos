package com.alesefs.githubjavarepos.data.repository.pullRequestData

interface PullRequestRepository {
    fun getPullRequest(repositoryURL: String, repositoryName: String,pullrequestResultCallback: (result: PullRequestResult) -> Unit)
}