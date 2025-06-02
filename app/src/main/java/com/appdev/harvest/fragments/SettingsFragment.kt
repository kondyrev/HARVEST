package com.appdev.harvest.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.appdev.harvest.R
import com.google.firebase.auth.FirebaseAuth

class SettingsFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!isAuthorized()) {
            onNotAuthorized()
            return
        }

        // Найдём кнопку выхода
        val btnSignOut = view.findViewById<com.google.android.material.button.MaterialButton>(R.id.btnSignOut)

        btnSignOut.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, LoginFragment())
                .commitAllowingStateLoss()
        }
    }
}