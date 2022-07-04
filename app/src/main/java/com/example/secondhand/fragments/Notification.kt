package com.example.secondhand.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.secondhand.R
import com.example.secondhand.databinding.FragmentNotificationBinding

class Notification : Fragment() {
    private lateinit var binding: FragmentNotificationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentNotificationBinding = FragmentNotificationBinding.inflate(inflater, container, false)
        binding = fragmentNotificationBinding
        return fragmentNotificationBinding.root
    }

}