package com.example.weather_viewer.data_source.local.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.weather_viewer.data_classes.one_call.Alert
import com.example.weather_viewer.data_classes.one_call.Current
import com.example.weather_viewer.data_classes.one_call.Daily
import com.example.weather_viewer.data_classes.one_call.Hourly

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