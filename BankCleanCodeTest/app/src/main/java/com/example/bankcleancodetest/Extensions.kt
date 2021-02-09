package com.example.bankcleancodetest

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

/******** String ************/
fun String.isCPF(): Boolean{
    val parsing = this.toLongOrNull()
    return this.length == 11 && parsing != null
}

fun String.isEmail() = this.contains("@") && this.contains(".com")

fun String.validPassword(): Boolean {
    val validInputs = "^(?=.*[a-z])" + //for lowercase
                        "(?=.*[A-Z])" + //for uppercase
                        "(?=.*\\d)" + // for numeric
                        "(?=.*[-+_!@#$%^&*., ?]).+$" //for special
    val pattern = Pattern.compile(validInputs)
    return pattern.matcher(this).matches()
}

fun String.formatDate(): String {
    val parser =  SimpleDateFormat("yyyy-MM-dd")
    val formatter = SimpleDateFormat("dd/MM/yyyy")
    return formatter.format(parser.parse(this))

}

fun String.formatAccountNumber(): String {
    val builder = StringBuilder(this)
    builder.insert(2, '.')
    builder.insert(this.length -1, '-')
    return builder.toString()
}

/******* Double ******/
fun Double.convertToBRL(): String{
    val ptBr = Locale("pt", "BR")
    return NumberFormat.getCurrencyInstance(ptBr).format(this)
}