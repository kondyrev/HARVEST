package com.appdev.harvest.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.appdev.harvest.MainActivity
import com.appdev.harvest.R
import com.appdev.harvest.adapters.ZoneAdapter
import com.appdev.harvest.models.ZoneItem

class HomeFragment : BaseFragment() {

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
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!isAuthorized()) {
            onNotAuthorized()
            return
        }

        // Восстанавливаем видимость BottomNavigationView
        (requireActivity() as MainActivity).updateBottomNavigationVisibility(true)

        val recyclerView = view.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.recyclerViewZones)
        val adapter = ZoneAdapter(zoneList) { selectedItem ->
            parentFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.fragment_enter, R.anim.fragment_exit)
                .replace(R.id.container, GardenListFragment.newInstance(selectedItem.name))
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }
}