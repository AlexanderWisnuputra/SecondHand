package com.example.secondhand

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.secondhand.databinding.FragmentHistoryDetailBinding
import com.example.secondhand.databinding.FragmentOrderListDetailBinding


class OrderListDetail : Fragment() {
    private lateinit var binding: FragmentOrderListDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentOrderListDetailBinding =
            FragmentOrderListDetailBinding.inflate(inflater, container, false)
        binding = fragmentOrderListDetailBinding
        return fragmentOrderListDetailBinding.root
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
