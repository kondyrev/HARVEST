package com.appdev.harvest.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.appdev.harvest.R
import com.appdev.harvest.adapters.ZoneAdapter
import com.appdev.harvest.models.Zone
import com.appdev.harvest.fragments.AddZoneFragment
import com.appdev.harvest.fragments.BaseFragment
import com.appdev.harvest.fragments.GardenListFragment

/**
 * Главный экран приложения — список зон участка.
 * Пользователь может добавлять/удалять/редактировать зоны.
 */
class HomeFragment : BaseFragment() {

    //private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    private val zoneList = mutableListOf<Zone>()
    private lateinit var adapter: ZoneAdapter

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

        // Инициализируем Firebase
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        // Находим RecyclerView и FAB
        val recyclerView = view.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.recyclerViewZones)
        val floatingActionButton = view.findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.fabAddZone)

        // Создаем адаптер для отображения зон
        adapter = ZoneAdapter(zoneList) { selectedItem ->
            parentFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.fragment_enter, R.anim.fragment_exit)
                .replace(R.id.container, GardenListFragment.newInstance(selectedItem.name))
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }

        // Устанавливаем адаптер
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        // Подписываемся на обновления из Firebase
        firestore.collection("zones")
            .whereEqualTo("userId", auth.currentUser?.uid ?: "")
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Toast.makeText(context, "Ошибка загрузки зон", Toast.LENGTH_SHORT).show()
                    return@addSnapshotListener
                }

                zoneList.clear()
                for (document in value?.documents.orEmpty()) {
                    document.toObject(Zone::class.java)?.let { zone ->
                        zoneList.add(zone)
                    }
                }
                adapter.notifyDataSetChanged()
            }

        // Обработка клика по кнопке "Добавить зону"
        floatingActionButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, AddZoneFragment())
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }
    }
}