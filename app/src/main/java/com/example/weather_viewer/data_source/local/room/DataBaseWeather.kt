package com.example.weather_viewer.data_source.local.room

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.weather_viewer.data_source.local.room.entities.AlertTable
import com.example.weather_viewer.data_source.local.room.entities.AllData
import com.example.weather_viewer.data_source.local.room.entities.FavData


@TypeConverters(Converter::class)
@Database(entities = [AllData::class, FavData::class, AlertTable::class], version = 1,exportSchema = false)
abstract class DataBaseWeather : RoomDatabase() {
    companion object{
        @Volatile
        private var db : DataBaseWeather? =null

        fun getInstance(application: Application): DataBaseWeather? {
            synchronized(this) {
                if (db == null)
                    db = Room.databaseBuilder(
                        application, DataBaseWeather::class.java, "Weather"
                    ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
            }
            return db
        }

    }


    abstract fun weatherDao(): WeatherDao

}