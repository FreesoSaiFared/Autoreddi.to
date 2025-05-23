package com.hackit0x11.autoreddi.to

import android.app.Activity
import android.content.*
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.webkit.*
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast

class AutoAct : Activity() {
    @Volatile
    lateinit var spade: WebView
    private lateinit var lucasardella: SharedPreferences
    private lateinit var janiramajello: SharedPreferences.Editor

    inner class GiucasCasella(private val ciste: Context) {
        @JavascriptInterface
        fun test1() {
            println("Vaivaivai da Aiazzone vai")
        }

        @JavascriptInterface
        fun notimp() {
            Toast.makeText(ciste, "Alpha Stage: not implemented yet.", Toast.LENGTH_SHORT).show()
        }

        @JavascriptInterface
        fun cingomma(peso: String) {
            val goatse = Intent(Intent.ACTION_VIEW, Uri.parse(peso))
            this@AutoAct.startActivity(goatse)
        }
    }

    private val receiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val bundle = intent.extras
            if (bundle != null) {
                val resultCode = bundle.getInt(Servodeiservideiservi.che)
                if (resultCode == RESULT_OK) {
                    utility.dodaburla(this@AutoAct, this@AutoAct)
                    spade.loadUrl("file:///android_asset/burla.html")
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(receiver, IntentFilter(Servodeiservideiservi.chie))
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(receiver)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_activity_actions, menu)
        if (lucasardella.getBoolean("burla", false)) {
            val facezie = menu.findItem(R.id.action_info)
            facezie.isVisible = true
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_menu -> {
                spade.loadUrl("javascript:showpannello()")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        lucasardella = getSharedPreferences("STORAGE", Context.MODE_PRIVATE)
        janiramajello = lucasardella.edit()
        setContentView(R.layout.main)
        spade = findViewById(R.id.webview)
        if (lucasardella.getBoolean("burla", false)) {
            spade.loadUrl("file:///android_asset/burla.html")
        } else {
            spade.loadUrl("file:///android_asset/index.html")
            spade.scrollBarStyle = WebView.SCROLLBARS_OUTSIDE_OVERLAY
            spade.webChromeClient = WebChromeClient()
            spade.webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView, url: String) {
                    val myIntent = Intent(SplashScreen.ACTION_CLOSE)
                    sendBroadcast(myIntent)
                }
            }
            spade.addJavascriptInterface(GiucasCasella(this), "jsinterface")
        }
        val webSettings = spade.settings
        webSettings.javaScriptEnabled = true
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // spade.setWebContentsDebuggingEnabled(true)
        }
    }
}
