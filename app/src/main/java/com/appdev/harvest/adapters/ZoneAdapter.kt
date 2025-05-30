package com.appdev.harvest.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.appdev.harvest.R
import com.appdev.harvest.models.ZoneItem

class ZoneAdapter(
    private val zones: List<ZoneItem>,
    private val onItemClick: (ZoneItem) -> Unit
) : RecyclerView.Adapter<ZoneAdapter.ZoneViewHolder>() {

    class ZoneViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ZoneViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_zone, parent, false)
        return ZoneViewHolder(view)
    }

    override fun onBindViewHolder(holder: ZoneViewHolder, position: Int) {
        val item = zones[position]

        // Найдём элементы по ID
        holder.itemView.findViewById<TextView>(R.id.zoneName).text = item.name
        holder.itemView.findViewById<TextView>(R.id.zoneDescription).text = item.description

        // Клик по карточке
        holder.itemView.setOnClickListener {
            onItemClick(item)
        }
    }

    override fun getItemCount(): Int = zones.size
}