package com.example.weather_viewer.data_source.local.room

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.weather_viewer.data_classes.favourite.Daily
import com.example.weather_viewer.data_classes.favourite.Hourly
import com.example.weather_viewer.data_classes.one_call.Current
import com.example.weather_viewer.data_source.local.room.entities.AllData
import com.example.weather_viewer.data_source.local.room.entities.FavData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert
import org.hamcrest.core.Is
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
@ExperimentalCoroutinesApi
@SmallTest
@RunWith(AndroidJUnit4::class)
class WeatherDaoTest {
lateinit var db:DataBaseWeather
lateinit var dao:WeatherDao
@get:Rule
var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun initDB() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            DataBaseWeather::class.java,
        ).allowMainThreadQueries().build()
        dao = db.weatherDao()
    }

    @After
    fun close() {
        db.close()
    }
    val fakeAllData = AllData(null,
        Current(0,0.0,0,0.0,0,0,0,0,0.0,0.0,0, emptyList(),0,0.0),
        emptyList(),
        emptyList(),
        0.0,
        0.0,
        "Egypt",
        0
    )
    val fakeFavoriteData = FavData(0.0,
        0.0,
        "Egypt",
        0,
        com.example.weather_viewer.data_classes.favourite.Current(
            0,
            0.0,
            0,
            0.0,
            0,
            0,
            0,
            0,
            0.0,
            0.0,
            0,
            emptyList(),
            0,
            0.0
        ),
        emptyList<Hourly>(),
        emptyList<Daily>(),
        null
    )
    val fakeFavoriteData2 = FavData(1.0,
        1.0,
        "iti",
        0,
        com.example.weather_viewer.data_classes.favourite.Current(
            0,
            0.0,
            0,
            0.0,
            0,
            0,
            0,
            0,
            0.0,
            0.0,
            0,
            emptyList(),
            0,
            0.0
        ),
        emptyList<Hourly>(),
        emptyList<Daily>(),
        null
    )
    @Test
    fun getAllData_OneItemSaved_returnTimeZoneAttribute() = runBlockingTest{
        // Given one item saved
        dao.saveAllData(fakeAllData)

        //When get a list with the stored items
        val results = dao.getAllData().first()
        var timezone =results[0].timezone

        //Then return the time zone attribute of the first item of this list it will be "Egypt"
        MatcherAssert.assertThat(timezone, Is.`is`("Egypt"))
    }

    @Test
    fun saveAllData_returnTimeZoneAttribute()= runBlockingTest{
        //When store one item
        dao.saveAllData(fakeAllData)

        //When get a list with the stored items and return the time zone attribute of the stored item in the list it will equal "Egypt"
        val results = dao.getAllData().first()
        var timezone =results[0].timezone
        MatcherAssert.assertThat(timezone, Is.`is`("Egypt"))
    }

    @Test
    fun deleteAll_OneItemSaved_returnTheSizeOfTheStoredList()= runBlockingTest {
        // Given one item saved
        dao.saveAllData(fakeAllData)

        //When delete all item in the  data base
        dao.deleteAll()
        val results = dao.getAllData().first()

        //Then return the size of the stored list
        MatcherAssert.assertThat(results.size, Is.`is`(0))
    }

    @Test
    fun getFavData_OneFavoriteItemSaved_returnTimeZoneAttribute()= runBlockingTest  {
        // Given OneFavoriteItemSaved
        dao.saveFaveData(fakeFavoriteData)
        //When get a list with the stored items
        val results = dao.getFavData().first()
        var timezone =results[0].timezone
        //Then return the time zone attribute of the stored item in the list it will equal "Egypt"
        MatcherAssert.assertThat(timezone, Is.`is`("Egypt"))
    }

    @Test
    fun getFavDataAsList_OneFavoriteItemSaved_returnTimeZoneAttribute()= runBlockingTest  {
        // Given OneFavoriteItemSaved
        dao.saveFaveData(fakeFavoriteData)

        //When get a list with the stored items
        val results = dao.getFavDataAsList().first()
        var timezone =results[0].timezone

        //Then return the time zone attribute of the stored item in the list it will equal "Egypt"
        MatcherAssert.assertThat(timezone, Is.`is`("Egypt"))
    }

    @Test
    fun saveFaveData_returnTimeZoneAttribute()= runBlockingTest  {
        // when save one item
        dao.saveFaveData(fakeFavoriteData)

        //Then get list with the stored items and return the time zone attribute of the stored item in the list it will equal "Egypt"
        val results = dao.getFavData().first()
        var timezone =results[0].timezone
        MatcherAssert.assertThat(timezone, Is.`is`("Egypt"))
    }

    @Test
    fun getOneFav_OneFavoriteItemSaved_returnTimeZoneAttribute()= runBlockingTest {
        // Given OneFavoriteItemSaved
        dao.saveFaveData(fakeFavoriteData)

        //When get this item by its latitude an longtude info
        val results = dao.getOneFav("0.0","0.0").first()
        val timezone = results.timezone

        //Then return its time zone attribute it will equal "Egypt"
        MatcherAssert.assertThat(timezone, Is.`is`("Egypt"))
    }

    @Test
    fun deleteOneFav_TwoFavoriteItemsSaved_returnTimeZoneAttributeAndTheSizeOfTheList() = runBlockingTest{

        // Given TwoFavoriteItemsSaved
        dao.saveFaveData(fakeFavoriteData)//Egypt
        dao.saveFaveData(fakeFavoriteData2)//iti

        //When delete the fist item from them
        dao.deleteOneFav("0.0","0.0")
        val results = dao.getFavData().first()
        val timezone = results.first().timezone

        //Then the size of the list will equal 1 and the time zone of this item in the list is "iti"
        MatcherAssert.assertThat(timezone, Is.`is`("iti"))
        MatcherAssert.assertThat(results.size, Is.`is`(1))
    }

    @Test
    fun deleteAllFav_TwoFavoriteItemsSaved_returnTheSizeOfTheList()= runBlockingTest{
        // Given TwoFavoriteItemsSaved
        dao.saveFaveData(fakeFavoriteData)//Egypt
        dao.saveFaveData(fakeFavoriteData2)//iti

        //When delete all items
        dao.deleteAllFav()
        val results = dao.getFavData().first()

        //Then the list size will equal 0
        MatcherAssert.assertThat(results.size, Is.`is`(0))
    }
}