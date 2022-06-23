package com.example.secondhand.fragments

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.secondhand.R
import com.example.secondhand.databinding.FragmentProfileBinding
import kotlinx.coroutines.launch

class Profile : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var dataStore: DataStore<Preferences>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val profileBinding = FragmentProfileBinding.inflate(inflater, container, false)
        binding = profileBinding
        return profileBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataStore = requireContext().createDataStore(name = "user")
        binding.apply {
            textUbahAkun.setOnClickListener { toUbahAkun() }
            textSettingAcc.setOnClickListener { toSettingAcc() }
            textLogout.setOnClickListener { logout() }
        }
    }

    private fun logout() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("Yakin ingin keluar?")
            .setPositiveButton("Ya") {_, _->
                lifecycleScope.launch{
                    save("login", "")
                }
                findNavController().navigate(R.id.action_profileDetail_to_login)
            }
            .setNegativeButton("Tidak") {dialog, _->
                dialog.dismiss()
            }
            .show()
    }

    private fun toSettingAcc() {
        findNavController().navigate(R.id.action_profileDetail_to_settingAcc)
    }

    private fun toUbahAkun() {
        findNavController().navigate(R.id.action_profileDetail_to_changeAcc)
    }

    private suspend fun save(key: String, value: String) {
        val dataStoreKey = preferencesKey<String>(key)
        dataStore.edit { settings ->
            settings[dataStoreKey] = value
        }
    }

}