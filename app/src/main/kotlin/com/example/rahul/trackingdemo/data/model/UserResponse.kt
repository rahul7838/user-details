package com.example.rahul.trackingdemo.data.model

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
)

data class Picture(
        val large: String,
        val medium: String,
        val thumbnail: String
)

data class Dob(
        val age: Int,
        val date: String
)

data class Name(
        val first: String,
        val last: String,
        val title: String
) : Comparable<Name> {
    override fun compareTo(other: Name): Int {
        return this.first.compareTo(other.first)

    }
}
