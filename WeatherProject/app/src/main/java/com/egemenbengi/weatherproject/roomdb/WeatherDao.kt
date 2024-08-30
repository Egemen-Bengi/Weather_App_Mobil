package com.egemenbengi.weatherproject.roomdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.egemenbengi.weatherproject.model.Weather

@Dao
interface WeatherDao {
    @Insert
    suspend fun insertAll(vararg weather: Weather): List<Long>

    @Query("DELETE FROM Weather")
    suspend fun deleteAll()

    @Query("SELECT * FROM Weather")
    suspend fun getAllData(): List<Weather>
}