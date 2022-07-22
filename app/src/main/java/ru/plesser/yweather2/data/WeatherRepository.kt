package ru.plesser.yweather2.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.room.Room
import ru.plesser.yweather2.data.room.CityEntity
import ru.plesser.yweather2.data.room.WeatherDatabase
import ru.plesser.yweather2.data.template.geocoder.Geocoder

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