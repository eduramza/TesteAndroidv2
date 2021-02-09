package com.example.bankcleancodetest

import android.app.Application
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

class BaseApplication : Application() {
    companion object{
        lateinit var INSTANCE: BaseApplication
    }

    init {
        INSTANCE = this
    }

    //Routing
    lateinit var cicerone: Cicerone<Router>

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        this.initCicerone()
    }

    private fun initCicerone() {
        this.cicerone = Cicerone.create()
    }
}