package com.appdev.harvest.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.appdev.harvest.R
import com.appdev.harvest.adapters.GardenListAdapter

class GardenListFragment : Fragment() {

    private var zoneName: String? = null

    companion object {
        fun newInstance(zoneName: String): GardenListFragment {
            val args = Bundle().apply {
                putString("zone_name", zoneName)
            }
            val fragment = GardenListFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            zoneName = it.getString("zone_name")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_garden_list, container, false)

        val titleTextView = view.findViewById<TextView>(R.id.zoneTitleText)
        titleTextView.text = zoneName ?: "Зона не выбрана"

        val recyclerView = view.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.recyclerViewGardens)
        val gardenNames = listOf("Грядка 1", "Грядка 2", "Грядка 3")

        val adapter = GardenListAdapter(gardenNames) { name ->
            // Можно добавить переход к растениям
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        return view
    }
}