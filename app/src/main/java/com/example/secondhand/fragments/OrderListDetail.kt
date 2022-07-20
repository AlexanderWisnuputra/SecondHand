package com.example.secondhand.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.secondhand.Helper
import com.example.secondhand.R
import com.example.secondhand.api.ServiceBuilder
import com.example.secondhand.databinding.FragmentOrderListDetailBinding
import com.example.secondhand.entity.Product
import com.example.secondhand.entity.SellerProductItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderListDetail: Fragment() {
    private lateinit var binding: FragmentOrderListDetailBinding
    private lateinit var sharedPref: Helper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val orderListDetail =
            FragmentOrderListDetailBinding.inflate(inflater, container, false)
        binding = orderListDetail
        return orderListDetail.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        sharedPref = Helper(requireContext())
        super.onViewCreated(view, savedInstanceState)
        val ids = arguments?.getInt("ids")
        val nameProduct = arguments?.getString("name_product")
        val categoryProduct = arguments?.getString("category_product")
        val priceProduct = arguments?.getString("price_product")
        val poster = arguments?.getString("poster")
        Glide.with(this)
            .load(poster)
            .into(binding.imageView3)
        binding.detailHistoryName.text = nameProduct
        binding.detailHistoryCategory.text = categoryProduct
        binding.detailHistoryPrice.text = priceProduct
        binding.button7.setOnClickListener {
            deleteProduct(ids!!)
        }
        binding.imageView4.setOnClickListener {
            it.findNavController().navigate(R.id.action_historyDetail_to_list)
        }
    }
    private fun deleteProduct(id: Int) {
        val api = ServiceBuilder.instance()
        var x = sharedPref.getAT("AT")

        api.deleteProduct(x, id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {

                    val x = response.code().toString()
                    findNavController().popBackStack()
                    Toast.makeText(context,"$x + Produk Berhasil Dihapus",Toast.LENGTH_SHORT).show()
                    }
                }
            override fun onFailure(call: Call<Void>, t: Throwable) {
                println(t.message)
                Toast.makeText(context,"Produk Gagal Dihapus",Toast.LENGTH_SHORT).show()

            }
        })
    }
}