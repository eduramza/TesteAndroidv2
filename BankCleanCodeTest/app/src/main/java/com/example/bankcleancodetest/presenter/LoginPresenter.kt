package com.example.bankcleancodetest.presenter

import com.example.bankcleancodetest.LoginContract
import com.example.bankcleancodetest.entity.UserResponse
import com.example.bankcleancodetest.interactor.LoginInteractor
import com.example.bankcleancodetest.isCPF
import com.example.bankcleancodetest.isEmail
import com.example.bankcleancodetest.validPassword
import com.github.kittinunf.result.Result
import com.google.gson.Gson

const val DEFAULT_ERROR_CODE = 0

class LoginPresenter(private var view: LoginContract.View?): LoginContract.Presenter,
    LoginContract.InteractorOutput {

    private var interactor:LoginContract.Interactor? = LoginInteractor()

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
        interactor?.loadUserLogged(username, password) { result ->
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
        interactor = null
    }

    override fun onRequestSuccess(data: UserResponse) {
        view?.hideLoading()
        if ( !responseWithError(data.error) ){
            saveUserInSession(data.userAccount)
            //TODO view open new activity
        } else {
            view?.showErrorLoginRequest(data.error.message)
        }
    }

    private fun responseWithError(error: UserResponse.Error) = error.code != DEFAULT_ERROR_CODE

    private fun saveUserInSession(userAccount: UserResponse.UserAccount){

    }

    override fun onRequestError() {
        view?.hideLoading()
        //TODO case request dont return 200
    }

}