package com.example.homefinder2.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.homefinder2.R
import com.example.homefinder2.ViewModel.UserViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_new_user.*
import kotlinx.android.synthetic.main.activity_user.*

class UserActivity : AppCompatActivity() {
    private val adapt by lazy{UserViewPagerAdapter(this)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        pager.adapter =adapt
        val tablayoutMediator = TabLayoutMediator(tab_lay, pager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                when (position + 1) {
                    1 -> {
                        tab.setIcon(R.drawable.ic_home)
                    }
                    2 -> {
                        tab.setIcon(R.drawable.ic_favorite)
                    }
                    3 -> {
                        tab.setIcon(R.drawable.ic_notifications)
                    }
                    4 -> {
                        tab.setIcon(R.drawable.ic_account)
                    }
                }

            })
        tablayoutMediator.attach()
}
}
