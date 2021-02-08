package com.example.bankcleancodetest

import java.util.regex.Pattern

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