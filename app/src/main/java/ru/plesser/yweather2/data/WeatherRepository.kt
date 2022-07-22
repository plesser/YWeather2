package ru.plesser.yweather2.data

import android.content.Context
import androidx.room.Room
import ru.plesser.yweather2.data.room.CityEntity
import ru.plesser.yweather2.data.room.WeatherDatabase
import ru.plesser.yweather2.data.template.geocoder.Geocoder

private const val DATABASE_NAME = "weather"

class WeatherRepository private constructor(context: Context){


    val database: WeatherDatabase = Room.databaseBuilder(
        context.applicationContext,
        WeatherDatabase::class.java,
        DATABASE_NAME
    ).build()

    fun insertCity(cities: ArrayList<City>){
        for (city in cities){
            //cityEntity: CityEntity
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