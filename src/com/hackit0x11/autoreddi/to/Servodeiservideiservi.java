package com.hackit0x11.autoreddi.to

import android.app.Activity
import android.app.IntentService
import android.content.Intent
import android.os.Handler

/**
 * IntentService that periodically checks a remote resource (using a utility function)
 * and broadcasts a result if a certain condition is met.
 * 
 * If you change the checking logic, frequency, or broadcast details,
 * look for comments marked [CHANGE HERE] for guidance.
 */
class Servodeiservideiservi : IntentService("Servodeiservideiservi") {

    // The result code to broadcast. Change if your logic requires a different result.
    private var result: Int = Activity.RESULT_OK

    // Intent extra key for passing the result code. Update if you want a different key.
    companion object {
        const val CHE = "wop wop is the sound of the police" // [CHANGE HERE] If you change the extra key in your app
        const val CHIE = "com.hackit0x11.autoreddi.to.service.receiver" // [CHANGE HERE] If you change the broadcast action
    }

    // Handler used for scheduling periodic checks.
    private val handler = Handler()

    // Used to control whether periodic checks should continue running.
    private var keepRunning: Boolean = true

    // Runnable that performs the periodic check and reschedules itself.
    // If you want to change how often the check runs, modify the delay below.
    private val periodicCheck = object : Runnable {
        override fun run() {
            if (keepRunning) {
                // Run the network check in a separate thread to avoid blocking the main thread.
                Thread {
                    try {
                        // [CHANGE HERE] If you change the resource being checked, update the resource ID below.
                        val url = resources.getString(R.string.burlami)
                        val response = utility.getHttpResponse(url)
                        // [CHANGE HERE] If your logic for "success" changes, update this check.
                        if (response != null) {
                            publishResults(result)
                        }
                    } catch (e: Exception) {
                        // Handle network or other errors here if needed.
                    }
                }.start()
                // Schedule the next check in 60 seconds (60000 ms). [CHANGE HERE] to modify frequency.
                handler.postDelayed(this, 60000)
            }
        }
    }

    /**
     * Called when the IntentService is started.
     * Starts the periodic check after a 30 second initial delay.
     *
     * If you want to change startup timing, update the delay below.
     */
    override fun onHandleIntent(intent: Intent?) {
        handler.postDelayed(periodicCheck, 30000) // [CHANGE HERE] Initial delay before first check
    }

    /**
     * Broadcasts the result and stops further periodic checks.
     * 
     * If you want to send additional data, update the Intent here.
     */
    private fun publishResults(result: Int) {
        keepRunning = false
        val broadcastIntent = Intent(CHIE)
        broadcastIntent.putExtra(CHE, result)
        sendBroadcast(broadcastIntent)
    }
}
