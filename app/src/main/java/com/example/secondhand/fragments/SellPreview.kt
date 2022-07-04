package com.example.secondhand.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.secondhand.Helper
import com.example.secondhand.databinding.FragmentSellPreviewBinding
import com.example.secondhand.entity.Product
import com.example.secondhand.sellerProduct.ServiceBuilder
import kotlinx.android.synthetic.main.fragment_sell.*
import kotlinx.android.synthetic.main.fragment_sell.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SellPreview : Fragment() {
    private lateinit var binding: FragmentSellPreviewBinding
    private lateinit var dataStore: DataStore<Preferences>
    private lateinit var sharedPref: Helper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val sellPreviewBinding  = FragmentSellPreviewBinding.inflate(inflater, container, false)
        binding = sellPreviewBinding
        return sellPreviewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref = Helper(requireContext())
        binding.button5.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                sellProduct()
            }
        }
    }

    override fun onStart() {
        super.onStart()

        val namaProduk = sharedPref.getSell("nama")
        val hargaProduk = sharedPref.getSell("harga")
        val kategori = sharedPref.getSell("kategori")
        val deskripsi = sharedPref.getSell("deskripsi")
        val gambar = sharedPref.getSell("picture")
        Glide.with(requireActivity())
            .load(gambar)
            .into(binding.imageView3)

        binding.textView5.setText("$namaProduk").toString()
        binding.textView7.setText("$hargaProduk").toString()
        binding.textView6.setText("$kategori").toString()
        binding.smallerDetail.setText("$deskripsi").toString()
    }
    private suspend fun sellProduct() {
        var x = sharedPref.getAT("AT")
        val gambar = sharedPref.getSell("picture")
     /*   ServiceBuilder.instance().addProduct(
            x,
            Product(
                null,
                "${binding.textView5.text}",
                "${binding.textView5.text}",
                binding.textView7.text.toString().toInt(),

                "$gambar",
                "${binding.textView5.text}",
                null
            )
        )*/
    }
}