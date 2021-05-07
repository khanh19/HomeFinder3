package com.example.homefinder2.View

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.example.homefinder2.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_pop_up.*
import kotlinx.android.synthetic.main.activity_pop_up.view.*

class DecisionActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private val database = Firebase.database.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        auth = FirebaseAuth.getInstance()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_decision)
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.activity_pop_up, null)
        //AlertDialogBuilder

        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
        val  mAlertDialog = mBuilder.show()
        val preferences: SharedPreferences = this.getSharedPreferences("takeRole", 0)
        val editor: SharedPreferences.Editor = preferences.edit()
        mDialogView.Landlord.setOnClickListener {
//
            val database = FirebaseDatabase.getInstance()
            val id = auth.currentUser?.uid
            val myRef = database.getReference("Accounts")
            myRef.child(id!!).child("role").setValue("landlord")
            editor.putString("role", "landlord")
            editor.commit()
            startActivity(Intent(this, LandlordActivity::class.java))
            mAlertDialog.dismiss()
        }
        mAlertDialog.User.setOnClickListener {
            val database = FirebaseDatabase.getInstance()
            val id = auth.currentUser?.uid
            val myRef = database.getReference("Accounts")
            myRef.child(id!!).child("role").setValue("user")
            editor.putString("role", "user")
            editor.commit()
            startActivity(Intent(this, UserActivity::class.java))
        }

    }
}
