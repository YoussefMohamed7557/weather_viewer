package com.example.weather_viewer.data_source.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AlertTable(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,

    @ColumnInfo(defaultValue = "UnTitled", name = "title")
    val title: String,
    val type: String,
    val time :String,
    val reputation: Boolean,
)