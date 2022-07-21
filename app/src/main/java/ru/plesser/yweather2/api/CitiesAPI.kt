package ru.plesser.yweather2.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import ru.plesser.yweather2.data.template.geocoder.Geocoder
import ru.plesser.yweather2.data.template.weather.Weather

interface CitiesAPI {
    @GET("/1.x/")
    fun getCity(
        @Query("apikey") apikey: String,
        @Query("geocode") geocode: String,
        @Query("format") format: String = "json"
    ): Call<Geocoder>
}