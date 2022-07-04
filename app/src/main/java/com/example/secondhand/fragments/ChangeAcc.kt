package com.example.secondhand.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.secondhand.Helper
import com.example.secondhand.R
import com.example.secondhand.dao.UserViewModel
import com.example.secondhand.databinding.FragmentChangeAccBinding
import com.example.secondhand.entity.Login
import com.example.secondhand.entity.User
import com.example.secondhand.sellerProduct.ServiceBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class ChangeAcc : Fragment() {
    private lateinit var binding: FragmentChangeAccBinding
    private lateinit var dataStore: DataStore<Preferences>
    private val viewModel: UserViewModel by viewModel()
    private val args: ChangeAccArgs by navArgs()
    private lateinit var sharedPref: Helper

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
        sharedPref = Helper(requireContext())
        dataStore = requireContext().createDataStore(name = "user")
        binding.apply {
            btnEdit.setOnClickListener {
                saveData()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launch{
            val loginCheck = read("login")
            if (loginCheck == "") {
                login()
            }
        }
    }

    private fun saveData() {
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.userProfile(
                args.dataAcc.id!!,
                binding.namaUbah.text.toString(),
                args.dataAcc.email,
                args.dataAcc.password,
                binding.kota.text.toString(),
                args.dataAcc.noHP,
                args.dataAcc.picture
            )
            activity?.runOnUiThread {
                Toast.makeText(requireContext(),"Data Berhasil ditambahkan", Toast.LENGTH_LONG).show()
                findNavController().navigate(R.id.action_changeAcc_to_profileDetail)
            }
        }
        lifecycleScope.launch(Dispatchers.IO){
            editUser()
        }
    }

    private fun login() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("Anda belum login, apakah anda ingin login ?")
            .setPositiveButton("Ya") {_, _->
                findNavController().navigate(R.id.action_changeAcc_to_login)
            }
            .setNegativeButton("Tidak") {_, _->
                findNavController().navigate(R.id.action_changeAcc_to_profileDetail)
                Toast.makeText(requireContext(), "Untuk menambahkan info, anda harus login terlebih dahulu", Toast.LENGTH_LONG).show()
            }
            .show()
    }

    private suspend fun read(key: String): String? {
        val dataStoreKey = preferencesKey<String>(key)
        val preferences = dataStore.data.first()
        return preferences[dataStoreKey]
    }
    // gambar buat error
    private suspend fun editUser() {
        var x =sharedPref.getAT("AT")
            ServiceBuilder.instance().changeDetail(
            x,
            User(
                binding.namaUbah.text.toString(),
                args.dataAcc.email,
                args.dataAcc.password,
                args.dataAcc.noHP,
                args.dataAcc.alamat,
                args.dataAcc.password,
                binding.kota.text.toString()
            )
        )
    }
}
