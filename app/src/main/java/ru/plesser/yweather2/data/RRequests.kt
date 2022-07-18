package ru.plesser.yweather2.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import ru.plesser.yweather2.api.CitiesAPI
import ru.plesser.yweather2.data.template.geocoder.Geocoder

private const val TAG = "RRequests"

object RRequests {
    fun requestCitiesRetrofit(geocoderKey: String, city: String): LiveData<String> {
        Log.d(TAG, "RRequests.requestCitiesRetrofit")
        val responseLiveData: MutableLiveData<String> = MutableLiveData()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://geocode-maps.yandex.ru/1.x/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()

        val citiesAPI: CitiesAPI = retrofit.create(CitiesAPI::class.java)
        val citiesRequest: Call<String> = citiesAPI.getWeather(geocoderKey, city, "json")
        Log.d(TAG, citiesRequest.request().toString() )

        citiesRequest.enqueue(object : Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d(TAG, "Failed to fetch weather", t)
                responseLiveData.value = "error"
            }
            override fun onResponse(
                call: Call<String>,
                response: Response<String>
            ) {
                Log.d(TAG, "Response received " + response.body().toString())
                //val geocoder = Gson().fromJson(response.body(), Geocoder::class.java)
                responseLiveData.value = response.body()

            }
        })

        return responseLiveData

    }

}