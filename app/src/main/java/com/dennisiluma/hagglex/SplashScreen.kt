package com.dennisiluma.hagglex

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        //Delay splash screen for 3 seconds the launch MainActivity
        Handler(Looper.getMainLooper())
            .postDelayed(
                { startActivity(Intent(this, MainActivity::class.java)) },
                3000
            )
        //kill this splash activity once  MainActivity is launched
        Handler(Looper.getMainLooper()).postDelayed({ this.finish() }, 5000)

    }
}