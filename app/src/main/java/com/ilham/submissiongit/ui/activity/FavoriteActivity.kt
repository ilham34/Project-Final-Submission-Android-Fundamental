package com.ilham.submissiongit.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ilham.submissiongit.database.FavoriteUser
import com.ilham.submissiongit.UsersItem
import com.ilham.submissiongit.adapter.ListUserAdapter
import com.ilham.submissiongit.databinding.ActivityFavoriteBinding
import com.ilham.submissiongit.ui.viewmodel.FavoriteViewModel

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private val favUserViewModel by viewModels<FavoriteViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Favorite"

        favUserViewModel.getFavoriteUser()?.observe(this, {
            showUser(it)
        })

        val layoutManager = LinearLayoutManager(this)
        binding.rvUserFavorite.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUserFavorite.addItemDecoration(itemDecoration)
    }

    private fun showUser(items: List<FavoriteUser>) {
        val listUser = ArrayList<UsersItem>()
        for (i in items) {
            val user = UsersItem(i.id, i.avatarUrl, i.login, i.htmlUrl)
            listUser.add(user)
        }

        val userAdapter = ListUserAdapter(listUser)

        binding.rvUserFavorite.apply {
            setHasFixedSize(true)
            adapter = userAdapter
        }

        userAdapter.setOnItemClickCall(object : ListUserAdapter.OnItemClickCall {
            override fun onItemClicked(data: UsersItem) {

                val moveDetailUser = Intent(this@FavoriteActivity, DetailUserActivity::class.java)
                moveDetailUser.putExtra(DetailUserActivity.EXTRA_USER, data)
                startActivity(moveDetailUser)
            }
        })
    }
}