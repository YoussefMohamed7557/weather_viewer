package com.example.weather_viewer.map_activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.weather_viewer.R
import com.example.weather_viewer.databinding.ActivityMapBinding

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapBinding
    private lateinit var mapActivityViewMode: MapActivityViewMode
    private lateinit var latLng: LatLng
    private lateinit var type: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        type=intent.getStringExtra("type").toString()

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        mapActivityViewMode= ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory.getInstance(
                application
            )
        )[MapActivityViewMode::class.java]
        mapActivityViewMode.saveLatLng.observe(this) {
            if (it) {
                mapActivityViewMode.saveLocationSetting(latLng)
                mapActivityViewMode.saveLatLng.value = false

            }
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.mapType = GoogleMap.MAP_TYPE_HYBRID
        mMap.uiSettings.isZoomControlsEnabled = true

        mMap.setOnMapClickListener {
            mMap.clear()
            mMap.addMarker(MarkerOptions().position(it))
            Log.d("TAG", "${it.latitude}.....${it.longitude}")
            latLng=it
            if (type=="setting"){
                mapActivityViewMode.showLocationSavingAlarm(this)
            }else {
                //TODO: - Handle Add this location to favourites case
            }
            Log.d("TAG", "${latLng.latitude}.....${type}")
            Log.d("TAG type", "")
        }
    }
}