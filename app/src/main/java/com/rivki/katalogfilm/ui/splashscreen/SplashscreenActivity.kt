package com.rivki.katalogfilm.ui.splashscreen

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowInsets
import android.view.WindowManager
import com.rivki.katalogfilm.R
import com.rivki.katalogfilm.base.BaseActivity
import com.rivki.katalogfilm.databinding.ActivitySplashscreenBinding
import com.rivki.katalogfilm.ui.home.HomeActivity

class SplashscreenActivity : BaseActivity() {
    private lateinit var splashscreenBinding: ActivitySplashscreenBinding
    override fun onSetupLayout(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_splashscreen)
        splashscreenBinding = ActivitySplashscreenBinding.inflate(layoutInflater)
        setContentView(splashscreenBinding.root)
    }

    override fun onViewReady(savedInstanceState: Bundle?) {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this@SplashscreenActivity, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }

    override fun observeData() {}
}