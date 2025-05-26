package com.example.pomodoro

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.json.JSONObject

class register_page : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register_page)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val backToLoginButton = findViewById<AppCompatButton>(R.id.backToLoginButton)
        //set click listener for the button "back to login" and direct to login
        backToLoginButton.setOnClickListener {
            startActivity(Intent(this, login_page::class.java))
            finish()
        }


        //find textview and button by their IDs
        val usernameEditText = findViewById<EditText>(R.id.usernametext)
        val emailEditText = findViewById<EditText>(R.id.emailtext)
        val passwordEditText = findViewById<EditText>(R.id.passwordtext)
        val registerButton = findViewById<AppCompatButton>(R.id.registerbutton)

        //set register button click listener
        registerButton.setOnClickListener {
            val username = usernameEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            //check if the credentials are provided and add it to shared preferences
            if (username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                val sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()

                //get existing users from sharedPreferences
                val userJson = sharedPreferences.getString("users", "{}")
                val users = JSONObject(userJson ?: "{}")

                //check if username already exists
                if (users.has(username)) {
                    Toast.makeText(this, "User already exists!", Toast.LENGTH_SHORT).show()
                } else {
                    //if not, create a new JSON object to store user's email and password
                    val userData = JSONObject()
                    userData.put("email", email)
                    userData.put("password", password)
                    //add the new user to the JSOn object
                    users.put(username, userData)

                    editor.putString("users", users.toString())
                    editor.apply()

                    //message saying registration is successful
                    Toast.makeText(this, "Registration success, going to login", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, login_page::class.java))
                    finish()

                }
                } else {
                    //display a message saying one or more fields are empty
                    Toast.makeText(this, "Please fill all the fields.", Toast.LENGTH_SHORT).show()
                }

            }
        }

    }
