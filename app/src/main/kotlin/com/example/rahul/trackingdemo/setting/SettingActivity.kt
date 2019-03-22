package com.example.rahul.trackingdemo.setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.rahul.trackingdemo.R

class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.setting_container)
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.setting_container, AppSettingFragment())
                .commit()
    }
}