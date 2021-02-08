package com.example.bankcleancodetest.presenter

import android.util.Log
import com.example.bankcleancodetest.LoginContract
import com.example.bankcleancodetest.entity.UserResponse
import com.example.bankcleancodetest.interactor.LoginInteractor
import com.example.bankcleancodetest.isCPF
import com.example.bankcleancodetest.isEmail
import com.example.bankcleancodetest.validPassword
import com.github.kittinunf.result.Result
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class LoginPresenter(private var view: LoginContract.View?): LoginContract.Presenter,
    LoginContract.InteractorOutput {

    private var interactor:LoginContract.Interactor? = LoginInteractor()

    override fun loginButtonClick(username: String, password: String) {
        view?.showLoading()
        if ( username.isCPF() || username.isEmail() ){
            if (password.validPassword()){
                interactor?.loadUserLogged(username, password) { result ->
                    when(result){
                        is Result.Failure -> { this.onLoginError() }
                        is Result.Success -> {
                            val userObj = result.get().obj()
                            val type = object: TypeToken<UserResponse>() {}.type
                            //Problemas para converter a resposta
                            val response: UserResponse =
                                Gson().fromJson(userObj.getJSONObject("userAccount").toString(), type)

                            this.onLoginSuccess(response)
                        }
                    }

                }
            } else {
                view?.showInvalidPassword()
            }
        } else {
            view?.showInvalidUsername()
        }
    }

    override fun onViewCreated() {
        view?.showLoading()
        //verificar se o usuário ta logado via shared preferences
    }

    override fun onDestoy() {
        view = null
        interactor = null
    }

    override fun onLoginSuccess(data: UserResponse) {
        view?.hideLoading()
        Log.d("Login", "Sucesso")
        //validar se a resposta veio no objeto de erro e enganou-nos
        //publicar o sucesso ou abrir a proxima tela
    }

    override fun onLoginError() {
        view?.hideLoading()
        Log.d("Login", "Problema no login")

    }

}