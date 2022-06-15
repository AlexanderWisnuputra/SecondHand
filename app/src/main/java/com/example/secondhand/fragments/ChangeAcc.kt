package com.example.secondhand.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.secondhand.R
import com.example.secondhand.databinding.FragmentChangeAccBinding


class ChangeAcc : Fragment() {
    private lateinit var binding: FragmentChangeAccBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_change_acc, container, false)
    }


}