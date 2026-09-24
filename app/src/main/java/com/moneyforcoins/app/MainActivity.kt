package com.moneyforcoins.app

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val textView = TextView(this).apply {
            text = "Money for Coins\n\nWelcome!"
            textSize = 28f
            setPadding(40, 80, 40, 40)
        }

        setContentView(textView)
    }
}
