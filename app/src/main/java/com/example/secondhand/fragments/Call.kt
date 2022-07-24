package com.example.secondhand.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.secondhand.Helper
import com.example.secondhand.R
import com.example.secondhand.api.ServiceBuilder
import com.example.secondhand.databinding.FragmentCallBinding
import com.example.secondhand.databinding.FragmentProductMatchBinding
import com.example.secondhand.wishlist.WishlistVM
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Call : Fragment() {
    private lateinit var binding: FragmentCallBinding
        private lateinit var sharedPref: Helper

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            val bin = FragmentCallBinding.inflate(inflater, container, false)
            binding = bin
            return bin.root
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Halo")
            sendIntent.type = "text/plain"
            sendIntent.setPackage("com.whatsapp")
            startActivity(Intent.createChooser(sendIntent, ""))
            startActivity(sendIntent)
        findNavController().popBackStack()
        }
    }

