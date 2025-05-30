package com.appdev.harvest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView

import com.appdev.harvest.fragments.HomeFragment
import com.appdev.harvest.fragments.PlantsFragment
import com.appdev.harvest.fragments.TasksFragment
import com.appdev.harvest.fragments.DiaryFragment
import com.appdev.harvest.fragments.SettingsFragment

class MainActivity : AppCompatActivity() {

    // Объявляем переменные для каждого фрагмента
    private val homeFragment = HomeFragment()
    private val plantsFragment = PlantsFragment()
    private val tasksFragment = TasksFragment()
    private val diaryFragment = DiaryFragment()
    private val settingsFragment = SettingsFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Загружаем начальный фрагмент (например, HomeFragment)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, homeFragment, "home")
                .commit()
        }

        // Находим BottomNavigationView по ID
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        // Настраиваем обработчик выбора пункта меню
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeFragment -> switchFragment(homeFragment)
                R.id.plantsFragment -> switchFragment(plantsFragment)
                R.id.tasksFragment -> switchFragment(tasksFragment)
                R.id.diaryFragment -> switchFragment(diaryFragment)
                R.id.settingsFragment -> switchFragment(settingsFragment)
                else -> return@setOnItemSelectedListener false
            }
            true // ← вот здесь была ошибка
        }
    }

    /**
     * Функция для плавного переключения между фрагментами
     * @param fragment - фрагмент, который нужно отобразить
     */
    private fun switchFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.fragment_enter,  // Анимация появления
                R.anim.fragment_exit    // Анимация исчезания
            )
            .replace(R.id.container, fragment)
            .disallowAddToBackStack() // Не добавляем в back stack
            .commit()
    }
}