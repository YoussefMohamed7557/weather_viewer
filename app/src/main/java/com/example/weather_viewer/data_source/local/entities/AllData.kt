package com.example.weather_viewer.data_source.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.weather_viewer.data_classes.Alert
import com.example.weather_viewer.data_classes.Current
import com.example.weather_viewer.data_classes.Daily
import com.example.weather_viewer.data_classes.Hourly

@Entity
data class AllData(
    val alerts: List<Alert>?,
    val current: Current,
    val daily: List<Daily>,
    val hourly: List<Hourly>,
    val lat: Double,
    val lon: Double,
    @PrimaryKey
    val timezone: String,
    val timezone_offset: Int
)