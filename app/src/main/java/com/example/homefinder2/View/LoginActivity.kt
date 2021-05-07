package com.example.homefinder2.View

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.homefinder2.R
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.GraphRequest
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {
    private var callbackManager: CallbackManager? = null
    private lateinit var auth: FirebaseAuth
    private val fireBase = FirebaseDatabase.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

// ...
// Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
//        var btnLoginFacebook = findViewById<Button>(R.id.login_button)
        signin_btn.setOnClickListener {
            doLogin()
        }

        login_button.setOnClickListener {
            // Login
            callbackManager = CallbackManager.Factory.create()
            LoginManager.getInstance()
                .logInWithReadPermissions(this, arrayListOf("public_profile", "email"))
            LoginManager.getInstance().registerCallback(callbackManager,
                object : FacebookCallback<LoginResult> {
                    override fun onSuccess(loginResult: LoginResult) {
                        val request: GraphRequest = GraphRequest.newMeRequest(
                            loginResult.accessToken
                        ) { `object`, response ->
                            Log.v("LoginActivity", response.toString())

                            // Application code
                            val email = `object`.getString("email")
                            val profile =
                                `object`.getString("public_profile") // 01/31/1980 format
                        }
                        val parameters = Bundle()
                        parameters.putString("fields", "profile, email")
                        request.parameters = parameters
                        request.executeAsync()
                        Log.d("MainActivity", "Facebook token: " + loginResult.accessToken.token)
                        startActivity(Intent(applicationContext, LandlordActivity::class.java))
                        val currentUser = auth.currentUser
                        updateUI(currentUser)
                    }

                    override fun onCancel() {
                        Log.d("MainActivity", "Facebook onCancel.")
                        updateUI(null)

                    }

                    override fun onError(error: FacebookException) {
                        Log.d("MainActivity", "Facebook onError.")
                        updateUI(null)

                    }
                })
        }

        cre.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        callbackManager?.onActivityResult(requestCode, resultCode, data)
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser


    }


    private fun doLogin() {
        val preferences: SharedPreferences = this.getSharedPreferences("login", 0)
        val edited = preferences.edit()
        if (username.text.toString().isEmpty()) {
            username.error = "please enter email"
            username.requestFocus()
            return

        }

        if (!Patterns.EMAIL_ADDRESS.matcher(username.text.toString()).matches()) {
            username.error = "Type in the valid one"
            username.requestFocus()
            return
        }

        if (password.text.toString().isEmpty()) {
            password.error = "please enter password"
            password.requestFocus()
            return
        }

        auth.signInWithEmailAndPassword(username.text.toString(), password.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    updateUI(user)
                    edited.putBoolean("isLoggedIn",true)
                    edited.commit()

                } else {
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }
            }
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            val preferences: SharedPreferences = this.getSharedPreferences("takeRole", 0)
            val editor = preferences.edit()
            val uid = auth.currentUser?.uid
            val userRef = fireBase.getReference("Accounts")
            val ordersRef = userRef.child(uid!!)
            val valueEventListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val role = dataSnapshot.child("role").getValue(String::class.java)
                    Log.d("DataBaseGetName", "role")
                    if (role.equals("landlord"))
                    {
                        editor.putString("role", "landlord")
                        editor.commit()
                        startActivity(Intent(applicationContext, LandlordActivity::class.java))

                    }

                    else if (role.equals("user"))
                    {
                        editor.putString("role", "user")
                        editor.commit()
                        startActivity((Intent(applicationContext, UserActivity::class.java)))
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.d("Data", databaseError.message) //Don't ignore errors!
                }
            }
            ordersRef.addValueEventListener(valueEventListener)



        } else {
            Toast.makeText(baseContext, "Login Failed", Toast.LENGTH_SHORT).show()
        }
    }
}


