package com.alesefs.githubjavarepos.presentation.repositoriesGithub

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.alesefs.githubjavarepos.R
import com.alesefs.githubjavarepos.data.model.repositoryData.Repo
import com.alesefs.githubjavarepos.data.repository.repositoryData.ReposRepository
import com.alesefs.githubjavarepos.data.repository.repositoryData.ReposResult
import com.nhaarman.mockitokotlin2.verify
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RepositoryViewModelTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var mRepoLiveDataObserver: Observer<List<Repo>>

    @Mock
    private lateinit var mViewFlipperRepoLiveDateObserver: Observer<Pair<Int, Int?>>

    @Mock
    private lateinit var mPageRepoLiveDateObserver: Observer<Pair<Boolean, Int>>

    private lateinit var viewModel: RepositoriesViewModel

    @Test
    fun `when viewModel getRepos get success then sets repositoryLiveData`() {
        val repos = listOf(
            Repo("title", "description", 1,1, "userName", "realName", "picture", "userNameDisplay")
        )

        val resultSuccess = MockReposRepository(ReposResult.Success(repos))
        viewModel = RepositoriesViewModel(resultSuccess)
        viewModel.repoLiveDate.observeForever(mRepoLiveDataObserver)
        viewModel.viewFlipperRepoLiveDate.observeForever(mViewFlipperRepoLiveDateObserver)
        viewModel.pageRepoLiveDate.observeForever(mPageRepoLiveDateObserver)

        viewModel.getRepos(1,1)

        verify(mRepoLiveDataObserver).onChanged(repos)
        verify(mViewFlipperRepoLiveDateObserver).onChanged(Pair(1, null))
        verify(mPageRepoLiveDateObserver).onChanged(Pair(true, 1))
    }

    @Test
    fun `when viewModel getRepos get api error = 401 then sets viewFlipperLiveData`() {
        val resultApiError = MockReposRepository(ReposResult.ApiError(401))
        viewModel = RepositoriesViewModel(resultApiError)
        viewModel.viewFlipperRepoLiveDate.observeForever(mViewFlipperRepoLiveDateObserver)
        viewModel.pageRepoLiveDate.observeForever(mPageRepoLiveDateObserver)

        viewModel.getRepos(1, 1)

        verify(mViewFlipperRepoLiveDateObserver).onChanged(Pair(2, R.string.error401))
        verify(mPageRepoLiveDateObserver).onChanged(Pair(true, 1))
    }

    @Test
    fun `when viewModel getRepos get api error = generic then sets viewFlipperLiveData`() {
        val resultApiError = MockReposRepository(ReposResult.ApiError(403))
        viewModel = RepositoriesViewModel(resultApiError)
        viewModel.viewFlipperRepoLiveDate.observeForever(mViewFlipperRepoLiveDateObserver)
        viewModel.pageRepoLiveDate.observeForever(mPageRepoLiveDateObserver)

        viewModel.getRepos(1,1)

        verify(mViewFlipperRepoLiveDateObserver).onChanged(Pair(2, R.string.error400_gen))
        verify(mPageRepoLiveDateObserver).onChanged(Pair(true, 1))
    }

    @Test
    fun `when viewModel getRepos get server error then sets viewFlipperLiveData`() {
        val resultSereverError = MockReposRepository(ReposResult.ServerError)
        viewModel = RepositoriesViewModel(resultSereverError)
        viewModel.viewFlipperRepoLiveDate.observeForever(mViewFlipperRepoLiveDateObserver)
        viewModel.pageRepoLiveDate.observeForever(mPageRepoLiveDateObserver)

        viewModel.getRepos(1,1)

        verify(mViewFlipperRepoLiveDateObserver).onChanged(Pair(2, R.string.error500))
        verify(mPageRepoLiveDateObserver).onChanged(Pair(true, 1))
    }
}

class MockReposRepository(private val result: ReposResult) : ReposRepository {
    override fun getRepos(
        page: Int,
        perPage: Int,
        reposResultCallback: (result: ReposResult) -> Unit
    ) {
        reposResultCallback(result)
    }
}