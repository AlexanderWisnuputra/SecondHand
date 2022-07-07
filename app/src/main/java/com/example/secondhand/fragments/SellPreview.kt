package com.example.secondhand.fragments

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.secondhand.Helper
import com.example.secondhand.databinding.FragmentSellPreviewBinding
import com.example.secondhand.sellerProduct.SPViewModel
import java.io.ByteArrayOutputStream
import java.io.File
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
        setupViewModel()
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

    private fun setupViewModel(){
        vmod = ViewModelProvider(this).get(SPViewModel::class.java)
    }
    private fun sellProduct() {
    }
    fun convertBitmapToFile(bitmap: Bitmap, fileNameToSave: String):File? {
        var file: File? = null
        return try {
            file = File(Environment.getExternalStorageDirectory().toString() + File.separator + fileNameToSave)
            file.createNewFile()
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, baos) // It can be also saved it as JPEG
            val bitmapdata = baos.toByteArray()
            val fos = FileOutputStream(file)
                fos.write(bitmapdata)
            fos.flush()
            fos.close()
            return file
        }
        catch (e: Exception) {
            e.printStackTrace()
            file // it will return null
        }
    }
}