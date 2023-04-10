package com.example.weather_viewer.data_source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.weather_viewer.data_classes.favourite.Daily
import com.example.weather_viewer.data_classes.favourite.Hourly
import com.example.weather_viewer.data_classes.one_call.Current
import com.example.weather_viewer.data_source.local.room.RoomRepoInterface
import com.example.weather_viewer.data_source.local.room.entities.AllData
import com.example.weather_viewer.data_source.local.room.entities.FavData
import com.example.weather_viewer.data_source.local.shared_preferences.SharedPreferenceInterface
import com.example.weather_viewer.data_source.remote.ApiClient
import com.example.weather_viewer.data_source.remote.RemoteRepoInterface
import com.example.weather_viewer.data_source.remote.repoTest.FakeRemoteRepo
import com.example.weather_viewer.data_source.remote.repoTest.FakeRoomRepo
import com.example.weather_viewer.data_source.remote.repoTest.FakeSharedPrefRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert
import org.hamcrest.core.Is
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@Config(manifest= Config.NONE)
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class GeneralRepositoryTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    private var favoriteList: MutableList<FavData> = mutableListOf<FavData>(
        FavData(0.0,
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
        ),
        FavData(1.0,
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
    )
    private var allDataList = mutableListOf<AllData>(
        AllData(null,
            Current(0,0.0,0,0.0,0,0,0,0,0.0,0.0,0, emptyList(),0,0.0),
            emptyList(),
            emptyList(),
            0.0,
            0.0,
            "Egypt",
            0
        ),
        AllData(null,
            Current(0,0.0,0,0.0,0,0,0,0,0.0,0.0,0, emptyList(),0,0.0),
            emptyList(),
            emptyList(),
            0.0,
            0.0,
            "iti",
            0
        )
    )
    lateinit var generalRepository: GeneralRepository
    lateinit var roomRepo: RoomRepoInterface
    lateinit var remoteRepo: RemoteRepoInterface
    lateinit var sharedPrefRepo: SharedPreferenceInterface
    @Before
    fun initData(){
        roomRepo = FakeRoomRepo(favoriteList,allDataList)
        remoteRepo = FakeRemoteRepo(ApiClient.apiService())
        sharedPrefRepo = FakeSharedPrefRepo()
        generalRepository = GeneralRepository(remoteRepo,roomRepo,sharedPrefRepo)
    }

    @Test
    fun getAllData_OneItemSaved_returnTimeZoneAttribute() = runBlockingTest {

        //When get a list with the stored items
        val results = generalRepository.getRoomDataBase().first()
        var timezone =results[0].timezone

        //Then return the time zone attribute of the first item of this list it will be "Egypt"
        MatcherAssert.assertThat(timezone, Is.`is`("Egypt"))
    }

    @Test
    fun deleteOneFav_TwoFavoriteItemsSaved_returnTimeZoneAttributeAndTheSizeOfTheList() = runBlockingTest{
        //When delete the fist item from the favorite list
        generalRepository.deleteOneFav("0.0","0.0")
        val results = generalRepository.getFavDataBase().first()
        val timezone = results.first().timezone

        //Then the size of the list will equal 1 and the time zone of this item in the list is "iti"
        MatcherAssert.assertThat(timezone, Is.`is`("iti"))
        MatcherAssert.assertThat(results.size, Is.`is`(1))
    }

    @Test
    fun getFavData_OneFavoriteItemSaved_returnTimeZoneAttribute()  = runBlockingTest{
        //When get a list with the stored items
        val results = generalRepository.getFavDataBase().first()
        var timezone =results[0].timezone
        //Then return the time zone attribute of the stored item in the list it will equal "Egypt"
        MatcherAssert.assertThat(timezone, Is.`is`("Egypt"))
    }

    @Test
    fun getFavDataAsList_OneFavoriteItemSaved_returnTimeZoneAttribute()= runBlockingTest {
        //When get a list with the stored items
        val results = generalRepository.getFavDataBase().first()
        var timezone =results[0].timezone
        //Then return the time zone attribute of the stored item in the list it will equal "Egypt"
        MatcherAssert.assertThat(timezone, Is.`is`("Egypt"))
    }

    @Test
    fun saveFave() = runBlockingTest {
        // given an item
        val item = favoriteList[0]
        // when save this item
        generalRepository.saveFave(favoriteList[0].lat.toString(),favoriteList[0].lon.toString(),"en","standrd")
    }

    @Test
    fun getOneFav_OneFavoriteItemSaved_returnTimeZoneAttribute()= runBlockingTest {
        //When get this item by its latitude an longtude info
        val results = generalRepository.getOneFav("0.0","0.0").first()
        val timezone = results.timezone

        //Then return its time zone attribute it will equal "Egypt"
        MatcherAssert.assertThat(timezone, Is.`is`("Egypt"))
    }

}