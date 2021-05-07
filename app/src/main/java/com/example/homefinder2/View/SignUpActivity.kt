package com.example.homefinder2.View

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.homefinder2.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_sign_up)
        auth = FirebaseAuth.getInstance()
        val preferences: SharedPreferences = this.getSharedPreferences("takeRole", 0)
        val editor: SharedPreferences.Editor = preferences.edit()
        sigu_btn.setOnClickListener {
            if (email.text.toString().isEmpty()) {
                email.error = "Please enter your email"
                email.requestFocus()
                return@setOnClickListener
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()) {
                email.error = "Type in the valid one"
                email.requestFocus()
                return@setOnClickListener
            }

            if (password.text.toString().isEmpty()) {
                password.error = "please enter password"
                password.requestFocus()
                return@setOnClickListener
            }

            if (!confirmpass.text.toString().equals(password.text.toString())) {
                confirmpass.error = "Please retype your password"
                confirmpass.requestFocus()
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        editor.putString("role", "null")
                        editor.commit()
                        startActivity(Intent(this, DecisionActivity::class.java))
                        finish()
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(
                            baseContext, "Sign up failed. Try again",
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                }
        }
    }
}