package com.example.bankcleancodetest.entity


import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("error")
    val error: Error,
    @SerializedName("userAccount")
    val userAccount: UserAccount
) {
    class Error(
        @SerializedName("code")
        var code: Int = 0,
        @SerializedName("message")
        val message: String
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
    ): Parcelable {
        constructor(parcel: Parcel) : this(
            parcel.readString().toString(),
            parcel.readDouble(),
            parcel.readString().toString(),
            parcel.readString().toString(),
            parcel.readInt()
        ) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(agency)
            parcel.writeDouble(balance)
            parcel.writeString(bankAccount)
            parcel.writeString(name)
            parcel.writeInt(userId)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<UserAccount> {
            override fun createFromParcel(parcel: Parcel): UserAccount {
                return UserAccount(parcel)
            }

            override fun newArray(size: Int): Array<UserAccount?> {
                return arrayOfNulls(size)
            }
        }
    }
}