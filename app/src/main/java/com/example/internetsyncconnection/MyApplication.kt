package com.example.internetsyncconnection

import android.app.Application

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    fun setConnectionReceiver(listener: ConnectionReceiver.ConnectionReceiverListener) {
        ConnectionReceiver.connectionReceiverListener = listener
    }

    companion object {
        @get:Synchronized
        lateinit var instance : MyApplication
    }
}