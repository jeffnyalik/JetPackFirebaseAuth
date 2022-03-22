package com.example.logisticsapp.data

import androidx.annotation.VisibleForTesting

data class UserData(
    var userId: String? = null,
    var name: String? = null,
    var username: String? = null,
    var emailAddress: String? = null,
    var bio: String? = null,
    var imageurl: String? = null,
){
    fun toMap() = mapOf(
        "userId" to userId,
        "name" to name,
        "username" to username,
        "bio" to bio,
        "imageurl" to imageurl
    )
}
