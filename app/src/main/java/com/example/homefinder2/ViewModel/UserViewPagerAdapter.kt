package com.example.homefinder2.ViewModel

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.homefinder2.View.*

class UserViewPagerAdapter(ma: UserActivity): FragmentStateAdapter(ma) {
    override fun getItemCount(): Int = 4
    override fun createFragment(position: Int): Fragment {
        val frag1 = UserHomeFragment()
        val frag2 = UserSaveFragment()
        val frag3 = UserNoticeFragment()
        val frag4 = UserAccountFragment()

        when (position + 1) {
            1 -> {
                return frag1
            }
            2 -> {
                return frag2
            }
            3 -> {

                return frag3
            }

            else -> {
                return frag4
            }
        }


    }
}