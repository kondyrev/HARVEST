package com.appdev.harvest

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.appdev.harvest.R
import com.appdev.harvest.fragments.LoginFragment
import com.appdev.harvest.fragments.HomeFragment
import com.appdev.harvest.fragments.PlantsFragment
import com.appdev.harvest.fragments.TasksFragment
import com.appdev.harvest.fragments.DiaryFragment
import com.appdev.harvest.fragments.SettingsFragment

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val homeFragment = HomeFragment()
    private val plantsFragment = PlantsFragment()
    private val tasksFragment = TasksFragment()
    private val diaryFragment = DiaryFragment()
    private val settingsFragment = SettingsFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Инициализируем Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Получаем BottomNavigationView
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        // Проверяем авторизацию пользователя
        if (auth.currentUser == null) {
            // Пользователь не вошёл → скрываем меню и показываем LoginFragment
            supportFragmentManager.commit {
                add(R.id.container, LoginFragment())
                setReorderingAllowed(true)
            }
            bottomNav.visibility = View.GONE
        } else {
            // Пользователь авторизован → показываем главный экран и меню
            supportFragmentManager.commit {
                add(R.id.container, homeFragment)
                setReorderingAllowed(true)
            }
            bottomNav.visibility = View.VISIBLE

            // Настраиваем навигацию по нижнему меню
            bottomNav.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.homeFragment -> switchFragment(homeFragment)
                    R.id.plantsFragment -> switchFragment(plantsFragment)
                    R.id.tasksFragment -> switchFragment(tasksFragment)
                    R.id.diaryFragment -> switchFragment(diaryFragment)
                    R.id.settingsFragment -> switchFragment(settingsFragment)
                    else -> false
                }
            }
        }
    }

    /**
     * Функция для плавного переключения между фрагментами
     */
    private fun switchFragment(fragment: Fragment): Boolean {
        supportFragmentManager.commit {
            replace(R.id.container, fragment)
            setCustomAnimations(
                R.anim.fragment_enter,
                R.anim.fragment_exit
            )
            disallowAddToBackStack()
            setReorderingAllowed(true)
        }
        return true
    }

    /**
     * Вызови этот метод из любого фрагмента, чтобы обновить состояние меню
     * Пример: updateBottomNavigationVisibility(isVisible = false)
     */
    fun updateBottomNavigationVisibility(isVisible: Boolean) {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNav.visibility = if (isVisible) View.VISIBLE else View.GONE
    }
}