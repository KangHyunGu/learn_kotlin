package com.example.activityandfragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class TwoColorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_two_color)

        settingButtons()
    }

    fun settingButtons() {
        val button_red = findViewById<Button>(R.id.button_red_fragment)
        val button_blue = findViewById<Button>(R.id.button_blue_fragment)

        // button_red
        button_red.setOnClickListener {
            val fragmentTransection = supportFragmentManager.beginTransaction()
            fragmentTransection.replace(R.id.frame_layout, RedFragment())
            fragmentTransection.commit()
        }

        // button_blue
        button_blue.setOnClickListener {
           val fragmentTransection = supportFragmentManager.beginTransaction()
            fragmentTransection.replace(R.id.frame_layout, BlueFragment())
            fragmentTransection.commit()
        }
    }
}