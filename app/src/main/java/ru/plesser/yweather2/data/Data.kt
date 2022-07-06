package ru.plesser.yweather2.data

class Data {

    var cities: ArrayList<City> = ArrayList()

    companion object {
        private var data: Data

        init {
            data= Data()
        }

        fun newInstance(): Data{
            return data
        }
    }
}