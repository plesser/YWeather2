package ru.plesser.yweather2.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeather(weatherEntity:WeatherEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCity(cityEntity:CityEntity)

    @Query("select * from cities where lower(city) like '%'||lower(:city)||'%'")
    fun getCities(city: String?): List<CityEntity>

}