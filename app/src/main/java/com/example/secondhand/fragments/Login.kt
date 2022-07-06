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
import com.example.secondhand.dao.UserViewModel
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
        sharedPref = Helper(requireContext())
        binding.apply {
            tvtoReg.setOnClickListener { toRegister() }
            btnLogin.setOnClickListener { toHome() }
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
                        saveSession(binding.etEmail.text.toString())
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
    private fun saveSession(email: String) {

        sharedPref.putEmail("email", email)
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

    private suspend fun getUser(){
        ServiceBuilder.instance().loginUser(UserAcessToken(binding.etEmail.text.toString(),binding.etPassword.text.toString(),null)
        ).enqueue(object : Callback<UserAcessToken> {
            override fun onResponse(call: Call<UserAcessToken>, response: Response<UserAcessToken>) {
                        if (response.isSuccessful) {
                            response.body()
                            val at: String = response.body()?.accessToken.toString()
                            sharedPref.putAT("AT", at)
                        } else Toast.makeText(context,response.errorBody()!!.string(),Toast.LENGTH_SHORT).show()
                    }
            override fun onFailure(call: Call<UserAcessToken>, t: Throwable) {
                println(t.message)
                }
        })
    }
}
