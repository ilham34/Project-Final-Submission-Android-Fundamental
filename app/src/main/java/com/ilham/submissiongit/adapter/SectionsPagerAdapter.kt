package com.ilham.submissiongit.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ilham.submissiongit.ui.fragment.FollowFragment
import kotlin.math.log

class SectionsPagerAdapter(activity: AppCompatActivity, private val login: Bundle) :
    FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        var fragment = FollowFragment()
        fragment.arguments = login.apply {
            putInt(FollowFragment.SECTION_NUMBER, position + 1)
        }
        return fragment
    }
}