package com.appdev.harvest.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appdev.harvest.R
import com.appdev.harvest.adapters.ZoneAdapter
import com.appdev.harvest.models.ZoneItem

class HomeFragment : Fragment() {

    private val zoneList = listOf(
        ZoneItem("Теплица", "Поликарбонатная теплица"),
        ZoneItem("Открытый огород", "Грядки на улице"),
        ZoneItem("Цветник", "Клумбы")
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewZones)
        val adapter = ZoneAdapter(zoneList) { selectedItem ->
            // Переход к списку грядок
            view.findNavController().navigate(R.id.action_homeFragment_to_gardenListFragment)
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        return view
    }
}