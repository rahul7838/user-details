package com.example.rahul.trackingdemo.utils

import com.example.rahul.trackingdemo.data.model.Name
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

        fun formatDob(dob: String) : String {
//            var dob = list[position].dob.date
            var dob = dob.substring(0, 10)
            dob = dob.replace("-", "/")
            var reverseDOb = ""
            reverseDOb += dob.substring(8) + "/" + dob.substring(5, 7) + "/" + dob.substring(0, 4)
            return reverseDOb
        }

        fun formatName(name: Name) : String {
            var firstname = name.first
            firstname = firstname[0].toUpperCase().toString() + firstname.substring(1)
            var lastName = name.last
            lastName = lastName[0].toUpperCase().toString() + lastName.substring(1)
            val name = "$firstname $lastName"
                return name
        }

        fun formatPhoneNUmber(number: String) : String {
            val re = Regex("[^0-9]")

//            number = re.replace(number, "")

            var num = re.replace(number, "")
            if (number.length >= 10) {
                num = "${number.substring(0, 3)} ${number.substring(3, 6)} ${number.substring(7, 9)}XX"
            }
            return num
        }
    }
}