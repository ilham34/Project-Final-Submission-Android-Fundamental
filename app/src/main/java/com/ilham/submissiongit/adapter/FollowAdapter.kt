package com.ilham.submissiongit.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ilham.submissiongit.UsersItem
import com.ilham.submissiongit.databinding.ItemRowUserBinding

class FollowAdapter(private val listFollowing: List<UsersItem>) :
    RecyclerView.Adapter<FollowAdapter.FollowingViewHolder>() {
    class FollowingViewHolder(var binding: ItemRowUserBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowingViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FollowingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FollowingViewHolder, position: Int) {
        val following = listFollowing[position]

        holder.binding.apply {
            Glide.with(root.context)
                .load(following.avatarUrl)
                .circleCrop()
                .into(ivAvatar)
            tvUsername.text = following.login
            tvUrl.text = following.htmlUrl
        }
    }

    override fun getItemCount(): Int = listFollowing.size
}