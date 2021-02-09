package com.example.bankcleancodetest.entity

import com.google.gson.annotations.SerializedName

data class InvoiceExtract(
    @SerializedName("error")
    val error: Error,
    @SerializedName("statementList")
    val statementList: List<Statement>
)  {

    data class Error(
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String
    )

    data class Statement(
        @SerializedName("date")
        val date: String,
        @SerializedName("desc")
        val desc: String,
        @SerializedName("title")
        val title: String,
        @SerializedName("value")
        val value: Double
    )

}