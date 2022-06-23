package com.example.secondhand.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.navigation.fragment.navArgs
import com.example.secondhand.R
import com.example.secondhand.dao.UserViewModel
import com.example.secondhand.databinding.FragmentChangeAccBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class ChangeAcc : Fragment() {
    private lateinit var binding: FragmentChangeAccBinding
    private lateinit var dataStore: DataStore<Preferences>
    private val viewModel: UserViewModel by viewModel()
    private val args: ChangeAccArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val changeAccBinding = FragmentChangeAccBinding.inflate(inflater, container, false)
        binding = changeAccBinding
        return changeAccBinding.root
    }
}