package com.grupo10.medicalthy

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_pharmacy_map.*

class PharmacyMapActivity : AppCompatActivity(), OnMapReadyCallback {

    //private val MAP_URL = "https://www.google.com/maps/d/u/0/embed?mid=1uS-6vEJFfaG2eC607YTeJSVVNOc&ll=40.49213712776706%2C-1.0529294857418314&z=7"
    private lateinit var map: GoogleMap
    lateinit var fusedLocation: FusedLocationProviderClient
    private var deviceLocation: LatLng? = null

    companion object {
        const val REQUEST_CODE_LOCATION = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pharmacy_map)

        fusedLocation = LocationServices.getFusedLocationProviderClient(this)

        createFragment()
//        webWiew.webChromeClient = object : WebChromeClient(){
//
//        }
//
//        webWiew.webViewClient = object : WebViewClient(){
//
//        }
//
//        val settings = webWiew.settings
//        settings.javaScriptEnabled = true
//
//        webWiew.loadUrl(MAP_URL)
    }

    @SuppressLint("MissingPermission")
    private fun getLocation(){
        /*
     * Get the best and most recent location of the device, which may be null in rare
     * cases when a location is not available.
     */
            if (isLocationPermissionGranted()) {
                val locationResult = fusedLocation.lastLocation
                locationResult.addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val lastKnownLocation = task.result
                        if (lastKnownLocation != null) {
                            deviceLocation =LatLng(lastKnownLocation.latitude, lastKnownLocation.longitude)
                        }
                    }
                }
            }
    }

    private fun createFragment() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        //getLocation()
        //createMarker()
        enableLocation()
    }

    private fun createMarker() {
        val coordinates = LatLng(deviceLocation!!.latitude, deviceLocation!!.longitude )
        val marker = MarkerOptions().position(coordinates).title("Estás aquí")
        map.addMarker(marker)
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(coordinates, 18f), 4000, null)
    }

    private fun isLocationPermissionGranted() = ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED

    @SuppressLint("MissingPermission")
    private fun enableLocation() {
        if(!::map.isInitialized) return
        if(isLocationPermissionGranted()) {
            map.isMyLocationEnabled = true
        } else {
            requestLocationPermission()
        }
    }

    private fun requestLocationPermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            Toast.makeText(this, "Acepta los putos permisos viejo asqueroso", Toast.LENGTH_SHORT).show()
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE_LOCATION)
        }
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            REQUEST_CODE_LOCATION -> if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                map.isMyLocationEnabled = true

            } else {
                Toast.makeText(this, "Te jodiste trozo de mierda", Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }

    @SuppressLint("MissingPermission")
    override fun onResumeFragments() {
        super.onResumeFragments()
        if(!::map.isInitialized) return
        if(!isLocationPermissionGranted()) {
            map.isMyLocationEnabled = false
        }
    }
}