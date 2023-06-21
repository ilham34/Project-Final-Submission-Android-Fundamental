package com.ilham.submissiongit.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.ilham.submissiongit.R
import com.ilham.submissiongit.UserDetail
import com.ilham.submissiongit.UsersItem
import com.ilham.submissiongit.adapter.SectionsPagerAdapter
import com.ilham.submissiongit.databinding.ActivityDetailUserBinding
import com.ilham.submissiongit.ui.viewmodel.DetailUserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailUserActivity : AppCompatActivity(), View.OnClickListener {
    private var _binding: ActivityDetailUserBinding? = null
    private val binding get() = _binding!!
    private val detailUserViewModel by viewModels<DetailUserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        detailUserViewModel.isLoading.observe(this, {
            showLoading(it)
        })

        detailUserViewModel.listDetail.observe(this, { users ->
            setData(users)
        })

        setTableLayout()

        binding.btnShare.setOnClickListener(this)
    }

    private fun setTableLayout() {
        val intentUser = intent.getParcelableExtra<UsersItem>(EXTRA_USER) as UsersItem
        detailUserViewModel.getUsers(intentUser.login)

        supportActionBar?.title = intentUser.login

        val login = Bundle()
        login.putString(EXTRA_FRAGMENT, intentUser.login)

        var _isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = detailUserViewModel.checkUser(intentUser.id)
            withContext(Dispatchers.Main) {
                if (count != null) {
                    if (count > 0) {
                        binding.toggleButton.isChecked = true
                        _isChecked = true
                    } else {
                        binding.toggleButton.isChecked = false
                        _isChecked = false
                    }
                }
            }
        }

        binding.toggleButton.setOnClickListener {
            _isChecked = !_isChecked
            if (_isChecked) {
                detailUserViewModel.addToFavorite(intentUser.login, intentUser.id, intentUser.avatarUrl, intentUser.htmlUrl)
            } else {
                detailUserViewModel.removeFromFavorite(intentUser.id)
            }
            binding.toggleButton.isChecked = _isChecked
        }

        val sectionPagerAdapter = SectionsPagerAdapter(this, login)
        val viewPager: ViewPager2 = binding.viewPager

        viewPager.adapter = sectionPagerAdapter
        val tabs: TabLayout = binding.tabLayout
        val tabTitle = resources.getStringArray(R.array.tab_title)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = tabTitle[position]
        }.attach()
    }

    private fun setData(users: UserDetail) {
        binding.apply {
            Glide.with(this@DetailUserActivity)
                .load(users.avatarUrl)
                .circleCrop()
                .into(imgUser)
            tvName.text = users.name ?: "No Name"
            tvUsername.text = users.login
            tvFollower.text = users.followers.toString()
            tvFollowing.text = users.following.toString()
            tvCompany.text = users.company ?: "No Company"
            tvLocation.text = users.location ?: "No Location"
            tvRepo.text = users.repos.toString()
        }
    }

    override fun onClick(p0: View?) {
        if (p0 == binding.btnShare) {
            val share = Intent(Intent.ACTION_SEND)
            share.type = "text/plain"
            val name = binding.tvName.text
            share.putExtra(Intent.EXTRA_TEXT, name)
            startActivity(Intent.createChooser(share, getString(R.string.sharew)))
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val EXTRA_USER = "extra_user"
        const val EXTRA_FRAGMENT = "extra_fragment"
    }
}