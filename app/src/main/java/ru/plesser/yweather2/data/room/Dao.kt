package ru.plesser.yweather2.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeather(weatherEntity:WeatherEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCity(cityEntity:CityEntity)

}