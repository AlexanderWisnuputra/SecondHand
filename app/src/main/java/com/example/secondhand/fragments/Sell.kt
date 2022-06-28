package com.example.secondhand.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.secondhand.R
import com.example.secondhand.databinding.FragmentSellBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class Sell : Fragment() {
    private lateinit var binding: FragmentSellBinding
    private lateinit var dataStore: DataStore<Preferences>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val sellBinding = FragmentSellBinding.inflate(inflater, container, false)
        binding = sellBinding
        return sellBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataStore = requireContext().createDataStore(name = "user")
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

    private fun login() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("Anda belum login, apakah anda ingin login ?")
            .setPositiveButton("Ya") {_, _->
                findNavController().navigate(R.id.action_sell_to_login)
            }
            .setNegativeButton("Tidak") {_, _->
                findNavController().navigate(R.id.action_sell_to_home)
                Toast.makeText(requireContext(), "Untuk memposting barang anda harus login terlebih dahulu", Toast.LENGTH_LONG).show()
            }
            .show()
    }

    private suspend fun read(key: String): String? {
        val dataStoreKey = preferencesKey<String>(key)
        val preferences = dataStore.data.first()
        return preferences[dataStoreKey]
    }
}