package com.example.bankcleancodetest.entity


import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("error")
    val error: Error,
    @SerializedName("userAccount")
    val userAccount: UserAccount
) {
    class Error(
    )

    data class UserAccount(
        @SerializedName("agency")
        val agency: String,
        @SerializedName("balance")
        val balance: Double,
        @SerializedName("bankAccount")
        val bankAccount: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("userId")
        val userId: Int
    )
}