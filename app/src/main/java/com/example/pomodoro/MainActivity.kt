package com.example.pomodoro

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //find the buttons by their IDs
        val loginButton = findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.loginbutton)
        val registerButton = findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.registerbutton)

        //set click listener when button is clicked and direct to login
        loginButton.setOnClickListener {
            val intent = Intent(this, login_page::class.java)
            startActivity(intent)
        }
        //set click listener when button is clicked and direct to signup
        registerButton.setOnClickListener {
            val intent = Intent(this, register_page::class.java)
            startActivity(intent)
        }
    }
}
