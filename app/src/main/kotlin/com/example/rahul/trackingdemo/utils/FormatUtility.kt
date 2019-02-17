package com.example.rahul.trackingdemo.utils

import android.telephony.PhoneNumberUtils

class FormatUtility {

    companion object {
        fun formatNumber(list: ArrayList<String>) : ArrayList<String> {
            var formattedList: ArrayList<Int> = ArrayList()
            val re = Regex("[^0-9]")
            for (i in 0 until list.size){
                 list[i] = re.replace(list[i], "")
            }

            return list
        }
    }
}