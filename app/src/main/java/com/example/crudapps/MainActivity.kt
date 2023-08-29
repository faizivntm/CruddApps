package com.example.crudapps

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.crudapps.databinding.ActivityMainBinding
import com.example.crudapps.ui.HomeActivity
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.hilt.android.HiltAndroidApp

private lateinit var binding: ActivityMainBinding

@HiltAndroidApp
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)

        binding.splashImage.postDelayed({
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)
    }
}