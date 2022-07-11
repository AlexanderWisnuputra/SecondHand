package com.example.secondhand

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.secondhand.databinding.FragmentBottomSheetDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class bottom_sheet_dialog : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentBottomSheetDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bottom_sheet_dialog, container, false)

        val nameProduct = this.arguments?.getString("named")
        val priceProduct = this.arguments?.getString("priced")
        val poster = this.arguments?.getString("posterd")

        binding.namaBarang.text = nameProduct
        binding.hargaBarang.text = priceProduct

        Glide.with(this)
            .load(poster)
            .into(binding.posterBarang)
    }
}