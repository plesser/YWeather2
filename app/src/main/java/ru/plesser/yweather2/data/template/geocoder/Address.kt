package ru.plesser.yweather2.data.template.geocoder

data class Address(
    val Components: List<Component>,
    val country_code: String,
    val formatted: String
)