package com.ilham.submissiongit.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ilham.submissiongit.UsersItem
import com.ilham.submissiongit.adapter.FollowAdapter
import com.ilham.submissiongit.databinding.FragmentFollowBinding
import com.ilham.submissiongit.ui.activity.DetailUserActivity
import com.ilham.submissiongit.ui.viewmodel.FollowViewModel

class FollowFragment : Fragment() {
    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!
    private val followViewModel by viewModels<FollowViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val index = arguments?.getInt(SECTION_NUMBER, 0)

        followViewModel.isLoading.observe(viewLifecycleOwner, {
            showLoading(it)
        })
        followViewModel.listFollowing.observe(viewLifecycleOwner, { listFollowing ->
            setData(listFollowing)
        })
        followViewModel.listFollower.observe(viewLifecycleOwner, { listFollower ->
            setData(listFollower)
        })

        when (index) {
            1 -> followViewModel.getFollowerUser(arguments?.getString(DetailUserActivity.EXTRA_FRAGMENT).toString())
            2 -> followViewModel.getFollowingUser(arguments?.getString(DetailUserActivity.EXTRA_FRAGMENT).toString())
        }

    }

    private fun setData(listFollow: ArrayList<UsersItem>) {
        val listUser = ArrayList<UsersItem>()
        binding.apply {
            for (a in listFollow) {
                listUser.clear()
                listUser.addAll(listFollow)
            }
            val adapter = FollowAdapter(listFollow)
            rvFollow.layoutManager = LinearLayoutManager(context)
            rvFollow.adapter = adapter
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
        const val SECTION_NUMBER = "section_number"
    }
}