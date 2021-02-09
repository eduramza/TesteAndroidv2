package com.example.bankcleancodetest.presenter

import android.util.Log
import com.example.bankcleancodetest.BaseApplication
import com.example.bankcleancodetest.MainContract
import com.example.bankcleancodetest.convertToBRL
import com.example.bankcleancodetest.entity.InvoiceExtract
import com.example.bankcleancodetest.entity.UserResponse
import com.example.bankcleancodetest.interactor.MainInteractor
import com.example.bankcleancodetest.view.LoginActivity
import com.github.kittinunf.result.Result
import com.google.gson.Gson
import ru.terrakok.cicerone.Router

class MainPresenter(private var view: MainContract.View?): MainContract.Presenter {

    private var interactor: MainContract.Interactor? = MainInteractor()
    private val router: Router? by lazy { BaseApplication.INSTANCE.cicerone.router }

    override fun onViewCreated(userData: UserResponse.UserAccount) {
        view?.showLoading()
        buildHeaderDetails(userData)
        fetchData(userData.userId)
    }

    private fun buildHeaderDetails(userData: UserResponse.UserAccount) {
        view?.showUserDetails(name = userData.name, account = userData.bankAccount,
            agency = userData.agency, balance = userData.balance.convertToBRL())
    }

    private fun fetchData(userId: Int) {
        interactor?.loadInvoiceExtract(userId) { result ->
            when(result){
                is Result.Failure -> { thrownErrorOnRequest() }
                is Result.Success -> {
                    val responseObj = result.get().obj()
                    val response: InvoiceExtract =
                        Gson().fromJson(responseObj.toString(), InvoiceExtract::class.java)
                    this.onRequestSuccess(response)
                }
            }
        }
    }

    override fun onDestroy() {
        view = null
        interactor = null
    }

    override fun logoutButtonClicked() {
        router?.navigateTo(LoginActivity.TAG)
    }

    private fun thrownErrorOnRequest() {
        view?.hideLoading()
        Log.e("MainPresenter", "Erro ao executar a request")
        //TODO implement simple error message
    }

    private fun onRequestSuccess(response: InvoiceExtract) {
        view?.publishDataList(response.statementList)
        view?.hideLoading()
    }
}