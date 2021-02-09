package com.example.bankcleancodetest

import com.example.bankcleancodetest.entity.UserResponse
import com.github.kittinunf.fuel.android.core.Json
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.result.Result

interface LoginContract {

    interface View {
        fun showLoading()
        fun hideLoading()
        fun showErrorInvalidPassword()
        fun showErrorInvalidUsername()
        fun showErrorLoginRequest(message: String)
    }

    interface Presenter {
        fun loginButtonClick(username: String, password: String)
        fun onViewCreated()
        fun onDestoy()
    }

    interface InteractorLogin{
        fun loadUserLogged(username: String, password: String, interactorOutput: (result: Result<Json, FuelError>) -> Unit)
    }

    interface InteractorLoginOutput{
        fun onRequestSuccess(data: UserResponse)
        fun onRequestError()
    }
}