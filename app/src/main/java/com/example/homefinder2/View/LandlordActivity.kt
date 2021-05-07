package com.example.homefinder2.View

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.homefinder2.R
import com.example.homefinder2.ViewModel.LandlordViewPagerAdapter
import com.example.homefinder2.ViewModel.UserViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_pop_up.*
import kotlinx.android.synthetic.main.activity_pop_up.view.*
import kotlinx.android.synthetic.main.activity_user.*


class LandlordActivity : AppCompatActivity() {
    private val adapt by lazy{ LandlordViewPagerAdapter(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        vipi.adapter = adapt
        val tablayoutMediator = TabLayoutMediator(ta_la, vipi,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                when (position + 1) {
                    1 -> {
                        tab.setIcon(R.drawable.ic_home)
                    }
                    2 -> {
                        tab.setIcon(R.drawable.ic_notifications)
                    }
                    3 -> {
                        tab.setIcon(R.drawable.ic_people)
                    }
                    4 -> {
                        tab.setIcon(R.drawable.ic_monetization)
                    }
                    5 -> {
                        tab.setIcon(R.drawable.ic_settings)
                    }
                }

            })
        tablayoutMediator.attach()

    }
}


