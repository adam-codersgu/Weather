package com.codersguidebook.weather

import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceManager
import com.codersguidebook.weather.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class MainActivity : AppCompatActivity() {

    companion object {
        // TODO: Get your API key here https://home.openweathermap.org/api_keys
        //  Note it can sometimes take a couple of hours following email confirmation for API keys to become active
        private const val API_KEY = "INSERT-API-KEY-HERE"
        private const val CITY_NAME_URL = "https://api.openweathermap.org/data/2.5/weather?q="
        private const val GEO_COORDINATES_URL = "https://api.openweathermap.org/data/2.5/weather?lat="
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

        binding.fab.setOnClickListener {
            getLocation()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) getLocation()
        else if (requestCode == 1) {
            Toast.makeText(this, getString(R.string.permission_required), Toast.LENGTH_LONG).show()
        }
    }

    private fun getLocation() {
        if (ContextCompat.checkSelfPermission(applicationContext,
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION), 1)
        } else {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                location?.let {
                    val apiCall = GEO_COORDINATES_URL + location.latitude + "&lon=" + location.longitude
                    updateWeatherData(apiCall)
                    sharedPreferences.edit().apply {
                        putString("location", "currentLocation")
                        apply()
                    }
                }
            }
        }
    }
}