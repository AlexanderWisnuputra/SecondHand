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
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.secondhand.Helper
import com.example.secondhand.R
import com.example.secondhand.databinding.FragmentProfileBinding
import kotlinx.coroutines.launch

class Profile : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var dataStore: DataStore<Preferences>
    private lateinit var sharedPref: Helper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val profileBinding = FragmentProfileBinding.inflate(inflater, container, false)
        binding = profileBinding
        return profileBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref = Helper(requireContext())
        dataStore = requireContext().createDataStore(name = "user")
        binding.apply {
            textUbahAkun.setOnClickListener {findNavController().navigate(R.id.action_profileDetail_to_changeAcc)}
            textSettingAcc.setOnClickListener { toSettingAcc() }
            textLogout.setOnClickListener { logout() }
            binding.userimageimp.setOnClickListener{
                findNavController().navigate(R.id.action_profileDetail_to_changeAcc)
            }
            val image = sharedPref.getSell("imageusr")
            Glide.with(requireActivity())
                .load(image)
                .into(binding.userimageimp)
            binding.cardViews.visibility = View.INVISIBLE
        }
    }

    private fun logout() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("Yakin ingin keluar?")
            .setPositiveButton("Ya") {_, _->
                lifecycleScope.launch{
                    save("login", "")
                    sharedPref.putEmail("em","")
                    sharedPref.putEmail("pass","")
                    sharedPref.putAT("AT", "")
                    sharedPref.putSell("imageusr","")
                }
                Toast.makeText(requireContext(), "Anda berhasil logout!", Toast.LENGTH_LONG).show()
            }
            .setNegativeButton("Tidak") {dialog, _->
                dialog.dismiss()
            }
            .show()
    }

    private fun toSettingAcc() {
        findNavController().navigate(R.id.action_profileDetail_to_settingAcc)
    }

    private suspend fun save(key: String, value: String) {
        val dataStoreKey = preferencesKey<String>(key)
        dataStore.edit { settings ->
            settings[dataStoreKey] = value
        }
    }
}
