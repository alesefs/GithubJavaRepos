package com.alesefs.githubjavarepos.data.repository.pullRequestData

import com.alesefs.githubjavarepos.data.ApiServices
import com.alesefs.githubjavarepos.data.model.pullRequestData.PullRequest
import com.alesefs.githubjavarepos.data.response.ListDataPullRequestResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class PullRequestAPIDataSource: PullRequestRepository {
    override fun getPullRequest(
        repositoryURL: String,
        repositoryName: String,
        pullrequestResultCallback: (result: PullRequestResult) -> Unit
    ) {
        ApiServices.services.getListPullRequests(repositoryURL, repositoryName).enqueue(object:
            Callback<List<ListDataPullRequestResponse>> {
            override fun onResponse(
                call: Call<List<ListDataPullRequestResponse>>,
                response: Response<List<ListDataPullRequestResponse>>
            ) {
                when {
                    response.isSuccessful -> {
                        val pRequests: MutableList<PullRequest> = mutableListOf()

                        response.body()?.let { pullReqResponse ->
                            for (results in pullReqResponse) {
                                val pullRequest = results.getListPullRequestModel()
                                pRequests.add(pullRequest)
                            }
                        }

                        pullrequestResultCallback(PullRequestResult.Success(pRequests))
                    } else -> {
                        pullrequestResultCallback(PullRequestResult.ApiError(response.code()))
                    }
                }
            }

            override fun onFailure(call: Call<List<ListDataPullRequestResponse>>, t: Throwable) {
                pullrequestResultCallback(PullRequestResult.ServerError)
            }
        })
    }
}