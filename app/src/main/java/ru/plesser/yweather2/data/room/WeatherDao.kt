package ru.plesser.yweather2.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WeatherDao {
    @Insert
    fun insertWeather(weatherEntity:WeatherEntity)

    @Insert
    suspend fun insertCity(cityEntity:CityEntity)

    @Query("select * from cities where lower(city) like '%'||lower(:city)||'%'")
    fun getCities(city: String?): List<CityEntity>

    @Query("select count(id) from cities where city=:city and lat=:lat and lon=:lon")
    suspend fun isExistCity(city: String, lat: Double, lon: Double): Int

}