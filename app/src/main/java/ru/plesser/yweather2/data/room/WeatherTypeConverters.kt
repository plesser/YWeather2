package ru.plesser.yweather2.data.room

import androidx.room.TypeConverter
import java.util.*

class WeatherTypeConverters {
    @TypeConverter
    fun fromDate(date: Date?): Long?{
        return date?.time
    }

    @TypeConverter
    fun toDate(millisSinceEpoch: Long?): Date?{
        return millisSinceEpoch?.let{
            Date(it)
        }
    }
}