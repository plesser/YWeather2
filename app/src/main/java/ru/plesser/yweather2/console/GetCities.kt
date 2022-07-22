package ru.plesser.yweather2.console

import com.google.gson.Gson
import ru.plesser.yweather2.data.template.geocoder.Geocoder
import ru.plesser.yweather2.utils.Transform
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

fun main(args: Array<String>){
    System.out.println("Hello world")
    val city = URLEncoder.encode("Удельная", "UTF-8")
    requestCities(city){
        geocoder -> bindGeocoder(geocoder)
    }
    System.out.println("Goodbye world")
}


private fun bindGeocoder(geocoder: Geocoder) {
    println(geocoder)
    val memmbers = geocoder.response.GeoObjectCollection.featureMember
    println(memmbers)
    for (member in memmbers){
        val pos = member.GeoObject.Point.pos
        val lat = pos.split(" ")[0].toDouble()
        val lon = pos.split(" ")[1].toDouble()
        println("${lat} ${lon} ${member.GeoObject.metaDataProperty.GeocoderMetaData.text}")
    }
}



fun requestCities(city: String, block:(geocoder: Geocoder)->Unit){

    val geocoderKey = "0a6a39b7-eebf-4977-bf4d-c74fbf392e65"
    val urlstr = "https://geocode-maps.yandex.ru/1.x/?apikey=${geocoderKey}&geocode=${city}&format=json"
    println(urlstr)
    val url = URL(urlstr)

    var myConnection = url.openConnection() as HttpURLConnection
    myConnection.readTimeout = 5000
    val reader = BufferedReader(InputStreamReader(myConnection.inputStream))
    val geocoder = Gson().fromJson(Transform.getLines(reader), Geocoder::class.java)
    block(geocoder)

}