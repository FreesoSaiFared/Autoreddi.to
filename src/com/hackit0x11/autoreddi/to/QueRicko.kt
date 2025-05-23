package com.hackit0x11.autoreddi.to

import android.app.Activity
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.widget.TextView

/**
 * QueRicko Activity displays a text with clickable links,
 * using a layout and string resource defined elsewhere.
 * 
 * If the Activity is started fresh (not from a saved state),
 * it calls utility.stop().
 *
 * To update UI or logic, look for resource IDs, resource names,
 * and method calls in comments below.
 */
class QueRicko : Activity() {
    // Reference to the TextView that displays the Rick Text.
    // If the TextView ID in the layout changes, update R.id.ricktesto here.
    private lateinit var magalli: TextView

    /**
     * onCreate is called when the Activity starts.
     * @param ilcomitato: Bundle? (savedInstanceState) - null if starting fresh.
     * 
     * Changes to layout, text content, or startup logic should be made here.
     */
    override fun onCreate(ilcomitato: Bundle?) {
        super.onCreate(ilcomitato)

        // Set the layout for this Activity.
        // If you change the UI XML file, update R.layout.rickrollo here.
        setContentView(R.layout.rickrollo)

        // Find the TextView by its ID.
        // If you rename or replace the TextView in the layout XML, update R.id.ricktesto here.
        magalli = findViewById(R.id.ricktesto)

        // Enable clickable links in the TextView.
        // If you want to disable links or change link behavior, update this line.
        magalli.movementMethod = LinkMovementMethod.getInstance()

        // Set the text of the TextView using a string resource.
        // If you change the string resource (e.g., rename it or change its content),
        // update R.string.RickText here.
        // Html.fromHtml is used to render HTML content in the string.
        magalli.text = Html.fromHtml(applicationContext.resources.getString(R.string.RickText))

        // If the Activity was started with no saved state, call utility.stop().
        // If you want different startup behavior, change or remove this block.
        if (ilcomitato == null) {
            utility.stop()
        }
    }
}
