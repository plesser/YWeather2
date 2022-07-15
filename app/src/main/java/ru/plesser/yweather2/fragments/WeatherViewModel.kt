package ru.plesser.yweather2.fragments

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import kotlinx.coroutines.launch
import ru.plesser.yweather2.data.City
import ru.plesser.yweather2.data.room.WeatherDatabase
import ru.plesser.yweather2.data.room.WeatherEntity
import ru.plesser.yweather2.data.template.weather.Weather
import ru.plesser.yweather2.utils.Assets
import ru.plesser.yweather2.utils.Loader

private val TAG = "WeatherViewModel"
private const val DATABASE_NAME = "weather-database"

class WeatherViewModel(database : WeatherDatabase): ViewModel() {
    val dao = database.getDao()

    fun requestWeatherRetrofit(application: Application, lat: Double, lon: Double): LiveData<String> {
        val weatherKey = Assets.getKeyYWeather(application.getApplicationContext() as Application)
        val responseLiveData: LiveData<String> = Loader.requestWeatherRetrofit(weatherKey, lat, lon)

        return responseLiveData
    }

    fun insertWeather(city: City, weather: Weather) {
        viewModelScope.launch {
            val weatherEntity: WeatherEntity = WeatherEntity(null,
                                                                city.name,
                                                                city.lat,
                                                                city.lon,
                                                                weather.fact.temp,
                                                                weather.fact.feels_like,
                                                                weather.fact.wind_dir,
                                                                weather.fact.icon
                                                                )

            dao.insertWeather(weatherEntity)
        }
    }

    class WeatherViewModelFactory(val database: WeatherDatabase) : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(WeatherViewModel::class.java)){
                @Suppress("UNCHECKED CAST")
                return WeatherViewModel(database) as T
            }
            throw IllegalArgumentException("Unknown ViewModelClass")
        }

    }
}