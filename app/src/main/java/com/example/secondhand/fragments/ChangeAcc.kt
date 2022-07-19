package com.example.secondhand.fragments

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.secondhand.Helper
import com.example.secondhand.R
import com.example.secondhand.URIPathHelper
import com.example.secondhand.databinding.FragmentChangeAccBinding
import com.example.secondhand.entity.User
import com.example.secondhand.api.ServiceBuilder
import com.example.secondhand.repository.ChangeAccRepo
import com.github.dhaval2404.imagepicker.ImagePicker
import kotlinx.android.synthetic.main.fragment_change_acc.*
import kotlinx.android.synthetic.main.fragment_sell.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.koin.androidx.viewmodel.ext.android.viewModel
import retrofit2.http.Part
import java.io.File

class ChangeAcc : Fragment() {
    private lateinit var binding: FragmentChangeAccBinding
    private lateinit var dataStore: DataStore<Preferences>
    private lateinit var sharedPref: Helper
    private val repo= ChangeAccRepo()
    private var imageUri : Uri?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val changeAccBinding = FragmentChangeAccBinding.inflate(inflater, container, false)
        binding = changeAccBinding
        return changeAccBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref = Helper(requireContext())
        dataStore = requireContext().createDataStore(name = "user")
        binding.apply {
            btnEdit.setOnClickListener {
                    val name = binding.namaUbah.text.toString()
                    val email = sharedPref.getEmail("em").toString()
                    val password = sharedPref.getEmail("pass").toString()
                    val phonenumber =  binding.noTelp.text.toString()
                    val city =  binding.kota.text.toString()
                    val address = binding.alamat.text.toString()
                    val imageFile = if(imageUri == null) {
                        null
                    }
                    else{
                        File(URIPathHelper.getPath(requireContext(), imageUri!!).toString())
                    }

                    val nameBody = name.toRequestBody("text/plain".toMediaTypeOrNull())
                    val emailBody = email.toRequestBody("text/plain".toMediaTypeOrNull())
                    val passwordBody = password.toRequestBody("text/plain".toMediaTypeOrNull())
                    val phonebody = phonenumber.toRequestBody("text/plain".toMediaTypeOrNull())
                    val addressbody = address.toRequestBody("text/plain".toMediaTypeOrNull())
                    val cityBody = city.toRequestBody("text/plain".toMediaTypeOrNull())
                    val requestImage = imageFile?.asRequestBody("image/jpeg".toMediaTypeOrNull())
                    val imageBody = requestImage?.let{
                        MultipartBody.Part.createFormData("image", imageFile?.name, it)
                    }
                    lifecycleScope.launch(Dispatchers.IO) {
                        var x = sharedPref.getAT("AT")
                        repo.changeAcc(
                            x,
                            nameBody,
                            emailBody,
                            passwordBody,
                            phonebody,
                            addressbody,
                            cityBody,
                            imageBody
                        )
                    }
                    activity?.runOnUiThread {
                        Toast.makeText(requireContext(),"Data Berhasil ditambahkan", Toast.LENGTH_LONG).show()
                        val x =sharedPref.getAT("status")
                        if(x == "1"){
                            findNavController().navigate(R.id.action_changeAcc_to_list)
                            sharedPref.putAT("status", "0")
                        }
                        else{
                            findNavController().navigate(R.id.action_changeAcc_to_profileDetail)

                        }
                    }

            }
            binding.photoProfile.setOnClickListener {
                openImagePicker()
            }
            binding.camera.setOnClickListener{
                openImagePicker()
            }
            binding.userimg.setOnClickListener{
                openImagePicker()

            }
        }
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
                findNavController().navigate(R.id.action_changeAcc_to_login)
            }
            .setNegativeButton("Tidak") {_, _->
                findNavController().navigate(R.id.action_changeAcc_to_profileDetail)
                Toast.makeText(requireContext(), "Untuk menambahkan info, anda harus login terlebih dahulu", Toast.LENGTH_LONG).show()
            }
            .show()
    }

    private suspend fun read(key: String): String? {
        val dataStoreKey = preferencesKey<String>(key)
        val preferences = dataStore.data.first()
        return preferences[dataStoreKey]
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
                    sharedPref.putSell("imageusr", fileUri.toString())
                }
            }
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
                .into(userimg)
        }
        binding.camera.visibility = View.GONE
        binding.photoProfile.visibility = View.INVISIBLE
    }

}

