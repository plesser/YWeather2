package ru.plesser.yweather2.data.room

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [WeatherEntity::class],
    version=1)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun getDao(): WeatherDao

    companion object{
        @Volatile
        private var INSTANCE: WeatherDatabase?= null
        fun getDataBase(context: Context): WeatherDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WeatherDatabase::class.java,
                    "shopping_list.db"
                ).build()
                instance
            }
        }

    }
}