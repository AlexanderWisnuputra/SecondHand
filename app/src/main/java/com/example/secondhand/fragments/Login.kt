package com.example.secondhand.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.secondhand.R
import com.example.secondhand.dao.UserViewModel
import com.example.secondhand.databinding.FragmentLoginBinding
import com.example.secondhand.entity.UserAcessToken
import com.example.secondhand.sellerProduct.ServiceBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class Login : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var dataStore: DataStore<Preferences>

    private val viewModel: UserViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val loginBinding = FragmentLoginBinding.inflate(inflater, container, false)
        binding = loginBinding
        return loginBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataStore = requireContext().createDataStore(name = "user")
        binding.apply {
            tvtoReg.setOnClickListener { toRegister() }
            btnLogin.setOnClickListener { toHome() }
        }
    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launch{
            val loginCheck = read("login")
            if (loginCheck == "LoggedIn") {
                findNavController().navigate(R.id.action_login_to_home)
            }
        }
    }

    private fun toHome() {
        if (blankInputCheck()) {
            lifecycleScope.launch(Dispatchers.IO) {
                val check = viewModel.checkUserExists(
                    binding.etEmail.text.toString(),
                    binding.etPassword.text.toString()
                )
                if (check) {
                    activity?.runOnUiThread {
                        findNavController().navigate(R.id.action_login_to_home)
                    }
                    lifecycleScope.launch(Dispatchers.IO){
                        save("login", "LoggedIn")
                        getUser()
                    }
                }
                else {
                    activity?.runOnUiThread {
                        Toast.makeText(requireContext(), "Email atau password salah!", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
        else {
            Toast.makeText(requireContext(), "Data masih kosong!", Toast.LENGTH_LONG).show()
        }
    }

    private fun toRegister(){
        findNavController().navigate(R.id.action_login_to_register)
    }

    private fun blankInputCheck(): Boolean {
        return viewModel.isInputEmpty(
            binding.etEmail.text.toString(),
            binding.etPassword.text.toString()
        )
    }

    private suspend fun save(key: String, value: String) {
        val dataStoreKey = preferencesKey<String>(key)
        dataStore.edit { settings ->
            settings[dataStoreKey] = value
        }
    }

    private suspend fun read(key: String): String? {
        val dataStoreKey = preferencesKey<String>(key)
        val preferences = dataStore.data.first()
        return preferences[dataStoreKey]
    }

    //BELOM BISA DAPET ACCESS TOKEN
    private suspend fun getUser(){
        ServiceBuilder.instance().loginUser(
            UserAcessToken(
                binding.etEmail.text.toString(),
                binding.etPassword.text.toString(),
                null
            )
        )

    }
}
