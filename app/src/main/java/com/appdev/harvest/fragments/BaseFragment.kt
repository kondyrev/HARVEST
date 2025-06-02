package com.appdev.harvest.fragments

import android.content.Context
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth

/**
 * Базовый класс для всех защищённых фрагментов.
 */
open class BaseFragment : Fragment() {

    protected lateinit var auth: FirebaseAuth

    override fun onAttach(context: Context) {
        super.onAttach(context)
        auth = FirebaseAuth.getInstance()

        if (!isAuthorized()) {
            onNotAuthorized()
        }
    }

    /**
     * Проверка авторизации пользователя
     */
    open fun isAuthorized(): Boolean {
        return auth.currentUser != null
    }

    /**
     * Метод, вызываемый, если пользователь не авторизован
     */
    open fun onNotAuthorized() {
        // По умолчанию перенаправляем на LoginFragment
        parentFragmentManager.beginTransaction()
            .replace(com.appdev.harvest.R.id.container, LoginFragment())
            .commitAllowingStateLoss()
    }
}