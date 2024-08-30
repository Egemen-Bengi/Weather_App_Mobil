package com.egemenbengi.weatherproject.service

import com.egemenbengi.weatherproject.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface WeatherApi {
    //https://api.collectapi.com/
    //weather/getWeather?data.lang=tr&

    @GET("weather/getWeather?data.lang=tr&")
    suspend fun getData(
        @Header("Authorization") apiKey: String,
        @Query("data.city") dataCity: String
    ): WeatherResponse
}