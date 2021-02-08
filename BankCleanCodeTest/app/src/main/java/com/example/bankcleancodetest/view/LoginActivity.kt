package com.example.bankcleancodetest.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.bankcleancodetest.LoginContract
import com.example.bankcleancodetest.R
import com.example.bankcleancodetest.presenter.LoginPresenter
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), LoginContract.View {

    companion object {
        val TAG = "LoginActivity"
    }

    private final var presenter: LoginContract.Presenter?= null
    private val editUsername: TextInputEditText? by lazy { edit_user }
    private val editPassword: TextInputEditText? by lazy { edit_password }
    private val btnLogin: Button? by lazy { btn_login }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        presenter = LoginPresenter(this)
        btnLogin?.setOnClickListener {
            presenter?.loginButtonClick(
                editUsername?.text.toString(),
                editPassword?.text.toString())
        }
    }

    override fun onResume() {
        super.onResume()
        presenter?.onViewCreated()
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

    override fun showInvalidPassword() {
        Toast.makeText(this, "Senha inválida", Toast.LENGTH_SHORT).show()
    }

    override fun showInvalidUsername(){
        Toast.makeText(this, "Usuário deve ser cpf ou e-mail valido", Toast.LENGTH_SHORT).show()
    }
}