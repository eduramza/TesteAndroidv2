package com.example.bankcleancodetest

import com.example.bankcleancodetest.entity.UserResponse

interface MainContract {

    interface View{
        fun doLogoff()
        fun showLoading()
        fun hideLoading()
        fun showUserDetails(name: String, account: String, agency:String, balance: String)
    }

    interface Presenter {
        fun fetchData()
        fun onViewCreated(userData: UserResponse.UserAccount)
        fun onDestroy()
        fun logoutButtonClicked()
    }
}