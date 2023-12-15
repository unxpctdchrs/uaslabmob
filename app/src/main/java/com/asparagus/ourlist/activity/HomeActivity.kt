package com.asparagus.ourlist.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.asparagus.ourlist.R
import com.asparagus.ourlist.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}