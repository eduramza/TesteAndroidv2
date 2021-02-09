package com.example.bankcleancodetest.presenter

import com.example.bankcleancodetest.*
import com.example.bankcleancodetest.Constants.Companion.DEFAULT_ERROR_CODE
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
                }
            }

        }
    }

    override fun onViewCreated() {
        view?.showLoading()
        //TODO verificar se o usuário ta logado via shared preferences
    }

    override fun onDestoy() {
        view = null
        interactorLogin = null
    }

    override fun onRequestSuccess(data: UserResponse) {
        view?.hideLoading()
        if ( !responseWithError(data.error) ){
            saveUserInSession(data.userAccount)
            router?.navigateTo(MainActivity.TAG, data.userAccount)
        } else {
            view?.showErrorLoginRequest(data.error.message)
        }
    }

    private fun responseWithError(error: UserResponse.Error) = error.code != DEFAULT_ERROR_CODE

    private fun saveUserInSession(userAccount: UserResponse.UserAccount){
        //TODO Save user using shared preferences
    }

    override fun onRequestError() {
        view?.hideLoading()
        //TODO case request dont return 200
    }

}