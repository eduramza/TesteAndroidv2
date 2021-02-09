package com.example.bankcleancodetest.presenter

import com.example.bankcleancodetest.BaseApplication
import com.example.bankcleancodetest.MainContract
import com.example.bankcleancodetest.convertToBRL
import com.example.bankcleancodetest.entity.UserResponse
import com.example.bankcleancodetest.view.LoginActivity
import ru.terrakok.cicerone.Router

class MainPresenter(private var view: MainContract.View?): MainContract.Presenter {

    private val router: Router? by lazy { BaseApplication.INSTANCE.cicerone.router }

    override fun fetchData() {
        TODO("Not yet implemented")
    }

    override fun onViewCreated(userData: UserResponse.UserAccount) {
        view?.showUserDetails(name = userData.name, account = userData.bankAccount,
            agency = userData.agency, balance = userData.balance.convertToBRL())
    }

    override fun onDestroy() {
        view = null
    }

    override fun logoutButtonClicked() {
        router?.navigateTo(LoginActivity.TAG)
    }
}