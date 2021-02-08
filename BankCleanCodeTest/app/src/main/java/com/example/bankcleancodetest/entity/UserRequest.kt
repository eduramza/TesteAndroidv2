package com.example.bankcleancodetest.entity

import com.google.gson.annotations.SerializedName

data class UserRequest (
    @SerializedName("user")
    val user: String,
    @SerializedName("password")
    val password: String
)