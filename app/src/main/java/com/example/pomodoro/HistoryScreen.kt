package com.example.pomodoro

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton


class HistoryScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_history_screen) //layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Bottom navigation menu
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.itemIconTintList = null //remove IconTint
        bottomNav.itemTextColor = null //remove default text color

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, HomeScreen::class.java))
                    //click homescreen icon to go to home screen
                    true
                }
                R.id.nav_history -> {
                    //already in history screen
                    true
                }
                else -> false
            }
        }

        //retrieve stats from sharedPreferences
        val prefs = getSharedPreferences("PomodoroStats", MODE_PRIVATE)
        val focusCount = prefs.getInt("FocusCount", 0)
        val breakCount = prefs.getInt("BreakCount", 0)

        //create the message string
        val statsMessage = """
            Focus Sessions Completed: $focusCount
            Breaks Taken: $breakCount
        """.trimIndent()


        //find the textview that displays the stats and set its text
        val statsTextView = findViewById<TextView>(R.id.statsTextView)
        statsTextView.text = "Focus Sessions Completed: $focusCount"

        //find the button that will show the stats
        val showStatsButton = findViewById<AppCompatButton>(R.id.showStatsButton)
        showStatsButton.setOnClickListener {
            val inflater = layoutInflater
            val dialogView = inflater.inflate(R.layout.custom_dialog, null)

            //find the textviews and buttos inside the dialog
            val titleTextView = dialogView.findViewById<TextView>(R.id.dialogTitle)
            val messageTextView = dialogView.findViewById<TextView>(R.id.dialogMessage)
            val okButton = dialogView.findViewById<AppCompatButton>(R.id.dialogOkButton)

            //set title and mesages inside the dialog
            titleTextView.text = "Your Stats"
            messageTextView.text = "Focus Sessions Completed: $focusCount\nBreaks Taken: $breakCount"

            //create the alertdialog
            val alertDialog = AlertDialog.Builder(this)
                .setView(dialogView)
                .create()

            //click listener for the ok button when clicked
            okButton.setOnClickListener {
                alertDialog.dismiss()
            }

            // show the dialog
            alertDialog.show()
        }


    }
}


