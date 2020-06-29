package com.alesefs.githubjavarepos.presentation.pullRequestsGithub

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alesefs.githubjavarepos.R
import com.alesefs.githubjavarepos.data.model.pullRequestData.PullRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.item_into_repo.view.*

class PullRequestsAdapter(
    private val pullRequests: List<PullRequest>
) : RecyclerView.Adapter<PullRequestsAdapter.PullRequestViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, view: Int): PullRequestViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_into_repo, parent, false)
        return PullRequestViewHolder(itemView)
    }

    override fun getItemCount() = pullRequests.count()

    override fun onBindViewHolder(holder: PullRequestViewHolder, position: Int) {
        holder.bindView(pullRequests[position])
    }


    class PullRequestViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        private val prTitle = itemView.pull_request_title
        private val prDescription = itemView.pull_request_description
        private val prProfileName = itemView.profile_name
        private val prCreateAt = itemView.profile_surname
        private val prProfilePic = itemView.img_pr_owner

        fun bindView(pullRequest: PullRequest) {
            prTitle.text = pullRequest.titlePR
            prDescription.text = pullRequest.descriptionPR
            prProfileName.text = "@${pullRequest.userName}"
            prCreateAt.text = "Criado em: ${pullRequest.createAt}"

            Glide.with(itemView.context)
                .load(pullRequest.picture)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_profile)
                .into(prProfilePic)

        }
    }
}
