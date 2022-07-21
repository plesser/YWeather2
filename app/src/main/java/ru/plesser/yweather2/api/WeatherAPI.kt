package ru.plesser.yweather2.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import ru.plesser.yweather2.data.template.weather.Weather

interface WeatherAPI {
    @GET("/v2/informers")
    fun getWeather(
        @Header("X-Yandex-API-Key") key: String,
        @Query("lat") lat: Double,
        @Query("lon") lon: Double
    ): Call<Weather>
}