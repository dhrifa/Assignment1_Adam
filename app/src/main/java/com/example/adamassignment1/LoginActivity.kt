package com.example.adamassignment1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.adamassignment1.databinding.ActivityLoginBinding
import com.google.android.material.button.MaterialButton

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginBtn: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)//  R.layout.activity_login)
        initViews()
        loginBtn.setOnClickListener { loginNavigate() }

    }

   private fun initViews() {
        loginBtn =binding.loginBtn// findViewById(R.id.loginBtn)
    }

   private fun loginNavigate() {
        val navIntent = Intent(this, MainActivity::class.java)
        startActivity(navIntent)
    }
}