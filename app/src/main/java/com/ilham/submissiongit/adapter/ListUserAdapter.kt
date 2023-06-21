package com.ilham.submissiongit.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ilham.submissiongit.UsersItem
import com.ilham.submissiongit.databinding.ItemRowUserBinding

class ListUserAdapter(private val listUser: ArrayList<UsersItem>) :
    RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {

    private lateinit var onItemClickCall: OnItemClickCall

    fun setOnItemClickCall(onItemClickCall: OnItemClickCall) {
        this.onItemClickCall = onItemClickCall
    }

    class ListViewHolder(var binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = listUser[position]
        holder.binding.apply {
            Glide.with(root)
                .load(user.avatarUrl)
                .circleCrop()
                .into(ivAvatar)
            tvUsername.text = user.login
            tvUrl.text = user.htmlUrl

            root.setOnClickListener { onItemClickCall.onItemClicked(listUser[holder.adapterPosition]) }
        }
    }

    override fun getItemCount(): Int = listUser.size

    interface OnItemClickCall {
        fun onItemClicked(data: UsersItem)
    }
}