package com.appdev.harvest.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.appdev.harvest.R
import com.google.firebase.auth.FirebaseAuth

class DiaryFragment : BaseFragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Подключаем разметку fragment_diary.xml
        return inflater.inflate(R.layout.fragment_diary, container, false)
    }
}