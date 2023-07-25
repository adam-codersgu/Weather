package com.codersguidebook.weather

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    companion object {
        // TODO: Get your API key here https://home.openweathermap.org/api_keys
        //  Note it can sometimes take a couple of hours following email confirmation for API keys to become active
        private const val API_KEY = "INSERT-API-KEY-HERE"
        private const val CITY_NAME_URL = "https://api.openweathermap.org/data/2.5/weather?q="
        private const val GEO_COORDINATES_URL = "https://api.openweathermap.org/data/2.5/weather?lat="
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}