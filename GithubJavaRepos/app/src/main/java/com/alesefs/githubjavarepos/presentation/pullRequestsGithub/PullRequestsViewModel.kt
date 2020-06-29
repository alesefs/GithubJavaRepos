package com.alesefs.githubjavarepos.presentation.pullRequestsGithub

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.alesefs.githubjavarepos.R
import com.alesefs.githubjavarepos.data.model.pullRequestData.PullRequest
import com.alesefs.githubjavarepos.data.repository.pullRequestData.PullRequestRepository
import com.alesefs.githubjavarepos.data.repository.pullRequestData.PullRequestResult

class PullRequestsViewModel(private val dataSource: PullRequestRepository) : ViewModel() {

    val pullRequestLiveDate: MutableLiveData<List<PullRequest>> = MutableLiveData()
    val viewFlipperPullRequestLiveDate: MutableLiveData<Pair<Int, Int?>> = MutableLiveData()

    fun getPullRequest(repositoryURL: String, repositoryName: String) {
        dataSource.getPullRequest(repositoryURL, repositoryName) { result: PullRequestResult ->
            when(result) {
                is PullRequestResult.Success -> {
                    pullRequestLiveDate.value = result.pulls
                    viewFlipperPullRequestLiveDate.value = Pair(VIEW_FLIPPER_SHOW_PR, null)
                }
                is PullRequestResult.ApiError -> {
                    if (result.statusCode == 401) {
                        viewFlipperPullRequestLiveDate.value = Pair(VIEW_FLIPPER_ERROR_PR, R.string.error401)
                    } else {
                        viewFlipperPullRequestLiveDate.value = Pair(VIEW_FLIPPER_ERROR_PR, R.string.error400_gen)
                    }
                }
                is PullRequestResult.ServerError -> {
                    viewFlipperPullRequestLiveDate.value = Pair(VIEW_FLIPPER_ERROR_PR, R.string.error500)
                }
            }
        }
    }

    class ViewModelFactoryPR(private val dataSource: PullRequestRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PullRequestsViewModel::class.java)) {
                return PullRequestsViewModel(dataSource) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    companion object {
        private const val VIEW_FLIPPER_SHOW_PR = 1
        private const val VIEW_FLIPPER_ERROR_PR = 2
    }
}