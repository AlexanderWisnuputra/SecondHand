package com.example.secondhand.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.secondhand.Helper
import com.example.secondhand.api.ServiceBuilder
import com.example.secondhand.databinding.FragmentProductMatchBinding
import com.example.secondhand.wishlist.WishlistVM
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProductMatch : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentProductMatchBinding
    private lateinit var sharedPref: Helper
    private val pid: ProductMatchArgs by navArgs()

    private val vmod2: WishlistVM by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val negotiatePriceBinding = FragmentProductMatchBinding.inflate(inflater, container, false)
        binding = negotiatePriceBinding
        return negotiatePriceBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref = Helper(requireContext())
        var x = sharedPref.getAT("AT")
        var id = pid.productid
        var ids = pid.pid
        vmod2.patchStatus(x,id,"accepted")
        deleteProduct(ids)
        val nameProduct = this.arguments?.getString("named")
        val priceProduct = this.arguments?.getString("priced")
        val poster = this.arguments?.getString("posterd")

        binding.whatsapp.setOnClickListener {
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Halo")
            sendIntent.type = "text/plain"
            sendIntent.setPackage("com.whatsapp")
            startActivity(Intent.createChooser(sendIntent, ""))
            startActivity(sendIntent)
        }
    }
    fun deleteProduct(id: Int) {
        val api = ServiceBuilder.instance()
        var x = sharedPref.getAT("AT")
        api.deleteProduct(x, id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
            }
            override fun onFailure(call: Call<Void>, t: Throwable) {
                println(t.message)
            }
        })

    }
    }