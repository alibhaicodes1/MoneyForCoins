package com.moneyforcoins.app

import android.content.Intent
import android.os.Bundle
import android.graphics.Color
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()

        // Already logged in
        if (auth.currentUser != null) {
            openHome()
            return
        }

        setupLoginScreen()
    }

    private fun setupLoginScreen() {

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(40, 40, 40, 40)
            setBackgroundColor(Color.rgb(247, 247, 250))
        }

        val logo = TextView(this).apply {
            text = "🪙"
            textSize = 55f
            gravity = Gravity.CENTER
        }

        val title = TextView(this).apply {
            text = "Money for Coins"
            textSize = 28f
            setTextColor(Color.rgb(103, 80, 164))
            gravity = Gravity.CENTER
        }

        val subtitle = TextView(this).apply {
            text = "Login to continue"
            textSize = 16f
            setTextColor(Color.DKGRAY)
            gravity = Gravity.CENTER
        }

        val emailInput = EditText(this).apply {
            hint = "Email"
            inputType = android.text.InputType.TYPE_CLASS_TEXT or
                    android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        }

        val passwordInput = EditText(this).apply {
            hint = "Password"
            inputType = android.text.InputType.TYPE_CLASS_TEXT or
                    android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
        }

        val loginButton = Button(this).apply {
            text = "LOGIN"
        }

        val signupButton = Button(this).apply {
            text = "CREATE ACCOUNT"
        }

        root.addView(logo)
        root.addView(title)
        root.addView(subtitle)

        addSpace(root, 30)

        root.addView(emailInput)
        root.addView(passwordInput)

        addSpace(root, 20)

        root.addView(loginButton)
        root.addView(signupButton)

        setContentView(root)

        loginButton.setOnClickListener {

            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(
                    this,
                    "Email and password required",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->

                    if (task.isSuccessful) {
                        Toast.makeText(
                            this,
                            "Login successful",
                            Toast.LENGTH_SHORT
                        ).show()

                        openHome()
                    } else {
                        Toast.makeText(
                            this,
                            task.exception?.message ?: "Login failed",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }

        signupButton.setOnClickListener {

            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(
                    this,
                    "Enter email and password",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            if (password.length < 6) {
                Toast.makeText(
                    this,
                    "Password must be at least 6 characters",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->

                    if (task.isSuccessful) {
                        Toast.makeText(
                            this,
                            "Account created successfully",
                            Toast.LENGTH_SHORT
                        ).show()

                        openHome()
                    } else {
                        Toast.makeText(
                            this,
                            task.exception?.message ?: "Signup failed",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }
    }

    private fun addSpace(root: LinearLayout, height: Int) {
        val space = TextView(this)
        space.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            height
        )
        root.addView(space)
    }

    private fun openHome() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
