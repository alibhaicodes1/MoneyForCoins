package com.moneyforcoins.app

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
class MainActivity : AppCompatActivity() {

    private lateinit var content: LinearLayout
    private lateinit var bottomBar: LinearLayout

    private val purple = Color.rgb(103, 80, 164)
    private val darkPurple = Color.rgb(79, 55, 139)
    private val gold = Color.rgb(255, 179, 0)
    private val background = Color.rgb(247, 247, 250)
    private val white = Color.WHITE
    private val darkText = Color.rgb(23, 23, 28)
    private val grayText = Color.rgb(112, 112, 120)
    private val green = Color.rgb(27, 158, 99)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = background
        window.navigationBarColor = background

        showHome()
    }

    private fun dp(value: Int): Int {
        return (value * resources.displayMetrics.density).toInt()
    }

    private fun text(
        value: String,
        size: Float,
        color: Int = darkText,
        bold: Boolean = false
    ): TextView {

        return TextView(this).apply {
            text = value
            textSize = size
            setTextColor(color)

            if (bold) {
                setTypeface(typeface, android.graphics.Typeface.BOLD)
            }

            setPadding(dp(4), dp(4), dp(4), dp(4))
        }
    }

    private fun roundedBackground(
        color: Int,
        radius: Int = 18
    ): GradientDrawable {

        return GradientDrawable().apply {
            setColor(color)
            cornerRadius = dp(radius).toFloat()
        }
    }

    private fun card(
        padding: Int = 18
    ): LinearLayout {

        return LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(padding), dp(padding), dp(padding), dp(padding))
            background = roundedBackground(white, 20)

            elevation = dp(3).toFloat()

            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, dp(8), 0, dp(8))
            }
        }
    }

    private fun button(
        title: String,
        onClick: () -> Unit
    ): MaterialButton {

        return MaterialButton(this).apply {

            text = title
            textSize = 14f
            isAllCaps = false

            setTextColor(white)
            backgroundTintList =
                android.content.res.ColorStateList.valueOf(purple)

            cornerRadius = dp(14)

            setOnClickListener {
                onClick()
            }

            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                dp(52)
            ).apply {
                setMargins(0, dp(6), 0, dp(6))
            }
        }
    }

    private fun setupScreen() {

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(this@MainActivity.background)
        }

        val scroll = ScrollView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                0,
                1f
            )
        }

        content = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(18), dp(18), dp(18), dp(18))
        }

        scroll.addView(content)

        bottomBar = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER
            setBackgroundColor(white)
            setPadding(dp(6), dp(8), dp(6), dp(8))

            elevation = dp(10).toFloat()

            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                dp(72)
            )
        }

        root.addView(scroll)
        root.addView(bottomBar)

        setContentView(root)
    }

    private fun addHeader(
        title: String,
        subtitle: String
    ) {

        val header = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
        }

        val left = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1f
            )
        }

        left.addView(text(title, 25f, darkText, true))
        left.addView(text(subtitle, 13f, grayText))

        val profile = TextView(this).apply {
            text = "👤"
            textSize = 25f
            gravity = Gravity.CENTER
            background = roundedBackground(Color.rgb(238, 232, 250), 50)

            layoutParams = LinearLayout.LayoutParams(
                dp(50),
                dp(50)
            )
        }

        profile.setOnClickListener {
            showProfile()
        }

        header.addView(left)
        header.addView(profile)

        content.addView(header)
    }

    private fun addBottomNavigation(selected: String) {

        bottomBar.removeAllViews()

        val items = listOf(
            "⌂\nHome" to "Home",
            "＋\nEarn" to "Earn",
            "🎁\nRewards" to "Rewards",
            "👤\nProfile" to "Profile"
        )

        items.forEach { item ->

            val nav = TextView(this).apply {
                text = item.first
                gravity = Gravity.CENTER
                textSize = 12f
                setPadding(dp(4), dp(4), dp(4), dp(4))

                if (item.second == selected) {
                    setTextColor(purple)
                    setTypeface(typeface, android.graphics.Typeface.BOLD)
                } else {
                    setTextColor(grayText)
                }

                layoutParams = LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    1f
                )
            }

            nav.setOnClickListener {

                when (item.second) {
                    "Home" -> showHome()
                    "Earn" -> showEarn()
                    "Rewards" -> showRewards()
                    "Profile" -> showProfile()
                }
            }

            bottomBar.addView(nav)
        }
    }

    private fun showHome() {

        setupScreen()

        addHeader(
            "Money for Coins",
            "Earn coins. Unlock rewards."
        )

        val balance = card(20)

        balance.background = roundedBackground(purple, 22)

        balance.addView(
            text(
                "YOUR COIN BALANCE",
                12f,
                Color.WHITE,
                true
            )
        )

        balance.addView(
            text(
                "2,450",
                38f,
                Color.WHITE,
                true
            )
        )

        balance.addView(
            text(
                "🪙 Coins",
                14f,
                Color.WHITE
            )
        )

        val redeem = button(
            "View Rewards"
        ) {
            showRewards()
        }

        balance.addView(redeem)

        content.addView(balance)

        content.addView(
            text(
                "Quick Actions",
                20f,
                darkText,
                true
            )
        )

        val quick = card()

        quick.addView(
            text(
                "Earn more coins every day",
                16f,
                darkText,
                true
            )
        )

        quick.addView(
            text(
                "Complete available activities and collect coins.",
                13f,
                grayText
            )
        )

        quick.addView(
            button("Start Earning") {
                showEarn()
            }
        )

        content.addView(quick)

        content.addView(
            text(
                "Today's Opportunities",
                20f,
                darkText,
                true
            )
        )

        addTaskCard(
            "🎬",
            "Watch & Earn",
            "Watch available videos",
            "+100 Coins"
        )

        addTaskCard(
            "📅",
            "Daily Check-in",
            "Check in once every day",
            "+50 Coins"
        )

        addTaskCard(
            "🧠",
            "Quick Quiz",
            "Answer today's quiz",
            "+150 Coins"
        )

        addTaskCard(
            "👥",
            "Invite Friends",
            "Share your referral",
            "+250 Coins"
        )

        content.addView(
            text(
                "Recent Activity",
                20f,
                darkText,
                true
            )
        )

        addHistoryRow(
            "Daily Check-in",
            "+50 Coins",
            "Today"
        )

        addHistoryRow(
            "Watch & Earn",
            "+100 Coins",
            "Today"
        )

        addHistoryRow(
            "Reward Redeem",
            "-1,000 Coins",
            "Yesterday"
        )

        addBottomNavigation("Home")
    }

    private fun addTaskCard(
        icon: String,
        title: String,
        description: String,
        coins: String
    ) {

        val task = card()

        val row = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
        }

        val iconView = text(icon, 28f)

        iconView.gravity = Gravity.CENTER
        iconView.background =
            roundedBackground(Color.rgb(242, 238, 250), 16)

        iconView.layoutParams = LinearLayout.LayoutParams(
            dp(55),
            dp(55)
        )

        val details = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL

            layoutParams = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1f
            ).apply {
                setMargins(dp(12), 0, dp(8), 0)
            }
        }

        details.addView(
            text(title, 16f, darkText, true)
        )

        details.addView(
            text(description, 12f, grayText)
        )

        val coinText = text(
            coins,
            13f,
            green,
            true
        )

        coinText.gravity = Gravity.CENTER

        row.addView(iconView)
        row.addView(details)
        row.addView(coinText)

        task.addView(row)

        task.setOnClickListener {
            Toast.makeText(
                this,
                "$title selected",
                Toast.LENGTH_SHORT
            ).show()
        }

        content.addView(task)
    }

    private fun addHistoryRow(
        title: String,
        amount: String,
        date: String
    ) {

        val row = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            setPadding(dp(14), dp(12), dp(14), dp(12))
            background = roundedBackground(white, 16)

            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, dp(5), 0, dp(5))
            }
        }

        val details = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL

            layoutParams = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1f
            )
        }

        details.addView(
            text(title, 14f, darkText, true)
        )

        details.addView(
            text(date, 11f, grayText)
        )

        row.addView(details)

        row.addView(
            text(amount, 13f, green, true)
        )

        content.addView(row)
    }

    private fun showEarn() {

        setupScreen()

        addHeader(
            "Earn Coins",
            "Choose an activity"
        )

        addTaskCard(
            "🎬",
            "Watch Videos",
            "Earn coins by completing available videos",
            "+100"
        )

        addTaskCard(
            "📅",
            "Daily Check-in",
            "Claim your daily bonus",
            "+50"
        )

        addTaskCard(
            "🧠",
            "Daily Quiz",
            "Complete today's quiz",
            "+150"
        )

        addTaskCard(
            "📋",
            "Complete Tasks",
            "Discover available earning tasks",
            "+200"
        )

        addTaskCard(
            "👥",
            "Refer Friends",
            "Invite friends and earn rewards",
            "+250"
        )

        addTaskCard(
            "⭐",
            "Special Offers",
            "Check available offers",
            "+500"
        )

        addBottomNavigation("Earn")
    }

    private fun showRewards() {

        setupScreen()

        addHeader(
            "Rewards",
            "Redeem your coins"
        )

        val info = card()

        info.addView(
            text(
                "Available Balance",
                13f,
                grayText
            )
        )

        info.addView(
            text(
                "2,450 Coins",
                28f,
                purple,
                true
            )
        )

        content.addView(info)

        content.addView(
            text(
                "Available Rewards",
                20f,
                darkText,
                true
            )
        )

        addRewardCard("₹10", "1,000 Coins")
        addRewardCard("₹50", "5,000 Coins")
        addRewardCard("₹100", "10,000 Coins")
        addRewardCard("₹250", "25,000 Coins")

        content.addView(
            text(
                "Note: Actual reward redemption will be connected to the secure backend and approved reward provider.",
                12f,
                grayText
            )
        )

        addBottomNavigation("Rewards")
    }

    private fun addRewardCard(
        reward: String,
        required: String
    ) {

        val rewardCard = card()

        val row = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
        }

        val amount = text(
            reward,
            28f,
            purple,
            true
        )

        amount.gravity = Gravity.CENTER

        amount.layoutParams = LinearLayout.LayoutParams(
            dp(80),
            dp(70)
        )

        val details = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL

            layoutParams = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1f
            ).apply {
                setMargins(dp(14), 0, dp(8), 0)
            }
        }

        details.addView(
            text(
                "Reward",
                12f,
                grayText
            )
        )

        details.addView(
            text(
                required,
                15f,
                darkText,
                true
            )
        )

        val redeem = MaterialButton(this).apply {

            text = "Redeem"
            isAllCaps = false
            textSize = 12f

            cornerRadius = dp(12)

            setTextColor(white)

            backgroundTintList =
                android.content.res.ColorStateList.valueOf(purple)

            setOnClickListener {
                Toast.makeText(
                    this@MainActivity,
                    "Redemption request will be processed after backend verification.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        row.addView(amount)
        row.addView(details)
        row.addView(redeem)

        rewardCard.addView(row)

        content.addView(rewardCard)
    }

    private fun showProfile() {

        setupScreen()

        addHeader(
            "My Profile",
            "Account & settings"
        )

        val profileCard = card()

        val avatar = text(
            "👤",
            45f
        )

        avatar.gravity = Gravity.CENTER
        avatar.background =
            roundedBackground(Color.rgb(238, 232, 250), 60)

        avatar.layoutParams = LinearLayout.LayoutParams(
            dp(85),
            dp(85)
        ).apply {
            gravity = Gravity.CENTER_HORIZONTAL
        }

        profileCard.addView(avatar)

        profileCard.addView(
            text(
                "Money for Coins User",
                20f,
                darkText,
                true
            ).apply {
                gravity = Gravity.CENTER
            }
        )

        profileCard.addView(
            text(
                "Account details will appear here",
                13f,
                grayText
            ).apply {
                gravity = Gravity.CENTER
            }
        )

        content.addView(profileCard)

        content.addView(
            text(
                "Account",
                20f,
                darkText,
                true
            )
        )

        content.addView(
            button("📜 Transaction History") {
                showHistory()
            }
        )

        content.addView(
            button("⚙️ Settings") {
                showSettings()
            }
        )

        content.addView(
    button("🔒 Logout") {
        FirebaseAuth.getInstance().signOut()

        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                Intent.FLAG_ACTIVITY_CLEAR_TASK

        startActivity(intent)
        finish()
    }
)

        addBottomNavigation("Profile")
    }

    private fun showHistory() {

        setupScreen()

        addHeader(
            "Transaction History",
            "Your recent activity"
        )

        addHistoryRow(
            "Daily Check-in",
            "+50 Coins",
            "Today"
        )

        addHistoryRow(
            "Watch & Earn",
            "+100 Coins",
            "Today"
        )

        addHistoryRow(
            "Quiz Completed",
            "+150 Coins",
            "Yesterday"
        )

        addHistoryRow(
            "Reward Redeem",
            "-1,000 Coins",
            "Yesterday"
        )

        addHistoryRow(
            "Referral Bonus",
            "+250 Coins",
            "2 days ago"
        )

        addBottomNavigation("Profile")
    }

    private fun showSettings() {

        setupScreen()

        addHeader(
            "Settings",
            "Manage your preferences"
        )

        val settings = card()

        settings.addView(
            text(
                "Notifications",
                16f,
                darkText,
                true
            )
        )

        settings.addView(
            text(
                "Receive updates about tasks and rewards",
                13f,
                grayText
            )
        )

        settings.addView(
            button("Notification Settings") {
                Toast.makeText(
                    this,
                    "Notification settings selected",
                    Toast.LENGTH_SHORT
                ).show()
            }
        )

        settings.addView(
            text(
                "Privacy & Security",
                16f,
                darkText,
                true
            )
        )

        settings.addView(
            text(
                "Your account security will be handled by the backend.",
                13f,
                grayText
            )
        )

        settings.addView(
            button("Privacy & Security") {
                Toast.makeText(
                    this,
                    "Privacy & Security selected",
                    Toast.LENGTH_SHORT
                ).show()
            }
        )

        settings.addView(
            text(
                "About Money for Coins",
                16f,
                darkText,
                true
            )
        )

        settings.addView(
            text(
                "Version 1.0",
                13f,
                grayText
            )
        )

        content.addView(settings)

        addBottomNavigation("Profile")
    }
}
