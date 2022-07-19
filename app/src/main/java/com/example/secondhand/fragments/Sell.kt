package com.example.secondhand.fragments

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.github.dhaval2404.imagepicker.ImagePicker
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.secondhand.Helper
import com.example.secondhand.R
import com.example.secondhand.URIPathHelper
import com.example.secondhand.databinding.FragmentSellBinding
import com.example.secondhand.repository.ProductRepo
import kotlinx.android.synthetic.main.fragment_sell.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File


class Sell : Fragment() {
    private lateinit var binding: FragmentSellBinding
    private lateinit var dataStore: DataStore<Preferences>
    private lateinit var sharedPref: Helper
    private var imageUri : Uri?= null
    private val repo = ProductRepo()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val sellBinding = FragmentSellBinding.inflate(inflater, container, false)
        binding = sellBinding

        val category = resources.getStringArray(R.array.category)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, category)
        binding.kategoriText.setAdapter(arrayAdapter)

        return sellBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataStore = requireContext().createDataStore(name = "user")
        sharedPref = Helper(requireContext())
        binding.apply {
            btnPreview.setOnClickListener { toPreview() }
            btnTerbit.setOnClickListener {
                val name = binding.namaProduk.text.toString()
                val price = binding.produkHarga.text.toString()
                val location = "jakarta"
                val category =  "119"
                val description = binding.deskripsi.text.toString()
                val imageFile = if(imageUri == null) {
                    null
                }
                else{
                    File(URIPathHelper.getPath(requireContext(), imageUri!!).toString())
                }
                val nameBody = name.toRequestBody("text/plain".toMediaTypeOrNull())
                val priceBody = price.toRequestBody("text/plain".toMediaTypeOrNull())
                val cityBody = location.toRequestBody("text/plain".toMediaTypeOrNull())
                val categoryBody = category.toRequestBody("text/plain".toMediaTypeOrNull())
                val descriptionBody = description.toRequestBody("text/plain".toMediaTypeOrNull())
                val requestImage = imageFile?.asRequestBody("image/jpeg".toMediaTypeOrNull())
                val imageBody = requestImage?.let{
                    MultipartBody.Part.createFormData("image", imageFile?.name, it)
                }
                lifecycleScope.launch(Dispatchers.IO) {
                var x = sharedPref.getAT("AT")
                  repo.addProuct(
                      x,
                      nameBody,
                      descriptionBody,
                      priceBody,
                      categoryBody,
                      cityBody,
                      imageBody
                  )
                }

                Toast(requireActivity()).showCustomToast("Produk berhasil ditambahkan", requireContext() as Activity)
            }
            fotoProduk.setOnClickListener { openImagePicker()}
        }
    }

    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data
            when (resultCode) {
                Activity.RESULT_OK -> {
                    //Image Uri will not be null for RESULT_OK
                    val fileUri = data?.data
                    imageUri = fileUri
                    loadImage(fileUri)
                    sharedPref.putSell("image", fileUri.toString())
                }
            }
        }

    private fun toPreview() {
        val sharedPreferences: SharedPreferences =
            requireContext().getSharedPreferences(sharedPref.toString(), Context.MODE_PRIVATE)

        val namaProduk: String = binding.namaProduk.text.toString()
        val hargaProduk: String = binding.produkHarga.text.toString()
        val kategori: String = binding.kategori.toString()
        val deskripsi: String = binding.deskripsi.text.toString()
        val editor: SharedPreferences.Editor = sharedPreferences.edit()

        sharedPref.putSell("nama", namaProduk)
        sharedPref.putSell("harga", hargaProduk)
        sharedPref.putSell("kategori", kategori)
        sharedPref.putSell("deskripsi", deskripsi)
        editor.apply()

        findNavController().navigate(R.id.action_sell_to_seller_Product_Add)
    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launch{
            val loginCheck = read("login")
            if (loginCheck == "") {
                login()
            }
        }
    }

    private fun login() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("Anda belum login, apakah anda ingin login ?")
            .setPositiveButton("Ya") {_, _->
                findNavController().navigate(R.id.action_sell_to_login)
            }
            .setNegativeButton("Tidak") {_, _->
                findNavController().navigate(R.id.action_sell_to_home)
                Toast.makeText(requireContext(), "Untuk memposting barang anda harus login terlebih dahulu", Toast.LENGTH_LONG).show()
            }
            .setOnCancelListener {
                findNavController().navigate(R.id.action_sell_to_home)
                Toast.makeText(requireContext(), "Untuk memposting barang anda harus login terlebih dahulu", Toast.LENGTH_LONG).show()
            }
            .show()
    }

    private suspend fun read(key: String): String? {
        val dataStoreKey = preferencesKey<String>(key)
        val preferences = dataStore.data.first()
        return preferences[dataStoreKey]
    }

    private fun openImagePicker() {
        ImagePicker.with(this)
            .crop()
            .saveDir(
                File(
                    requireContext().externalCacheDir,
                    "ImagePicker"
                )
            ) //Crop image(Optional), Check Customization for more option
            .compress(1024)            //Final image size will be less than 1 MB(Optional)
            .maxResultSize(
                1080,
                1080
            )    //Final image resolution will be less than 1080 x 1080(Optional)
            .createIntent { intent ->
                startForProfileImageResult.launch(intent)
            }
    }

    private fun loadImage(uri: Uri?) {
        uri?.let {
            Glide.with(this)
                .load(it)
                .centerCrop()
                .apply(RequestOptions.bitmapTransform(RoundedCorners(16)))
                .into(fotoProduk)
        }
    }

    private fun Toast.showCustomToast(message: String, activity: Activity) {
        val layout = activity.layoutInflater.inflate (
            R.layout.toast_layout,
            activity.findViewById(R.id.toastCustom)
        )

        // set the text of the TextView of the message
        val textView = layout.findViewById<TextView>(R.id.toastText)
        textView.text = message

        // use the application extension function
        this.apply {
            setGravity(Gravity.TOP, 0, 250)
            duration = Toast.LENGTH_LONG
            view = layout
            show()
        }
    }

}