package ru.plesser.yweather2.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [WeatherEntity::class, CityEntity::class],
    version=1)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun getDao(): Dao

    companion object{
        @Volatile
        private var INSTANCE: WeatherDatabase?= null
        fun getDataBase(context: Context): WeatherDatabase{
            val database: WeatherDatabase = Room.databaseBuilder(
                context.applicationContext,
                WeatherDatabase::class.java,
                "weather"
            ).build()

            return database
        }

    }
}