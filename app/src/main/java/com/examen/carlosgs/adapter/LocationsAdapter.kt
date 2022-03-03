package com.examen.carlosgs.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.examen.carlosgs.R
import com.examen.carlosgs.databinding.ItemLocationBinding
import com.examen.carlosgs.data.model.LocationModel

class LocationsAdapter(
    private val listener: LocationsAdapterListener,
    private var locations: List<LocationModel> = emptyList()
) : RecyclerView.Adapter<LocationsAdapter.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val layoutInflater = LayoutInflater.from(context)
        return ViewHolder(
            layoutInflater.inflate(
                R.layout.item_location,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(locations[position])
        holder.binding.cvItem.setOnClickListener { listener.onClickLocation(locations[position]) }
    }

    override fun getItemCount(): Int {
        return locations.size
    }

    fun setList(list: List<LocationModel>) {
        locations = list
        notifyDataSetChanged()
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemLocationBinding.bind(view)

        fun bind(locationModel: LocationModel) {
            binding.tvDate.text = locationModel.fecha?.toDate().toString()
            binding.tvLatitude.text = locationModel.latitud.toString()
            binding.tvLongitude.text = locationModel.longitud.toString()
        }

    }

    interface LocationsAdapterListener {
        fun onClickLocation(locationModel: LocationModel)
    }
}