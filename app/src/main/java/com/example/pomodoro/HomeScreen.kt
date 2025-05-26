package com.example.pomodoro

import android.os.Bundle
import android.os.CountDownTimer
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.content.Intent


class HomeScreen : AppCompatActivity() {

    //UI elements references
    private lateinit var timerTextView: TextView
    private lateinit var playButton: ImageButton
    private lateinit var pauseButton: ImageButton
    private lateinit var focusButton: Button
    private lateinit var breakButton: Button

    //variables for the timer
    private var isRunning = false
    private var isFocusMode = true
    private var millisLeft: Long = 25 * 60 * 1000L // 25 mins
    private var timer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        //find the UI elements according to their IDs
        timerTextView = findViewById(R.id.timerTextView)
        playButton = findViewById(R.id.playButton)
        pauseButton = findViewById(R.id.pauseButton)
        focusButton = findViewById(R.id.focusButton)
        breakButton = findViewById(R.id.breakButton)

        //default timer
        updateTimerText()

        //click the play button to play the timer
        playButton.setOnClickListener {
            if (!isRunning) startTimer()
        }

        //pause timer to pause the time
        pauseButton.setOnClickListener{
            pauseTimer()
        }

        //focus button to choose focus mode
        focusButton.setOnClickListener {
            switchToFocusMode()
        }

        //break button to choose break mode
        breakButton.setOnClickListener {
            switchToBreakMode()
        }



        //bottom navigation menu
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.itemIconTintList = null
        bottomNav.itemTextColor = null

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                //already in homescreen
                R.id.nav_home -> {
                    true
                }
                R.id.nav_history -> {
                    //click history button if you want to go to history screen
                    startActivity(Intent(this, HistoryScreen::class.java))
                    true
                }
                else -> false
            }



        }

    }

    //starts or resumes the timer
    private fun startTimer() {
        timer = object : CountDownTimer(millisLeft, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                millisLeft = millisUntilFinished //update the time left
                updateTimerText() //refresh the timer display
            }

            override fun onFinish() {
                isRunning = false
                if (isFocusMode) {
                    incrementFocusCount()
                    switchToBreakMode() //automatically switch to break mode
                } else {
                    incrementBreakCount()
                    switchToFocusMode() //automatically switch to focus mode
                }
                startTimer() //restart timer for a new timer automatically
            }

        }.start()
        isRunning = true

    }

    //to pause the timer
    private fun pauseTimer() {
        timer?.cancel()
        isRunning = false
    }

    //switch timer to focus mode
    private fun switchToFocusMode() {
        timer?.cancel()
        isRunning = false
        isFocusMode = true
        millisLeft = 25 * 60 * 1000L //reset the time to 25 mins again after timer ends
        updateTimerText()
    }

    //switch timer ot break mode
    private fun switchToBreakMode() {
        timer?.cancel()
        isRunning = false
        isFocusMode = false
        millisLeft = 5 * 60 * 1000L //reset timer to 5 mins after break timer ends
        updateTimerText()
    }

    // this will update and display what is the current time
    private fun updateTimerText() {
        val minutes = (millisLeft / 1000) / 60
        val seconds = (millisLeft / 1000) % 60
        timerTextView.text = String.format("%02d:%02d", minutes, seconds)
    }

    //increment the stored focus session count in SharedPreferences
    private fun incrementFocusCount() {
        val prefs = getSharedPreferences("PomodoroStats", MODE_PRIVATE)
        val currentCount = prefs.getInt("FocusCount", 0)
        val newCount = currentCount + 1
        prefs.edit().putInt("FocusCount", newCount).apply()
    }

    //increment the stored break session count in SharedPreferences
    private fun incrementBreakCount() {
        val prefs = getSharedPreferences("PomodoroStats", MODE_PRIVATE)
        val currentCount = prefs.getInt("BreakCount", 0)
        val newCount = currentCount + 1
        prefs.edit().putInt("BreakCount", newCount).apply()
    }




}