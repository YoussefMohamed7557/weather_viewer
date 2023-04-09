package com.example.weather_viewer.data_source.local.room.entities

import androidx.room.Entity
import com.example.weather_viewer.data_classes.favourite.Alert
import com.example.weather_viewer.data_classes.favourite.Current
import com.example.weather_viewer.data_classes.favourite.Daily
import com.example.weather_viewer.data_classes.favourite.Hourly

@Entity(primaryKeys = arrayOf("lat","lon"))
data class FavData(
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val timezone_offset: Int,
    val current: Current,
    val hourly: List<Hourly>,
    val daily: List<Daily>,
    val alerts: List<Alert>?){
    constructor():this(
        0.0,
        0.0,
        "",
        0,
        Current(0,0.0,0,0.0,0,0,0,0,0.0,0.0,0, emptyList(),0,0.0),
        emptyList<Hourly>(),
        emptyList<Daily>(),
        null
    )

}