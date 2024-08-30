package com.egemenbengi.weatherproject.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Weather(
    @ColumnInfo("date")
    @SerializedName("date") val date: String?,

    @ColumnInfo("day")
    @SerializedName("day") val day: String?,

    @ColumnInfo("icon")
    @SerializedName("icon") val icon: String?,

    @ColumnInfo("description")
    @SerializedName("description") val description: String?,

    @ColumnInfo("status")
    @SerializedName("status") val status: String?,

    @ColumnInfo("degree")
    @SerializedName("degree") val degree: String?,

    @ColumnInfo("min")
    @SerializedName("min") val min: String?,

    @ColumnInfo("max")
    @SerializedName("max") val max: String?,

    @ColumnInfo("night")
    @SerializedName("night") val night: String?,

    @ColumnInfo("humidity")
    @SerializedName("humidity") val humidity: String?
){
    @PrimaryKey(autoGenerate = true) var uuid = 0
}