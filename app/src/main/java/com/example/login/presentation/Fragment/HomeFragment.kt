package com.example.login.presentation.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.room.Room
import com.example.login.R
import com.example.login.databinding.FragmentHomeBinding
import com.example.login.db.room.Database

class HomeFragment : Fragment() {

    //viewBinding
    private var mbinding: FragmentHomeBinding ?= null
    private val binding get() = mbinding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mbinding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mbinding = null
    }

}