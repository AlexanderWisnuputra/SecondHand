package com.example.secondhand.fragments

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.secondhand.Helper
import com.example.secondhand.R
import com.example.secondhand.URIPathHelper
import com.example.secondhand.databinding.FragmentSellPreviewBinding
import com.example.secondhand.repository.ProductRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class SellPreview : Fragment() {
    private lateinit var binding: FragmentSellPreviewBinding
    private lateinit var sharedPref: Helper
    private var imageUri : Uri?= null
    private val repo = ProductRepo()
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
            clear()
            findNavController().navigate(R.id.action_seller_Product_Add_to_list)
        }
    }

    override fun onStart() {
        super.onStart()

        val namaProduk = sharedPref.getSell("nama")
        val hargaProduk = sharedPref.getSell("harga")
        val kategori = sharedPref.getSell("kategori")
        val deskripsi = sharedPref.getSell("deskripsi")
        val image = sharedPref.getSell("image")
        imageUri = image.toString().toUri()
        Glide.with(requireActivity())
            .load(image)
            .into(binding.imageView3)

        binding.textView5.setText("$namaProduk").toString()
        binding.textView7.setText("$hargaProduk").toString()
        binding.textView6.setText("$kategori").toString()
        binding.smallerDetail.setText("$deskripsi").toString()
    }

    private fun sellProduct() {
        val namaProduk = sharedPref.getSell("nama")
        val hargaProduk = sharedPref.getSell("harga")
        val deskripsi = sharedPref.getSell("deskripsi")
        val imageFile = if (imageUri == null) {
            null
        } else {
            File(URIPathHelper.getPath(requireContext(), imageUri!!).toString())
        }
        val nameBody = namaProduk?.toRequestBody("text/plain".toMediaTypeOrNull())
        val priceBody = hargaProduk?.toRequestBody("text/plain".toMediaTypeOrNull())
        val cityBody = "Nikothin".toRequestBody("text/plain".toMediaTypeOrNull())
        val categoryBody = "96".toRequestBody("text/plain".toMediaTypeOrNull())
        val descriptionBody = deskripsi?.toRequestBody("text/plain".toMediaTypeOrNull())
        val requestImage = imageFile?.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val imageBody = requestImage?.let {
            MultipartBody.Part.createFormData("image", imageFile?.name, it)
        }
        lifecycleScope.launch(Dispatchers.IO) {
            var x = sharedPref.getAT("AT")

            repo.addProuct(
                x,
                nameBody!!,
                descriptionBody!!,
                priceBody!!,
                categoryBody!!,
                cityBody!!,
                imageBody
            )
        }
    }
    private fun clear(){
       sharedPref.putSell("nama","")
       sharedPref.putSell("harga","")
       sharedPref.putSell("kategori","")
       sharedPref.putSell("deskripsi","")
       sharedPref.putSell("image","")
       }
    }
