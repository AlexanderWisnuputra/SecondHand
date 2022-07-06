package com.example.secondhand.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.secondhand.Helper
import com.example.secondhand.R
import com.example.secondhand.databinding.FragmentSettingAccBinding
import com.example.secondhand.entity.Password
import com.example.secondhand.api.ServiceBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SettingAcc : Fragment() {
    private lateinit var binding: FragmentSettingAccBinding
    private lateinit var sharedPref: Helper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val settingAccBinding = FragmentSettingAccBinding.inflate(inflater, container, false)
        binding = settingAccBinding
        return settingAccBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref = Helper(requireContext())
        binding.apply {
            accept.setOnClickListener {
                saveData()
            }
        }
    }

    private fun saveData() {
        lifecycleScope.launch(Dispatchers.IO) {
            editUser()
            activity?.runOnUiThread {
                Toast.makeText(requireContext(),"Password Berhasil diubah", Toast.LENGTH_LONG).show()
                findNavController().navigate(R.id.action_settingAcc_to_profileDetail)
            }
        }
    }

    private suspend fun editUser() {
        var x =sharedPref.getAT("AT")
        ServiceBuilder.instance().changePass(
            x,
            Password(
                "${binding.oldPass.text}",
                "${binding.newPass.text}",
                "${binding.confirm.text}"

            )
        )
    }
}
