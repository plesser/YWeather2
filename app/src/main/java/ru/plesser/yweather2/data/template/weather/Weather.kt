package ru.plesser.yweather2.data.template.weather

data class Weather(
    val fact: Fact,
    val forecast: Forecast,
    val info: Info,
    val now: Int,
    val now_dt: String
)