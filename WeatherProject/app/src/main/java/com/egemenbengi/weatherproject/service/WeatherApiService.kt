package com.egemenbengi.weatherproject.service

import com.egemenbengi.weatherproject.model.WeatherResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherApiService {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.collectapi.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(WeatherApi::class.java)

    suspend fun getData(dataCity: String): WeatherResponse {
        return try {
            retrofit.getData(
                apiKey = "apikey 7bh6JLHiDGDFUNwmVeFfwS:5jbT8HaFsDNlQik9DSVO4k",
                dataCity = dataCity
            )
        } catch (exception: Exception){
            throw exception
        }
    }
}