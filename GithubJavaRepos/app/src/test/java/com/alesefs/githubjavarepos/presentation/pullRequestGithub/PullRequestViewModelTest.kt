package com.alesefs.githubjavarepos.presentation.pullRequestGithub

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.alesefs.githubjavarepos.R
import com.alesefs.githubjavarepos.data.model.pullRequestData.PullRequest
import com.alesefs.githubjavarepos.data.repository.pullRequestData.PullRequestRepository
import com.alesefs.githubjavarepos.data.repository.pullRequestData.PullRequestResult
import com.alesefs.githubjavarepos.presentation.pullRequestsGithub.PullRequestsViewModel
import com.nhaarman.mockitokotlin2.verify
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PullRequestViewModelTest {
    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var mPullRequestLiveDateObserver: Observer<List<PullRequest>>

    @Mock
    private lateinit var mViewFlipperPullRequestLiveDateObserver: Observer<Pair<Int, Int?>>

    private lateinit var viewModel: PullRequestsViewModel

    @Test
    fun `when viewModel getPullRequest get success then sets pullRequestLiveData`() {
        val pullRequests = listOf(
            PullRequest("title", "description", "userName","2012-10-03T20:23:47Z", "picture")
        )

        val resultSuccess = MockPullRequestRepository(PullRequestResult.Success(pullRequests))
        viewModel = PullRequestsViewModel(resultSuccess)
        viewModel.pullRequestLiveDate.observeForever(mPullRequestLiveDateObserver)
        viewModel.viewFlipperPullRequestLiveDate.observeForever(mViewFlipperPullRequestLiveDateObserver)

        viewModel.getPullRequest("repository", "repositoryName")

        verify(mPullRequestLiveDateObserver).onChanged(pullRequests)
        verify(mViewFlipperPullRequestLiveDateObserver).onChanged(Pair(1, null))
    }

    @Test
    fun `when viewModel getPullRequest get api error = 401 then sets viewFlipperLiveData`() {
        val resultApiError = MockPullRequestRepository(PullRequestResult.ApiError(401))
        viewModel = PullRequestsViewModel(resultApiError)
        viewModel.viewFlipperPullRequestLiveDate.observeForever(mViewFlipperPullRequestLiveDateObserver)

        viewModel.getPullRequest("repository", "repositoryName")

        verify(mViewFlipperPullRequestLiveDateObserver).onChanged(Pair(2, R.string.error401))
    }

    @Test
    fun `when viewModel getPullRequest get api error = generic then sets viewFlipperLiveData`() {
        val resultApiError = MockPullRequestRepository(PullRequestResult.ApiError(403))
        viewModel = PullRequestsViewModel(resultApiError)
        viewModel.viewFlipperPullRequestLiveDate.observeForever(mViewFlipperPullRequestLiveDateObserver)

        viewModel.getPullRequest("repository", "repositoryName")

        verify(mViewFlipperPullRequestLiveDateObserver).onChanged(Pair(2, R.string.error400_gen))
    }

    @Test
    fun `when viewModel getPullRequest get server error then sets viewFlipperLiveData`() {
        val resultServerError = MockPullRequestRepository(PullRequestResult.ServerError)
        viewModel = PullRequestsViewModel(resultServerError)
        viewModel.viewFlipperPullRequestLiveDate.observeForever(mViewFlipperPullRequestLiveDateObserver)

        viewModel.getPullRequest("repository", "repositoryName")

        verify(mViewFlipperPullRequestLiveDateObserver).onChanged(Pair(2, R.string.error500))
    }
}

class MockPullRequestRepository(private val result: PullRequestResult) : PullRequestRepository {
    override fun getPullRequest(
        repositoryURL: String,
        repositoryName: String,
        pullrequestResultCallback: (result: PullRequestResult) -> Unit
    ) {
        pullrequestResultCallback(result)
    }

}