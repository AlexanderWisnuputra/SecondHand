package com.example.secondhand.fragments

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Base64
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
import com.example.secondhand.sellerProduct.SPViewModel
import com.example.secondhand.sellerProduct.ServiceBuilder
import kotlinx.android.synthetic.main.fragment_sell.*
import kotlinx.android.synthetic.main.fragment_sell.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okio.ByteString.Companion.decodeBase64
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


class SellPreview : Fragment() {
    private lateinit var binding: FragmentSellPreviewBinding
    private lateinit var dataStore: DataStore<Preferences>
    private lateinit var sharedPref: Helper
    private lateinit var vmod: SPViewModel
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
                sellProduct()

        }
    }

    override fun onStart() {
        super.onStart()

        val namaProduk = sharedPref.getSell("nama")
        val hargaProduk = sharedPref.getSell("harga")
        val kategori = sharedPref.getSell("kategori")
        val deskripsi = sharedPref.getSell("deskripsi")
        val gambar = sharedPref.getFilter("picture")

        Glide.with(requireActivity())
            .load(gambar)
            .into(binding.imageView3)

        binding.textView5.setText("$namaProduk").toString()
        binding.textView7.setText("$hargaProduk").toString()
        binding.textView6.setText("$kategori").toString()
        binding.smallerDetail.setText("$deskripsi").toString()
    }
    private fun sellProduct() {
        var x = sharedPref.getAT("AT")
        val gambar = sharedPref.getSell("picture")
        var imageBytes = Base64.decode(gambar, 0)

        var q =BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

        convertBitmapToFile(q)

        val file = File(Environment.getExternalStorageDirectory(),"image/png")
            vmod.PostProduct(
                acstkn = x,"${binding.textView5.text}","${binding.smallerDetail.text}",binding.textView7.text.toString().toInt(),
                listOf(100),"Jakarta",file
            )
    }
    fun convertBitmapToFile(bitmap: Bitmap):File {
        val file = File(Environment.getExternalStorageDirectory(),"image/png")
        file.createNewFile()
        if (!file.exists()) {
            file.mkdirs();
        }
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, baos) // It can be also saved it as JPEG
        val bitmapdata = baos.toByteArray()
        FileOutputStream(file).write(bitmapdata)
        FileOutputStream(file).flush()
        FileOutputStream(file).close()
        return file
    }
}