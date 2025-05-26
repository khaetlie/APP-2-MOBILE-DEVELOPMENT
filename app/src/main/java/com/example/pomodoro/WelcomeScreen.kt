package com.example.pomodoro

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton

class WelcomeScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_screen)

        //get text by their IDs
        val greetingText = findViewById<TextView>(R.id.greetingText)
        val messageText = findViewById<TextView>(R.id.messageText)

        //get sharedPreferences to retrieve teh current logged in user
        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val username = sharedPreferences.getString("loggedInUser", "User")

        //this is a personalized message to welcome the user using the username retrieved
        greetingText.text = "Welcome, \n$username!"
        messageText.text = "You've just taken the first step toward a more focused and productive you.\n\nLet's get started!"

        val continueButton = findViewById<AppCompatButton>(R.id.continueButton)
        //set click listener for the continueButton
        continueButton.setOnClickListener {
            startActivity(Intent(this, HomeScreen::class.java))
            finish()
        }
    }
}
