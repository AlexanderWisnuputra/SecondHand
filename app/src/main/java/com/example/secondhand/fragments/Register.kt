package com.example.secondhand.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.secondhand.R
import com.example.secondhand.dao.UserViewModel
import com.example.secondhand.databinding.FragmentRegisterBinding
import com.example.secondhand.entity.User
import com.example.secondhand.sellerProduct.ServiceBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class Register : Fragment() {
    private lateinit var binding: FragmentRegisterBinding

    private val viewModel: UserViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val registerBinding = FragmentRegisterBinding.inflate(inflater, container, false)
        binding = registerBinding
        return registerBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnDaftar.setOnClickListener { toLogin() }
            tvtoLogin.setOnClickListener { findNavController().navigate(R.id.action_register_to_login) }
        }
    }

    private fun toLogin(){
        if (blankInputCheck()) {
            if (binding.passReg.length() >= 6) {
                if (confirmPass()) {
                    lifecycleScope.launch(Dispatchers.IO) {
                        viewModel.userProfile(
                            binding.namaReg.text.toString(),
                            binding.emailReg.text.toString(),
                            binding.passReg.text.toString(),
                            "",
                            0,
                            ""
                        )
                    }
                    findNavController().navigate(R.id.action_register_to_login)
                    lifecycleScope.launch(Dispatchers.IO) {
                        postUser()
                    }
                    Toast.makeText(requireContext(), "Akun berhasil dibuat", Toast.LENGTH_LONG)
                        .show()
                }
        }
            else if (binding.passReg.length() <= 6) {
                Toast.makeText(requireContext(), "Password minimal 6 huruf", Toast.LENGTH_LONG)
                    .show()
            }

            else {
                Toast.makeText(requireContext(),"Password tidak sesuai", Toast.LENGTH_LONG).show()
            }
        }
        else {
            Toast.makeText(requireContext(), "Data masih kosong!", Toast.LENGTH_LONG).show()
        }
    }

    private fun blankInputCheck(): Boolean {
        return viewModel.isInputEmpty(
            binding.namaReg.text.toString(),
            binding.emailReg.text.toString(),
            binding.passReg.text.toString(),
            binding.confPassReg.toString()
        )
    }

    private fun confirmPass(): Boolean{
        return binding.passReg.text.toString() == binding.confPassReg.text.toString()
    }
                          //PASSWORD MINIMAL 6 CHAR   
    private suspend fun postUser(){
        ServiceBuilder.instance().addUser(
            User(
                binding.namaReg.text.toString(),
                binding.emailReg.text.toString(),
                binding.passReg.text.toString(),
        10,// NOMOR HP G BS NULL, NANTI PAS DI EDIT MENU G USH GET DARI API
            "Alamat Anda",
                "https://firebasestorage.googleapis.com/v0/b/market-final-project.appspot.com/o/avatar%2FAV-1656939653050-42503866_2259264127640278_7961827119955181568_n.jpg?alt=media",
                "Kota "
            )
        )
    }

}