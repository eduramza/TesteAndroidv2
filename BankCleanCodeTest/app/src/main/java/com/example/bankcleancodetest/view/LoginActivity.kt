package com.example.bankcleancodetest.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.example.bankcleancodetest.BaseApplication
import com.example.bankcleancodetest.LoginContract
import com.example.bankcleancodetest.R
import com.example.bankcleancodetest.entity.UserResponse
import com.example.bankcleancodetest.presenter.LoginPresenter
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_login.*
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Forward

class LoginActivity : AppCompatActivity(), LoginContract.View {

    companion object {
        val TAG = "LoginActivity"
    }

    private val navigator: Navigator? by lazy {
        object : Navigator {
            override fun applyCommand(command: Command) {
                if (command is Forward) {
                    forward(command)
                }
            }

            private fun forward(command: Forward) {
                val data = (command.transitionData as UserResponse.UserAccount)

                when (command.screenKey) {
                    MainActivity.TAG -> startActivity(Intent(this@LoginActivity,
                        MainActivity::class.java).putExtra("data", data as Parcelable))
                    else -> Log.e("Cicerone", "Unknown screen: " + command.screenKey)
                }
            }
        }
    }

    private var presenter: LoginContract.Presenter?= null
    private val editUsername: TextInputEditText? by lazy { edit_user }
    private val editPassword: TextInputEditText? by lazy { edit_password }
    private val btnLogin: Button? by lazy { btn_login }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        presenter = LoginPresenter(this)
        setListeners()

    }

    private fun setListeners(){
        btnLogin?.setOnClickListener {
            til_user.error = ""
            til_password.error = ""
            presenter?.loginButtonClick(
                editUsername?.text.toString(),
                editPassword?.text.toString())
        }
    }

    override fun onResume() {
        super.onResume()
        presenter?.onViewCreated()
        BaseApplication.INSTANCE.cicerone.navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        BaseApplication.INSTANCE.cicerone.navigatorHolder.removeNavigator()
    }
    override fun onDestroy() {
        presenter?.onDestoy()
        presenter = null
        super.onDestroy()
    }

    override fun showLoading() {
        //TODO("Not yet implemented")
    }

    override fun hideLoading() {
        //TODO("Not yet implemented")
    }

    override fun showErrorInvalidPassword() {
        til_password.error = getString(R.string.error_invalid_password)
    }

    override fun showErrorInvalidUsername(){
        til_user.error = getString(R.string.error_invalid_username)
    }

    override fun showErrorLoginRequest(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}