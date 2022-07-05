package ru.plesser.yweather2.data.template.geocoder

data class Locality(
    val DependentLocality: DependentLocality,
    val LocalityName: String,
    val Premise: Premise,
    val Thoroughfare: Thoroughfare
)