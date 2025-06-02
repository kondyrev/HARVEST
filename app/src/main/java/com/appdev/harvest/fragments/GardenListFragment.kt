package com.appdev.harvest.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appdev.harvest.R
import com.appdev.harvest.adapters.GardenListAdapter

class GardenListFragment : BaseFragment() {

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments?.let {
            zoneName = it.getString("zone_name")
        }

        return inflater.inflate(R.layout.fragment_garden_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!isAuthorized()) {
            onNotAuthorized()
            return
        }

        val titleTextView = view.findViewById<TextView>(R.id.zoneTitleText)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewGardens)

        titleTextView.text = zoneName ?: "Зона не выбрана"

        val gardenNames = listOf("Грядка 1", "Грядка 2", "Грядка 3")

        val adapter = GardenListAdapter(gardenNames) { _ -> }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }
}