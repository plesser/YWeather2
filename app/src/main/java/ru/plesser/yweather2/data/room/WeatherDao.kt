package ru.plesser.yweather2.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.plesser.yweather2.data.template.weather.Weather

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weatherEntity:WeatherEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCity(cityEntity:CityEntity)

    @Query("select * from cities where lower(city) like '%'||lower(:city)||'%'")
    fun getCities(city: String?): Flow<List<CityEntity>>

    @Query("select count(id) from cities where city=:city and lat=:lat and lon=:lon")
    suspend fun isExistCity(city: String, lat: Double, lon: Double): Int

    @Query("select * from weathers where city=:city order by time desc")
    fun getLastWeather(city: String?): Flow<List<WeatherEntity>>

}