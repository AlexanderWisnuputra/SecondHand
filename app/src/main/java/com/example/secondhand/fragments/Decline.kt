package com.example.secondhand.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.secondhand.databinding.FragmentDialogAlertBinding

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences

import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.secondhand.Helper
import com.example.secondhand.R
import com.example.secondhand.wishlist.WishlistVM
import kotlinx.android.synthetic.main.fragment_dialog_alert.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class Decline : Fragment() {
    private val vmod2: WishlistVM by viewModel()
    private lateinit var sharedPref: Helper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dialog_alert, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref = Helper(requireContext())
        var x = sharedPref.getAT("AT")
        var id = sharedPref.getSell("LOCK").toString().toInt()

        vmod2.patchStatus(x,id,"decline")

        findNavController().popBackStack()
    }
}


