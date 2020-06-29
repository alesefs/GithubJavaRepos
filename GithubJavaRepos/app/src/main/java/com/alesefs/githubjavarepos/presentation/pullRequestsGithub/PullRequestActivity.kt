package com.alesefs.githubjavarepos.presentation.pullRequestsGithub

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import com.alesefs.githubjavarepos.R
import com.alesefs.githubjavarepos.data.repository.pullRequestData.PullRequestAPIDataSource
import com.alesefs.githubjavarepos.presentation.BaseActivity
import kotlinx.android.synthetic.main.activity_pull_request.*
import kotlinx.android.synthetic.main.include_toolbar.*

class PullRequestActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pull_request)

        val repositoryName: String = intent.getStringExtra(EXTRA_USERNAME)
        val repositoryURL: String = intent.getStringExtra(EXTRA_REPOSITORY)
        setupToolbar(include_toolbar_pr as Toolbar, toolbar_title, repositoryName, true)

        val viewModel: PullRequestsViewModel = PullRequestsViewModel.ViewModelFactoryPR(
            PullRequestAPIDataSource()
        ).create(PullRequestsViewModel::class.java)
        viewModel.pullRequestLiveDate.observe(this, Observer {
            it?.let {pullRequests ->
                with(recycleViewPullRequests) {
                    layoutManager = LinearLayoutManager(this@PullRequestActivity, RecyclerView.VERTICAL, false)
                    setHasFixedSize(true)
                    adapter = PullRequestsAdapter(pullRequests)
                }
            }
        })

        viewModel.viewFlipperPullRequestLiveDate.observe(this, Observer {
            it?.let {viewFlipper ->
                view_flipper_pull_request_screen.displayedChild = viewFlipper.first
                viewFlipper.second?.let {errorMessageid ->
                    errorTextViewPullRequestScreen.text = getString(errorMessageid)
                }
            }
        })

        viewModel.getPullRequest(repositoryURL, repositoryName)
    }

    companion object {
        private const val EXTRA_USERNAME = "EXTRA_USERNAME"
        private const val EXTRA_REPOSITORY = "EXTRA_REPOSITORY"

        fun getStartIntent(context: Context, repository: String, realName: String): Intent {
            return Intent(context, PullRequestActivity::class.java).apply {
                putExtra(EXTRA_REPOSITORY, repository)
                putExtra(EXTRA_USERNAME, realName)
            }
        }
    }
}