package com.example.secondhand.fragments

import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.secondhand.Helper
import com.example.secondhand.R
import com.example.secondhand.api.ServiceBuilder
import com.example.secondhand.databinding.FragmentBuyerProductAddBinding
import com.example.secondhand.databinding.NegotiatePriceBinding
import com.example.secondhand.entity.Bid
import com.example.secondhand.entity.User
import com.example.secondhand.entity.UserAcessToken
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NegotiatePrice : BottomSheetDialogFragment() {
    private lateinit var sharedPref: Helper

    private lateinit var binding: NegotiatePriceBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val negotiatePriceBinding = NegotiatePriceBinding.inflate(inflater, container, false)
        binding = negotiatePriceBinding
        return negotiatePriceBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref = Helper(requireContext())
        val nameProduct = this.arguments?.getString("named")
        val priceProduct = this.arguments?.getString("priced")
        val poster = this.arguments?.getString("posterd")

        binding.namaBarang.text = nameProduct
        binding.hargaBarang.text = priceProduct

        Glide.with(this)
            .load(poster)
            .into(binding.posterBarang)

        binding.btnKirim.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                bidPrice()
            }
        }
    }

    private suspend fun bidPrice() {
        var x = sharedPref.getAT("AT")
        val bidid = this.requireArguments().getInt("id")
        ServiceBuilder.instance().bidPrice(
            x, Bid(
                bidid,
                binding.bid.text.toString().toInt()
            )
        ).enqueue(object : Callback<Bid> {
            override fun onResponse(call: Call<Bid>, response: Response<Bid>) {
                if (response.isSuccessful) {
                    response.body()
                    Toast.makeText(context,"Anda Berhasil Bid",Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_NegotiatePrice_to_buyer_Product_Add)
                } else Toast.makeText(context,"Gagal Menawar",Toast.LENGTH_SHORT).show()
            }
            override fun onFailure(call: Call<Bid>, t: Throwable) {
                println(t.message)
            }
        })
    }
    }




