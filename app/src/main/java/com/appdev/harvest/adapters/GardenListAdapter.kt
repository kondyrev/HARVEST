package com.appdev.harvest.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.appdev.harvest.R

class GardenListAdapter(
    private val gardens: List<String>,
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<GardenListAdapter.GardenViewHolder>() {

    class GardenViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GardenViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_garden, parent, false)
        return GardenViewHolder(view)
    }

    override fun onBindViewHolder(holder: GardenViewHolder, position: Int) {
        val gardenName = gardens[position]
        val textView = holder.itemView.findViewById<TextView>(R.id.gardenName)
        textView.text = gardenName

        holder.itemView.setOnClickListener {
            onItemClick(gardenName)
        }
    }

    override fun getItemCount(): Int = gardens.size
}