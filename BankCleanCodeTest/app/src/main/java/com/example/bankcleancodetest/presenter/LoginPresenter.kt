package com.example.bankcleancodetest.presenter

import android.content.SharedPreferences
import com.example.bankcleancodetest.*
import com.example.bankcleancodetest.Constants.Companion.DEFAULT_ERROR_CODE
import com.example.bankcleancodetest.Constants.Companion.SHARED_PASSWORD_KEY
import com.example.bankcleancodetest.Constants.Companion.SHARED_USERNAME_KEY
import com.example.bankcleancodetest.entity.UserResponse
import com.example.bankcleancodetest.interactor.LoginInteractor
import com.example.bankcleancodetest.view.MainActivity
import com.github.kittinunf.result.Result
import com.google.gson.Gson
import ru.terrakok.cicerone.Router

class LoginPresenter(private var view: LoginContract.View?): LoginContract.Presenter,
    LoginContract.InteractorLoginOutput {

    private var interactorLogin: LoginContract.InteractorLogin? = LoginInteractor()
    private val router: Router? by lazy { BaseApplication.INSTANCE.cicerone.router }
    private var preferences: SharedPreferences? = null

    override fun loginButtonClick(username: String, password: String) {
        view?.showLoading()
        if ( username.isCPF() || username.isEmail() ){
            if (password.validPassword()){
                doLoginRequest(username, password)
            } else {
                view?.showErrorInvalidPassword()
            }
        } else {
            view?.showErrorInvalidUsername()
        }
    }

    private fun doLoginRequest(username: String, password: String) {
        interactorLogin?.loadUserLogged(username, password) { result ->
            when(result){
                is Result.Failure -> { this.onRequestError() }
                is Result.Success -> {
                    val userObj = result.get().obj()
                    val response: UserResponse =
                        Gson().fromJson(userObj.toString(), UserResponse::class.java)

                    this.onRequestSuccess(response)
                    saveLoginData(username, password)
                }
            }

        }
    }

    private fun saveLoginData(username: String, password: String) {
        val editor = preferences?.edit()
        editor?.putString(SHARED_USERNAME_KEY, username)
        editor?.putString(SHARED_PASSWORD_KEY, password)
        editor?.apply()
    }

    override fun onViewCreated(sharedPref: SharedPreferences?) {
        view?.showLoading()
        preferences = sharedPref
        verifyUserIsSaved()
        view?.hideLoading()
    }

    private fun verifyUserIsSaved(){
        val sharedUsername = preferences?.getString(SHARED_USERNAME_KEY,"")
        val sharedPassword = preferences?.getString(SHARED_PASSWORD_KEY, "")
        if ( !sharedUsername.isNullOrBlank() && !sharedPassword.isNullOrBlank()){
            view?.setUserData(sharedUsername, sharedPassword)
        }
    }

    override fun onDestoy() {
        view = null
        interactorLogin = null
    }

    override fun onRequestSuccess(data: UserResponse) {
        view?.hideLoading()
        if ( !responseWithError(data.error) ){
            openMainActivity(data.userAccount)
        } else {
            view?.showErrorLoginRequest(data.error.message)
        }
    }

    private fun openMainActivity(userAccount: UserResponse.UserAccount) {
        router?.navigateTo(MainActivity.TAG, userAccount)
    }

    private fun responseWithError(error: UserResponse.Error) = error.code != DEFAULT_ERROR_CODE

    override fun onRequestError() {
        view?.hideLoading()
        //TODO case request dont return 200
    }

}