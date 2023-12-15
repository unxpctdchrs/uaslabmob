package com.asparagus.ourlist.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.asparagus.ourlist.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.signinactBtn.setOnClickListener{
            Intent(this@SignUpActivity, SignInActivity::class.java).let {
                startActivity(it)
                finish()
            }
        }

        binding.signupBtn.setOnClickListener{
            val email = binding.etEmail.text.toString()
            val pwd = binding.etPwd.text.toString()
            val verPwd = binding.etVerPwd.text.toString()

            if (email.isNotEmpty() && pwd.isNotEmpty() && verPwd.isNotEmpty()) {
                if (pwd == verPwd) {

                    registerUser(email, pwd)

                }
                else {
                    Toast.makeText(this, "Password is not same", Toast.LENGTH_SHORT).show()
                }
            }
            else {
                Toast.makeText(this, "Empty fields are not allowed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun registerUser(email: String, pass: String) {
        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
            if (it.isSuccessful)
                Intent(this@SignUpActivity, SignInActivity::class.java).let {
                    startActivity(it)
                    finish()
                }
            else
                Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()

        }
    }
}