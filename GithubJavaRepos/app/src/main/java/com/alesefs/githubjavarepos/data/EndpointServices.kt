package com.alesefs.githubjavarepos.data

import com.alesefs.githubjavarepos.data.response.ListDataPullRequestResponse
import com.alesefs.githubjavarepos.data.response.ListRepositoryBodyResponse
import com.alesefs.githubjavarepos.data.response.ListUserNamesResponse
import retrofit2.Call
import retrofit2.http.*

interface EndpointServices {
    @GET("search/repositories")
    fun getListRepositories(
        @Query("q") query: String,
        @Query("sort") sort: String,
        @Query("order") order: String,
        @Query("page") page: Int,
        @Query("per_page") per_page: Int
    ): Call<ListRepositoryBodyResponse>

    @GET("repos/{user}/{repo}/pulls")
    fun getListPullRequests(
        @Path("user") user: String,
        @Path("repo") repo: String
    ): Call<List<ListDataPullRequestResponse>>

    @GET("users/{login}")
    fun getUserNameRequests(
        @Path("login") login: String
    ): Call<ListUserNamesResponse>
}