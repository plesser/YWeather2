package ru.plesser.yweather2.data.room

import android.content.Context
import androidx.room.*

@Database(entities = [WeatherEntity::class, CityEntity::class], version=1)
@TypeConverters(WeatherTypeConverters::class)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun weatherDao(): WeatherDao
}