package com.example.secondhand.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.secondhand.R
import com.example.secondhand.api.ServiceBuilder
import com.example.secondhand.databinding.FragmentHistoryDetailBinding
import com.example.secondhand.entity.Product
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryDetail : Fragment() {
    private lateinit var binding: FragmentHistoryDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentHistoryDetailBinding =
            FragmentHistoryDetailBinding.inflate(inflater, container, false)
        binding = fragmentHistoryDetailBinding
        return fragmentHistoryDetailBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

        binding.imageView4.setOnClickListener {
            it.findNavController().navigate(R.id.action_historyDetail_to_list)
        }
    }
}