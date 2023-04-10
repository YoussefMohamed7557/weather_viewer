package com.example.weather_viewer.data_source.remote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.weather_viewer.activities.main_activity.MainActivity.Companion.units
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert
import org.hamcrest.core.Is
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@Config(manifest= Config.NONE)
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class ApiInterfaceTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    lateinit var api:ApiInterface
    @Before
    fun setUp() {
        api = ApiClient.apiService()
    }

    @Test
    fun getOneCall_CertainLocationInfo_returnTheTimeZone() = runBlocking{
        // Give this location info
        val lat = "27.371797074448676" ; val lon = "31.20118126273155" ; val language = "en"
        // when make network request with the previouse data and get the time zone from the response
       val result = api.getOneCall(
           lat,
           lon,
           language,
           "cc578004936ddce46e2c61bb7a0b729f",
           "minutely",
           units

        )
        val timezone = result.body()?.timezone
        //Then when we retreive the response time zone it will equal "Africa/Cairo"
        MatcherAssert.assertThat(timezone, Is.`is`("Africa/Cairo"))
    }

    @Test
    fun getFavCall_CertainLocationInfo_returnTheTimeZone()= runBlocking{
        // Give this location info
        val lat = "27.371797074448676" ; val lon = "31.20118126273155" ; val language = "en"
        // when make network request with the previouse data and get the time zone from the response
        val result = api.getFavCall(
            lat,
            lon,
            language,
            "cc578004936ddce46e2c61bb7a0b729f",
            "minutely",
            units

        )
        val timezone = result.body()?.timezone
        //Then when we retreive the response time zone it will equal "Africa/Cairo"
        MatcherAssert.assertThat(timezone, Is.`is`("Africa/Cairo"))
    }
}