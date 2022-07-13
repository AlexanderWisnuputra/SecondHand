package com.example.secondhand.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.secondhand.Helper
import com.example.secondhand.R
import com.example.secondhand.databinding.FragmentLoginBinding
import com.example.secondhand.entity.UserAcessToken
import com.example.secondhand.api.ServiceBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Login : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var dataStore: DataStore<Preferences>
    private lateinit var sharedPref: Helper

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
        sharedPref = Helper(requireContext())
        binding.apply {
            tvtoReg.setOnClickListener { toRegister() }
            btnLogin.setOnClickListener { toHome() }
        }
    }

    private fun toHome() {
            lifecycleScope.launch(Dispatchers.IO) {
                save("login", "LoggedIn")
                getUser()
            }
    }

    private fun toRegister(){
        findNavController().navigate(R.id.action_login_to_register)
    }

    private suspend fun save(key: String, value: String) {
        val dataStoreKey = preferencesKey<String>(key)
        dataStore.edit { settings ->
            settings[dataStoreKey] = value
        }
    }

    private suspend fun getUser(){
        ServiceBuilder.instance().loginUser(UserAcessToken(binding.etEmail.text.toString(),binding.etPassword.text.toString(),null)
        ).enqueue(object : Callback<UserAcessToken> {
            override fun onResponse(call: Call<UserAcessToken>, response: Response<UserAcessToken>) {
                        if (response.isSuccessful) {
                            response.body()
                            val at: String = response.body()?.accessToken.toString()
                            val pass: String = binding.etPassword.text.toString()
                            sharedPref.putAT("AT", at)
                            sharedPref.putEmail("em",binding.etEmail.text.toString() )
                            sharedPref.putEmail("pass",pass)
                            Toast.makeText(context,"Anda Berhasil Login",Toast.LENGTH_SHORT).show()
                            findNavController().navigate(R.id.action_login_to_home)
                        } else Toast.makeText(context,"Email atau Password Salah!",Toast.LENGTH_SHORT).show()
                    }
            override fun onFailure(call: Call<UserAcessToken>, t: Throwable) {
                println(t.message)
                }
        })
    }
}
