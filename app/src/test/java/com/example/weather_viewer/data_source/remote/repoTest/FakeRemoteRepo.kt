package com.example.weather_viewer.data_source.remote.repoTest

import com.example.weather_viewer.activities.main_activity.MainActivity
import com.example.weather_viewer.data_source.local.room.entities.AllData
import com.example.weather_viewer.data_source.local.room.entities.FavData
import com.example.weather_viewer.data_source.remote.ApiInterface
import com.example.weather_viewer.data_source.remote.RemoteRepoInterface
import retrofit2.Response

class FakeRemoteRepo(val api:ApiInterface):RemoteRepoInterface {
    override suspend fun getOneCall(
        lat: String,
        lon: String,
        lang: String ,
        appid: String,
        exclude: String,
        units: String
    ): Response<AllData> {
        return api.getOneCall(
            lat,
            lon,
            "en",
            "cc578004936ddce46e2c61bb7a0b729f",
            "minutely",
            MainActivity.units
        )
    }

    override suspend fun getFavCall(
        lat: String,
        lon: String,
        lang: String,
        appid: String,
        exclude: String,
        units: String
    ): Response<FavData> {
        return api.getFavCall(
            lat,
            lon,
            "en",
            "cc578004936ddce46e2c61bb7a0b729f",
            "minutely",
            MainActivity.units
        )
    }
}