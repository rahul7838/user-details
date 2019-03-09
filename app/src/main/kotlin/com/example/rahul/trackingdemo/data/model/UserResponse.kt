package com.example.rahul.trackingdemo.data.model

import android.os.Parcel
import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil

data class UserResponse(
        val info: Info,
        val results: List<Result>
)

data class Info(
        val page: Int,
        val results: Int,
        val seed: String,
        val version: String
)

data class Result(
        val dob: Dob,
        val email: String,
        val name: Name,
        var phone: String,
        val picture: Picture
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readParcelable(Dob::class.java.classLoader),
            parcel.readString(),
            parcel.readParcelable(Name::class.java.classLoader),
            parcel.readString(),
            parcel.readParcelable(Picture::class.java.classLoader))

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(dob, flags)
        parcel.writeString(email)
        parcel.writeParcelable(name, flags)
        parcel.writeString(phone)
        parcel.writeParcelable(picture, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    //    companion object CREATOR : Parcelable.Creator<Result> {
//        override fun createFromParcel(parcel: Parcel): Result {
//            return Result(parcel)
//        }
//
//        override fun newArray(size: Int): Array<Result?> {
//            return arrayOfNulls(size)
//        }
//    }
    companion object {
        val creater = object : Parcelable.Creator<Result> {
            override fun createFromParcel(source: Parcel): Result {
                return Result(source)
            }

            override fun newArray(size: Int): Array<Result?> {
                return arrayOfNulls(size)
            }
        }
    }
}

    data class Picture(
            val large: String,
            val medium: String,
            val thumbnail: String
    ) : Parcelable {
        constructor(parcel: Parcel) : this(
                parcel.readString(),
                parcel.readString(),
                parcel.readString()) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(large)
            parcel.writeString(medium)
            parcel.writeString(thumbnail)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Picture> {
            override fun createFromParcel(parcel: Parcel): Picture {
                return Picture(parcel)
            }

            override fun newArray(size: Int): Array<Picture?> {
                return arrayOfNulls(size)
            }
        }
    }

    data class Dob(
            val age: Int,
            val date: String?
    ) : Parcelable {
        constructor(parcel: Parcel) : this(
                parcel.readInt(),
                parcel.readString())

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeInt(age)
            parcel.writeString(date)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Dob> {
            override fun createFromParcel(parcel: Parcel): Dob {
                return Dob(parcel)
            }

            override fun newArray(size: Int): Array<Dob?> {
                return arrayOfNulls(size)
            }
        }
    }

    data class Name(
            val first: String,
            val last: String,
            val title: String
    ) : Comparable<Name>, Parcelable {
        constructor(parcel: Parcel) : this(
                parcel.readString(),
                parcel.readString(),
                parcel.readString())

        override fun compareTo(other: Name): Int {
            return this.first.compareTo(other.first)

        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(first)
            parcel.writeString(last)
            parcel.writeString(title)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Name> {
            override fun createFromParcel(parcel: Parcel): Name {
                return Name(parcel)
            }

            override fun newArray(size: Int): Array<Name?> {
                return arrayOfNulls(size)
            }
        }
    }

