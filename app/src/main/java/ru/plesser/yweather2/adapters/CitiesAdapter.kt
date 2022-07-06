package ru.plesser.yweather2.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import ru.plesser.yweather2.R
import ru.plesser.yweather2.data.City
import ru.plesser.yweather2.data.Data
import ru.plesser.yweather2.databinding.ItemCityBinding

private val TAG = "CitiesAdapter"

class CitiesAdapter(var citiesList: ArrayList<City>, val activity: FragmentActivity?, val listener: Listener): RecyclerView.Adapter<CitiesAdapter.CityHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityHolder {
        val layoutInflater = LayoutInflater.from(activity)
        val view = layoutInflater.inflate(R.layout.item_city, parent, false)
        return CityHolder(view)
    }

    override fun onBindViewHolder(holder: CityHolder, position: Int) {
        holder.bind(citiesList.get(position), position, listener)
    }

    override fun getItemCount(): Int {
        return citiesList.size
    }

    class CityHolder(view: View):RecyclerView.ViewHolder(view),  View.OnClickListener{
        private val binding = ItemCityBinding.bind(view)
        private lateinit var city: City
        private lateinit var listener: Listener
        private var pos: Int = 0

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(city: City, pos: Int, listener: Listener){
            this.city = city
            this.listener = listener
            this.pos = pos

            binding.apply {
                cityTextview.text = city.name
                latTextView.text = city.lat.toString()
                lonTextview.text = city.lon.toString()
            }
        }

        override fun onClick(view: View?) {
            listener.onCityClick(pos)
        }

    }

    interface Listener{
        fun onCityClick(id: Int)
    }

}
