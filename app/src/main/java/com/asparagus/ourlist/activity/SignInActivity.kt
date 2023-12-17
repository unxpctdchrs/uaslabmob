package com.asparagus.ourlist.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.asparagus.ourlist.databinding.ActivitySignInBinding
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.database

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
//    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        auth = FirebaseAuth.getInstance()

        binding.signupactBtn.setOnClickListener{
            Intent(this@SignInActivity, SignUpActivity::class.java).let {
                startActivity(it)
                finish()
            }
        }

        binding.signinBtn.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val pass = binding.etPwd.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty())
                loginUser(email, pass)
            else
                Toast.makeText(this, "Empty fields are not allowed", Toast.LENGTH_SHORT).show()
        }

    }

    private fun loginUser(email: String, pass: String) {
        Firebase.auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
            if (it.isSuccessful)
                Intent(this@SignInActivity, HomeActivity::class.java).let {
                    startActivity(it)
                    finish()
                }
            else
                Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()

        }
    }
}