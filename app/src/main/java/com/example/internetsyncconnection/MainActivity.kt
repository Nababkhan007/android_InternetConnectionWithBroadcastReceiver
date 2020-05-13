package com.example.internetsyncconnection

import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import android.widget.Toast

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity(), ConnectionReceiver.ConnectionReceiverListener {
    private lateinit var connectedTv: TextView
    private lateinit var disConnectedTv: TextView
    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        only for app opening time, check if internet connection is available or not?
        if (ConnectionReceiver.isConnected) {
            Toast.makeText(this, "Network is connected!", Toast.LENGTH_LONG).show()
        }

        initialization()

        setConnectionReceiver()
    }

    private fun setConnectionReceiver() {
        baseContext.registerReceiver(
            ConnectionReceiver(),
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
        MyApplication.instance.setConnectionReceiver(this)
    }

    private fun initialization() {
        connectedTv = findViewById(R.id.tv_connectedId)
        disConnectedTv = findViewById(R.id.tv_disConnectedId)
        webView = findViewById(R.id.webViewId)
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        if (isConnected) {
            connectedTv.visibility = View.VISIBLE
            disConnectedTv.visibility = View.GONE

            runWebView("https://www.google.com/")

        } else {
            connectedTv.visibility = View.GONE
            disConnectedTv.visibility = View.VISIBLE

            runWebView("")
        }
    }

    private fun runWebView(url: String?) {
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }
        }
        webView.loadUrl(url)
    }
}
