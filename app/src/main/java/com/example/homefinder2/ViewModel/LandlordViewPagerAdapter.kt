package com.example.homefinder2.ViewModel

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.homefinder2.View.*

class LandlordViewPagerAdapter(la: LandlordActivity): FragmentStateAdapter(la) {
    override fun getItemCount(): Int = 5


    override fun createFragment(position: Int): Fragment {
        val fra1 = LandlordHomeFrag()
        val fra2 = LandlordNoticeFrag()
        val fra3 = LandlordUsersFrag()
        val fra4 = LandlordIncomeFrag()
        val fra5 = LandlordSettingsFrag()

        when (position + 1) {
            1 -> {
                return fra1
            }
            2 -> {
                return fra2
            }
            3 -> {

                return fra3
            }
            4 -> {
                return fra4
            }

            else -> {
                return fra5
            }
        }
    }
}