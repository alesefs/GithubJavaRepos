package com.alesefs.githubjavarepos.presentation.repositoriesGithub

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alesefs.githubjavarepos.R
import com.alesefs.githubjavarepos.data.model.repositoryData.Repo
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.item_repo.view.*

class RepositoriesAdapter(
    private val repos: ArrayList<Repo>,
    private val onItemClickListener: ((currency: Repo) -> Unit)
) : RecyclerView.Adapter<RepositoriesAdapter.RepoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, view: Int): RepoViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_repo, parent, false)
        return RepoViewHolder(itemView, onItemClickListener)
    }

    override fun getItemCount() = repos.count()

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.bindView(repos[position])
    }

    fun setData(repos: List<Repo>) {
        this.repos.addAll(repos)
        notifyDataSetChanged()
    }


    class RepoViewHolder(
        itemView: View,
        private val onItemClickListener: ((currency: Repo) -> Unit)
    ) : RecyclerView.ViewHolder(itemView) {

        private val repoTitle = itemView.repo_title
        private val repoDescription = itemView.repo_description
        private val repoCounterForks = itemView.repo_count_forks
        private val repoCounterStars = itemView.repo_count_stars
        private val repoUserName = itemView.profile_username
        private val repoRealName = itemView.profile_real_name
        private val picture = itemView.profile_img

        fun bindView(repo: Repo) {
            repoTitle.text = repo.titleRepo
            repoTitle.contentDescription = repo.titleRepo

            repoDescription.text = repo.descriptionRepo
            repoDescription.contentDescription = repo.descriptionRepo

            repoCounterForks.text = repo.countForks.toString()
            repoCounterForks.contentDescription = repo.countForks.toString()

            repoCounterStars.text = repo.countStars.toString()
            repoCounterStars.contentDescription = repo.countStars.toString()

            repoUserName.text = "@${repo.userName}"
            repoUserName.contentDescription = "@${repo.userName}"

            repoRealName.text = repo.userNameDisplay
            repoRealName.contentDescription = repo.userNameDisplay

            Glide.with(itemView.context)
                .load(repo.picture)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_profile)
                .into(picture);
            picture.contentDescription = "foto de: ${repo.userName}"

            itemView.setOnClickListener {
                onItemClickListener.invoke(repo)
            }
        }
    }
}
