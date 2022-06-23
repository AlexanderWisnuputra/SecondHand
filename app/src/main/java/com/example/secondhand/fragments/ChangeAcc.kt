package com.example.secondhand.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.secondhand.R
import com.example.secondhand.dao.UserViewModel
import com.example.secondhand.databinding.FragmentChangeAccBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnEdit.setOnClickListener { saveData() }
            btnBatal.setOnClickListener { findNavController().navigate(R.id.action_changeAcc_to_profileDetail) }
        }
    }

    private fun saveData() {
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.userProfile(
                args.dataAcc.id!!,
                args.dataAcc.name,
                args.dataAcc.email,
                binding.passReg.text.toString(),
                binding.kotaProvinsi.text.toString(),
                args.dataAcc.noHP,
                args.dataAcc.picture
            )
            activity?.runOnUiThread {
                Toast.makeText(requireContext(),"Data Berhasil ditambahkan", Toast.LENGTH_LONG).show()
                findNavController().navigate(R.id.action_changeAcc_to_profileDetail)
            }
        }
    }
}