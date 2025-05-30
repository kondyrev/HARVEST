package com.appdev.harvest.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.appdev.harvest.R
import com.appdev.harvest.models.GardenItem

class GardenListAdapter(
    private val gardens: List<GardenItem>,
    private val onItemClick: (GardenItem) -> Unit
) : RecyclerView.Adapter<GardenListAdapter.GardenViewHolder>() {

    inner class GardenViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GardenViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_garden, parent, false)
        return GardenViewHolder(view)
    }

    override fun onBindViewHolder(holder: GardenViewHolder, position: Int) {
        val item = gardens[position]
        val textView = holder.itemView.findViewById<TextView>(R.id.gardenName)
        textView.text = item.name

        holder.itemView.setOnClickListener {
            onItemClick(item)
        }
    }

    override fun getItemCount(): Int = gardens.size
}