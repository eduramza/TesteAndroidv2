package com.example.bankcleancodetest.presenter

import android.view.View
import com.example.bankcleancodetest.LoginContract
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class LoginPresenterTest{

    private lateinit var presenter: LoginPresenter
    private val view = LoginControllerTest()
    @Mock
    private lateinit var interactorLogin: LoginContract.InteractorLogin

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
        presenter = LoginPresenter(view, interactorLogin)
    }

    @Test
    fun should_return_false_when_username_not_an_cpf(){
        val usernameInput= "1236asdeds"

        presenter.loginButtonClick("a", "anyString()")
        verify(view.showLoading())
    }
}

class LoginControllerTest: LoginContract.View{
    override fun showLoading() {
        //TODO("Not yet implemented")
    }

    override fun hideLoading() {
        //TODO("Not yet implemented")
    }

    override fun showErrorInvalidPassword() {
        //TODO("Not yet implemented")
    }

    override fun showErrorInvalidUsername() {
        //TODO("Not yet implemented")
    }

    override fun showErrorLoginRequest(message: String) {
        //TODO("Not yet implemented")
    }

    override fun setUserData(sharedUsername: String, sharedPassword: String) {
        //TODO("Not yet implemented")
    }

}