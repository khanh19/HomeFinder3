package com.example.homefinder2.ViewModel

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.homefinder2.View.*

class ViewPagerAdapter(fa: NewUserActivity): FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        val fragment1 = InstructionFragment1()
        val fragment2 = InstructionFragment2()
        val fragment3 = InstructionFragment3()
        val fragment4 = InstructionFragment4()

        when (position + 1) {
            1 -> {
                //  val homeFragment: HomeFragment = HomeFragment()
                return fragment1
            }
            2 -> {
                return fragment2
            }
            3 -> {
                // val movieFragment = MovieFragment()
                return fragment3
            }

            else -> {
                return fragment4
            }
        }
    }
}