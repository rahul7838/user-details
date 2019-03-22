package com.example.rahul.trackingdemo.setting

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.example.rahul.trackingdemo.R

class AppSettingFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {

        setPreferencesFromResource(R.xml.app_setting, rootKey)

    }
}