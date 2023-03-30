package com.example.weather_viewer.data_source.local.room.entities

import androidx.room.Entity
import com.example.weather_viewer.data_classes.Alert
import com.example.weather_viewer.data_classes.Current
import com.example.weather_viewer.data_classes.Daily
import com.example.weather_viewer.data_classes.Hourly

@Entity(primaryKeys = arrayOf("lat","lon"))
data class FavData(
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val timezone_offset: Int,
    val current: Current,
    val hourly: List<Hourly>,
    val daily: List<Daily>,
    val alerts: List<Alert>?)