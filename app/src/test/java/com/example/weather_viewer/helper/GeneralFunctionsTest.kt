package com.example.weather_viewer.helper

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.MatcherAssert
import org.hamcrest.core.Is
import org.junit.Assert.*
import org.junit.Rule

import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class GeneralFunctionsTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    @Test
    fun getUnites_unitIsStandrd_return_k() {
        //gavin the unit is standrd
        val unit = "standard"

        //when sore the return of this method
        val result= GeneralFunctions().getUnites(unit)

        //then the output will be
        MatcherAssert.assertThat(result, Is.`is`("K"))
    }
}