package com.alesefs.githubjavarepos.data.repository.repositoryData

import com.alesefs.githubjavarepos.data.ApiServices
import com.alesefs.githubjavarepos.data.model.repositoryData.Repo
import com.alesefs.githubjavarepos.data.response.ListRepositoryBodyResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryAPIDataSource :
    ReposRepository {
    val order: String = "desc"
    val query: String = "language:Java"
    val sort: String = "stars"

    override fun getRepos(
        page: Int,
        perPage: Int,
        reposResultCallback: (result: ReposResult) -> Unit
    ) {
        ApiServices.services.getListRepositories(query, sort, order, page, perPage).enqueue(object:
            Callback<ListRepositoryBodyResponse> {
            override fun onResponse(
                call: Call<ListRepositoryBodyResponse>,
                response: Response<ListRepositoryBodyResponse>
            ) {
                when {
                    response.isSuccessful -> {
                        val repos: MutableList<Repo> = mutableListOf()
//                        val start = ((page) * perPage) + 1
//                        val end = (page + 1) * perPage
//                        for (i in start..end) {
                            response.body()?.let { repoBodyResponse ->
                                for (results in repoBodyResponse.items) {
                                    val repo = results.getitemModel()
                                    repos.add(repo)
                                }
                            }
//                        }
                        reposResultCallback(ReposResult.Success(repos))
                    } else -> reposResultCallback(ReposResult.ApiError(response.code()))
                }
            }

            override fun onFailure(call: Call<ListRepositoryBodyResponse>, t: Throwable) {
                reposResultCallback(ReposResult.ServerError)
            }
        })
    }
}