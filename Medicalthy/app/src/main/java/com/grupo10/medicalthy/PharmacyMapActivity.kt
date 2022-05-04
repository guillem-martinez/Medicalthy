package com.grupo10.medicalthy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_pharmacy_map.*

class PharmacyMapActivity : AppCompatActivity() {

    private val MAP_URL = "https://www.google.com/maps/d/u/0/embed?mid=1uS-6vEJFfaG2eC607YTeJSVVNOc&ll=40.49213712776706%2C-1.0529294857418314&z=7"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pharmacy_map)

        webWiew.webChromeClient = object : WebChromeClient(){

        }

        webWiew.webViewClient = object : WebViewClient(){

        }

        val settings = webWiew.settings
        settings.javaScriptEnabled = true

        webWiew.loadUrl(MAP_URL)
    }
}