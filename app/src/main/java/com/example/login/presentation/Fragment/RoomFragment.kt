package com.example.login.presentation.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.login.R
import com.example.login.databinding.FragmentRoomBinding

class RoomFragment : Fragment() {
    private var mbinding: FragmentRoomBinding ?= null
    private val binding get() = mbinding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mbinding = FragmentRoomBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
}