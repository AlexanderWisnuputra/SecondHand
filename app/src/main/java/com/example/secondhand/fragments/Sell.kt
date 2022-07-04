package com.example.secondhand.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.secondhand.Helper
import com.example.secondhand.R
import com.example.secondhand.databinding.FragmentSellBinding
import kotlinx.android.synthetic.main.fragment_sell.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class Sell : Fragment() {
    private lateinit var binding: FragmentSellBinding
    private lateinit var dataStore: DataStore<Preferences>
    private lateinit var sharedPref: Helper

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
            btnTerbit.setOnClickListener { }
            fotoProduk.setOnClickListener { openGallery() }
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        galleryResult.launch("image/*")
    }

    private val galleryResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { result ->
            binding.fotoProduk.setImageURI(result)
            val gambarProduk = result.toString()
            sharedPref.putSell("picture", gambarProduk)

                requireActivity().runOnUiThread {
                    Glide.with(requireActivity())
                        .load(result)
                        .into(fotoProduk)
                }
            }

    private fun toTerbit() {
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
}