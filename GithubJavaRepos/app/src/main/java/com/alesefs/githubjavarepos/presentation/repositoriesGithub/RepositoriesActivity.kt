package com.alesefs.githubjavarepos.presentation.repositoriesGithub

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.*
import com.alesefs.githubjavarepos.R
import com.alesefs.githubjavarepos.data.model.repositoryData.Repo
import com.alesefs.githubjavarepos.data.repository.repositoryData.RepositoryAPIDataSource
import com.alesefs.githubjavarepos.presentation.BaseActivity
import com.alesefs.githubjavarepos.presentation.pullRequestsGithub.PullRequestActivity
import kotlinx.android.synthetic.main.activity_repositories.*
import kotlinx.android.synthetic.main.include_toolbar.*


class RepositoriesActivity : BaseActivity() {

    private val perPage = 10
    private var page = 0
    private var isLoading = false
    lateinit var adapterRepo: RepositoriesAdapter
    lateinit var linearLayoutManager: LinearLayoutManager
//    private var visibleItemCount: Int = 0
//    private var totalItemCount: Int = 0
//    private var pastVisibleItems: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repositories)

        setupToolbar(include_toolbar_repo as Toolbar, toolbar_title, getString(R.string.app_name), false)


        val viewModel: RepositoriesViewModel = RepositoriesViewModel.ViewModelFactory(
            RepositoryAPIDataSource()
        ).create(RepositoriesViewModel::class.java)

        viewModel.repoLiveDate.observe(this, Observer {
            it?.let {repos ->
                isLoading = false
                with(recycleViewRepo) {
                    linearLayoutManager = LinearLayoutManager(this@RepositoriesActivity, RecyclerView.VERTICAL, false)
                    layoutManager = linearLayoutManager
                    setHasFixedSize(true)
                    adapterRepo = RepositoriesAdapter(repos as ArrayList<Repo>) { repository ->
                        val intent = repository.userName?.let { it1 ->
                            repository.realName?.let { it2 ->
                                PullRequestActivity.getStartIntent(this@RepositoriesActivity, it1, it2)
                            }
                        }
                        this@RepositoriesActivity.startActivity(intent)
                    }
                    adapterRepo.setData(repos)
                    adapter = adapterRepo


                    addOnScrollListener(object : RecyclerView.OnScrollListener() {
                        override fun onScrolled(
                            recyclerView: RecyclerView,
                            dx: Int,
                            dy: Int
                        ) {
                            super.onScrolled(recyclerView, dx, dy)

                            if (dy > 0) {
                                val visibleItemCount = linearLayoutManager.childCount
                                val pastVisibleItems = linearLayoutManager.findFirstCompletelyVisibleItemPosition()
                                val totalItemCount = adapterRepo.itemCount

                                if (isLoading) {
                                    if (visibleItemCount + pastVisibleItems >= totalItemCount) {
                                        isLoading = false
                                        page += 1
                                        viewModel.getRepos(page, perPage)
                                    }
                                }
                            }
                        }
                    })
                }
            }
        })

        viewModel.viewFlipperRepoLiveDate.observe(this, Observer {
            it?.let {viewFlipper ->
                view_flipper_repo_screen.displayedChild = viewFlipper.first
                viewFlipper.second?.let {errorMessageid ->
                    errorTextViewRepoScreen.text = getString(errorMessageid)
                }
            }
        })

        viewModel.pageRepoLiveDate.observe(this@RepositoriesActivity, Observer {
            it?.let {pagination ->
                isLoading = pagination.first
            }
        })

        viewModel.getRepos(page, perPage)
    }
}
