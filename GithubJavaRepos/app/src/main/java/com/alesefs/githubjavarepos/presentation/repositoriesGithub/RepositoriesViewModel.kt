package com.alesefs.githubjavarepos.presentation.repositoriesGithub

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.alesefs.githubjavarepos.R
import com.alesefs.githubjavarepos.data.repository.repositoryData.ReposResult
import com.alesefs.githubjavarepos.data.model.repositoryData.Repo
import com.alesefs.githubjavarepos.data.repository.repositoryData.ReposRepository

class RepositoriesViewModel(private val dataSource: ReposRepository) : ViewModel() {

    val repoLiveDate: MutableLiveData<List<Repo>> = MutableLiveData()
    val viewFlipperRepoLiveDate: MutableLiveData<Pair<Int, Int?>> = MutableLiveData()
    val pageRepoLiveDate: MutableLiveData<Pair<Boolean, Int>> = MutableLiveData()

    fun getRepos(page: Int, perPage: Int) {
        dataSource.getRepos(page, perPage) { result: ReposResult ->
            when(result) {
                is ReposResult.Success -> {
                    repoLiveDate.value = result.repos
                    viewFlipperRepoLiveDate.value = Pair(VIEW_FLIPPER_SHOW_REPOS, null)
                    pageRepoLiveDate.value = Pair(true, page)
                }
                is ReposResult.ApiError -> {
                    if (result.statusCode == 401) {
                        viewFlipperRepoLiveDate.value = Pair(VIEW_FLIPPER_ERROR_REPOS, R.string.error401)
                    } else {
                        viewFlipperRepoLiveDate.value = Pair(VIEW_FLIPPER_ERROR_REPOS, R.string.error400_gen)
                    }
                    pageRepoLiveDate.value = Pair(true, page)
                }
                is ReposResult.ServerError -> {
                    viewFlipperRepoLiveDate.value = Pair(VIEW_FLIPPER_ERROR_REPOS, R.string.error500)
                    pageRepoLiveDate.value = Pair(true, page)
                }
            }
        }
    }

    class ViewModelFactory(private val dataSource: ReposRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RepositoriesViewModel::class.java)) {
                return RepositoriesViewModel(dataSource) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    companion object {
        private const val VIEW_FLIPPER_SHOW_REPOS = 1
        private const val VIEW_FLIPPER_ERROR_REPOS = 2
    }
}