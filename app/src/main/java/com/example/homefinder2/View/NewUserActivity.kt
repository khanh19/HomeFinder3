package com.example.homefinder2.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.homefinder2.R
import com.example.homefinder2.ViewModel.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_new_user.*

class NewUserActivity : AppCompatActivity() {
    private val adapt by lazy{ ViewPagerAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_user)
        viewpager.adapter =adapt
        val tablayoutMediator = TabLayoutMediator(tab_layout,viewpager,
            TabLayoutMediator.TabConfigurationStrategy{ tab, position ->
                when(position + 1)
                {
                    1 -> {
                        tab.setIcon(R.drawable.tab_selector)
                    }
                    2 -> {
                        tab.setIcon(R.drawable.tab_selector)
                    }
                    3 -> {
                        tab.setIcon(R.drawable.tab_selector)
                    }
                    4 -> {
                        tab.setIcon(R.drawable.tab_selector)
                    }
                }

            })
        tablayoutMediator.attach()

        login_btn.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        signup_btn.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }




}
