package com.example.rahul.trackingdemo.utils

import android.telephony.PhoneNumberUtils
import com.example.rahul.trackingdemo.data.model.Result

class FormatUtility {

    companion object {
        fun formatNumber(list: ArrayList<Result>) : ArrayList<Result> {
            val re = Regex("[^0-9]")
            for (i in 0 until list.size){
                 list[i].phone = re.replace(list[i].phone, "")
            }
            return list
        }
    }
}