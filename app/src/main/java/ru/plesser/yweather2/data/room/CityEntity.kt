package ru.plesser.yweather2.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cities")
data class CityEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val city: String,
    val lat: Double,
    val lon: Double,
    val time: Long = System.currentTimeMillis()
)
