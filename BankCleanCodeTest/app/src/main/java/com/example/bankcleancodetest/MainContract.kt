package com.example.bankcleancodetest

import com.example.bankcleancodetest.entity.InvoiceExtract
import com.example.bankcleancodetest.entity.UserResponse
import com.github.kittinunf.fuel.android.core.Json
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.result.Result

interface MainContract {

    interface View{
        fun doLogoff()
        fun showLoading()
        fun hideLoading()
        fun showUserDetails(name: String, account: String, agency:String, balance: String)
        fun publishDataList(data: List<InvoiceExtract.Statement>)
    }

    interface Presenter {
        fun onViewCreated(userData: UserResponse.UserAccount)
        fun onDestroy()
        //User actions
        fun logoutButtonClicked()
    }

    interface Interactor {
        fun loadInvoiceExtract(userId: Int,
                               interactorMainOutput: (result: Result<Json, FuelError>) -> Unit)
    }

    interface InteractorMainOutput{
        fun onSuccess(data: InvoiceExtract)
        fun onEFailure()
    }
}