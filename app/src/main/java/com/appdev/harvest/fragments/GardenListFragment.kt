package com.appdev.harvest.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appdev.harvest.R
import com.appdev.harvest.adapters.GardenListAdapter
import com.appdev.harvest.models.GardenItem

class GardenListFragment : Fragment() {

    private val gardenList = listOf(
        GardenItem("Грядка 1", "Описание 1"),
        GardenItem("Грядка 2", "Описание 2"),
        GardenItem("Грядка 3", "Описание 3")
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_garden_list, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewGardens)
        val adapter = GardenListAdapter(gardenList) { selectedItem ->
            // Здесь можно добавить переход к растениям или другим экранам
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        return view
    }
}