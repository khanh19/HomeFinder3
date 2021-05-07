package com.example.homefinder2.View

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.homefinder2.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.DecimalFormat


class SplashActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private val splashier: Long = 3000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        val preferences: SharedPreferences = this.getSharedPreferences("login", 0)
        val edited = preferences.edit()
        val isLoggedIn = preferences.getBoolean("isLoggedIn", false)
        if (isLoggedIn) {
            Log.d("onCreate: ", "is user login")
            edited.putBoolean("isLoggedIn", true)
            edited.commit()
            if (currentUser != null) {
                val preferences: SharedPreferences = this.getSharedPreferences("takeRole", 0)
                val name= preferences.getString("role","")
                if (name.equals("landlord"))
                {
                    Handler().postDelayed({
                        startActivity(Intent(this, LandlordActivity::class.java))
                        finish()
                    }, splashier)
                }
                else  if (name.equals("user"))
                {
                    Handler().postDelayed({
                        startActivity(Intent(this, UserActivity::class.java))
                        finish()
                    }, splashier)
                }
                else
                {

                    Handler().postDelayed({
                        startActivity(Intent(this, DecisionActivity::class.java))
                        finish()
                    }, splashier)

                }
            }
            else{
                Handler().postDelayed({
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }, splashier)
            }
        } else {
            Log.d("onCreate: ", "not user login ")
            val pref: SharedPreferences = this.getSharedPreferences("First Time", 0)
            val editor = pref.edit()
            val firstRun = pref.getBoolean("firstRun", true)
            if (firstRun) {
                Log.d("onCreate: ", "first time")
                editor.putBoolean("firstRun", false)
                editor.commit()
                Handler().postDelayed({
                    startActivity(Intent(this, NewUserActivity::class.java))
                    finish()
                }, splashier)

            } else {
                Log.d("onCreate: ", "second time")
                Handler().postDelayed({
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }, splashier)
            }
        }
    }
}
