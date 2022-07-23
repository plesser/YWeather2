package ru.plesser.yweather2.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.room.Room
import ru.plesser.yweather2.data.room.CityEntity
import ru.plesser.yweather2.data.room.WeatherDatabase
import ru.plesser.yweather2.data.room.WeatherEntity
import ru.plesser.yweather2.data.template.weather.Weather

private const val DATABASE_NAME = "weather"
private const val TAG = "WeatherRepository"


class WeatherRepository private constructor(context: Context) {


    val database: WeatherDatabase = Room.databaseBuilder(
        context.applicationContext,
        WeatherDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val weatherDao = database.weatherDao()

//    fun getCities(city: String): LiveData<ArrayList<City>> {
//        val cities: ArrayList<City> = ArrayList()
//
//        val citiesEntity = weatherDao.getCities(city)
//        for (c in citiesEntity){
//            val city = City(c.city, c.lat, c.lon)
//            cities.add(city)
//        }
//
//        return
//    }

    suspend fun insertCity(cities: ArrayList<City>) {
        Log.d(TAG, cities.toString())
        for (city in cities) {
            if (weatherDao.isExistCity(city.name, city.lat, city.lon) == 0) {
                val cityEntity: CityEntity = CityEntity(null, city.name, city.lat, city.lon)
                weatherDao.insertCity(cityEntity)
            }
        }
    }

    suspend fun insertWeather(city: String, lat: Double, lon: Double, weather: Weather){
        val weatherEntity = WeatherEntity(null, city, lat, lon, weather.fact.temp, weather.fact.feels_like, weather.fact.wind_dir, weather.fact.icon)
        weatherDao.insertWeather(weatherEntity)
    }

    fun getCities(city: String): LiveData<List<CityEntity>> {
        return weatherDao.getCities(city).asLiveData()
    }

    suspend fun getLastWeather(city: String): LiveData<List<WeatherEntity>> {
        return weatherDao.getLastWeather(city).asLiveData()
    }


    companion object{
        private var INSTANCE: WeatherRepository? = null
        fun initialize(context: Context){
            if (INSTANCE == null){
                INSTANCE = WeatherRepository(context)
            }
        }

        fun get(): WeatherRepository{
            return INSTANCE ?: throw IllegalStateException("WeatherRepository must be initialized")
        }
    }

}