package com.example.bankcleancodetest.interactor

import com.example.bankcleancodetest.Constants.Companion.BASE_LOGIN_URL
import com.example.bankcleancodetest.LoginContract
import com.github.kittinunf.fuel.android.core.Json
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result

class LoginInteractor: LoginContract.InteractorLogin {

    override fun loadUserLogged(
        username: String,
        password: String,
        interactorOutput: (result: Result<Json, FuelError>) -> Unit
    ) {
        val request = """{ "user": "$username", "password": "$password" }"""
        BASE_LOGIN_URL.httpPost()
            .header("Content-Type" to "application/json")
            .body(request).responseJson {_, _, result ->
            interactorOutput(result)
        }
    }
}