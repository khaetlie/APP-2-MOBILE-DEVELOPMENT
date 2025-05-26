package com.example.pomodoro

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import android.widget.EditText
import org.json.JSONObject


class login_page : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login_page)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //find UI elements by their IDs
        val usernameEdit = findViewById<EditText>(R.id.usernametext)
        val passwordEdit = findViewById<EditText>(R.id.passwordtext)
        val loginButton = findViewById<AppCompatButton>(R.id.loginbutton)
        val registerButton = findViewById<AppCompatButton>(R.id.registerbutton) // ✅ move this here

        //set click listener once the register button is clicked, it directs to register screen
        registerButton.setOnClickListener {
            startActivity(Intent(this, register_page::class.java))
        }

        //set click listener once the login button is clicked
        loginButton.setOnClickListener {
                val enteredUsername = usernameEdit.text.toString()
                val enteredPassword = passwordEdit.text.toString()

            //access shared preferences where the data is stored
                val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
            //retrieve JSON string that contains all the registered users
                val usersJson = sharedPreferences.getString("users", "{}")
            //covert the JSON string into a JSONObject for easy access
                val users = JSONObject(usersJson ?: "{}")

            //check if the username exists in stored users
                if (users.has(enteredUsername)) {
                    val user = users.getJSONObject(enteredUsername)

                    //check if the entered password is the same to the stored user for the specific user
                    if (user.getString("password") == enteredPassword) {
                        //this means the login is successful and it is saved in preferences
                        sharedPreferences.edit().putString("loggedInUser", enteredUsername).apply()
                        //this is an alert message below if it's successful
                        Toast.makeText(this, "Login success!", Toast.LENGTH_SHORT).show()
                        //once it is successful, go to the welcomescreen
                        startActivity(Intent(this, WelcomeScreen::class.java))
                        finish()
                    } else {
                        //show a message saying password is wrong
                        Toast.makeText(this, "Incorrect Password", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    //show a message saying the user does not exist
                    Toast.makeText(this, "user not found", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
