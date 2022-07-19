package ru.plesser.yweather2.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.google.gson.Gson
import ru.plesser.yweather2.data.room.WeatherDatabase
import ru.plesser.yweather2.data.template.geocoder.Geocoder

private const val TAG = "WeatherRepository"

class WeatherRepository private constructor(context: Context) {

//    val database: WeatherDatabase = Room.databaseBuilder(
//        context.applicationContext,
//        WeatherDatabase::class.java,
//        "weather"
//    ).build()

    var geocoder: MutableLiveData<Geocoder> = MutableLiveData()

    fun getCities(geocoderKey: String, city: String): LiveData<Geocoder> {
        // сначало пробуем получить данные из интернета

        val geocdoreLiveData = RRequests.requestCitiesRetrofit(geocoderKey, city)
        geocdoreLiveData.observeForever(){
            gecodervalue ->
                Log.d(TAG, gecodervalue)
                geocoder.value = Gson().fromJson(gecodervalue, Geocoder::class.java)
        }

        Log.d(TAG, "WeatherRepository.getCities is " + geocoder.toString())
        return geocoder
    }


    companion object {
        private var INSTANCE : WeatherRepository? = null

        fun initialize(context: Context){
            if (INSTANCE == null){
                Log.d(TAG, "initialize WeatherRepository")
                INSTANCE = WeatherRepository(context)
            } else {
                Log.d(TAG, "not initialize WeatherRepository")
            }
        }

        fun get(): WeatherRepository{
            Log.d(TAG, "get() WeatherRepository")
            return INSTANCE ?: throw IllegalStateException("WeatherRepository must be initialized")
        }
    }
}