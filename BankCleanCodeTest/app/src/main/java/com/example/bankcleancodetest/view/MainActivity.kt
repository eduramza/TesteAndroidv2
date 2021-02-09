package com.example.bankcleancodetest.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bankcleancodetest.BaseApplication
import com.example.bankcleancodetest.MainContract
import com.example.bankcleancodetest.R
import com.example.bankcleancodetest.entity.InvoiceExtract
import com.example.bankcleancodetest.entity.UserResponse
import com.example.bankcleancodetest.presenter.MainPresenter
import com.example.bankcleancodetest.view.adapter.StatmentAdapter
import kotlinx.android.synthetic.main.activity_main.*
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Forward

class MainActivity : AppCompatActivity(), MainContract.View {

    companion object {
        val TAG = "MainActivity"
    }

    private val navigator: Navigator? by lazy {
        object : Navigator {
            override fun applyCommand(command: Command?) {
                if(command is Forward){
                    forward(command)
                }
            }

            private fun forward(command: Forward) {
                when(command.screenKey){
                    LoginActivity.TAG -> startActivity(
                        Intent(this@MainActivity, LoginActivity::class.java))
                    else -> Log.e("Cicerone", "Unknown Screen" + command.screenKey)
                }
            }
        }
    }

    private var presenter: MainContract.Presenter? = null
    private val imgLogout: ImageView? by lazy { img_logout }
    private val tvUsername: TextView? by lazy { tv_username }
    private val tvAccountValue: TextView? by lazy { tv_valor_conta }
    private val tvBalance: TextView? by lazy { tv_valor_saldo }
    private val recyclerView: RecyclerView? by lazy { rv_lancamentos }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = MainPresenter(this)
        setListeners()
        configList()
    }

    private fun setListeners(){
        imgLogout?.setOnClickListener { presenter?.logoutButtonClicked() }
    }

    private fun configList(){
        recyclerView?.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,
        false)
        recyclerView?.adapter = StatmentAdapter(null)
    }

    override fun doLogoff() {
        //TODO remover o cliente da sessão
    }

    override fun showLoading() {
        //TODO("Not yet implemented")
    }

    override fun hideLoading() {
        //TODO("Not yet implemented")
    }

    override fun showUserDetails(name: String, account: String, agency: String, balance: String) {
        tvUsername?.text = name
        tvAccountValue?.text = "$account / $agency"
        tvBalance?.text = balance
    }

    override fun publishDataList(data: List<InvoiceExtract.Statement>) {
        (recyclerView?.adapter as StatmentAdapter).updateData(data)
    }

    override fun onResume() {
        super.onResume()
        val argument = intent.getParcelableExtra<UserResponse.UserAccount>("data")
        argument?.let { presenter?.onViewCreated(it) }
        BaseApplication.INSTANCE.cicerone.navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        BaseApplication.INSTANCE.cicerone.navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onDestroy() {
        presenter?.onDestroy()
        presenter = null
        super.onDestroy()
    }
}