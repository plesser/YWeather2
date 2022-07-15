package ru.plesser.yweather2.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weathers")
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val city: String,
    val lat: Double,
    val lon: Double,
    val temp: Int,
    val feel: Int,
    val dirWind: String,
    val icon: String,
    val time: Long = System.currentTimeMillis()
)
