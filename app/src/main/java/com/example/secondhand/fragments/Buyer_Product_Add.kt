package com.example.secondhand.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.secondhand.R
import com.example.secondhand.databinding.FragmentBuyerProductAddBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class Buyer_Product_Add : Fragment() {

    private lateinit var binding: FragmentBuyerProductAddBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val buyerProductAddBinding = FragmentBuyerProductAddBinding.inflate(inflater, container, false)
        binding = buyerProductAddBinding
        return buyerProductAddBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = requireArguments().getInt("id")
        val nameProduct = arguments?.getString("name_product")
        val categoryProduct = arguments?.getString("category_product")
        val descriptionProduct = arguments?.getString("description_product")
        val priceProduct = arguments?.getString("price_product")
        val poster = arguments?.getString("poster")

        Glide.with(this)
            .load(poster)
            .into(binding.imageView3)

        binding.textView5.text = nameProduct
        binding.textView6.text = categoryProduct
        binding.textView7.text = priceProduct
        binding.smallerDetail.text = descriptionProduct

        binding.imageView4.setOnClickListener {
            it.findNavController().navigate(R.id.action_buyer_Product_Add_to_home)
        }

        val bundle = Bundle().apply {
            putInt("id",id)
            putString("named", nameProduct)
            putString("posterd", poster)
            putString("priced", priceProduct)
        }
        val fragment = BottomSheetDialogFragment()
        fragment.arguments = bundle
        binding.button5.setOnClickListener {
            it.findNavController().navigate(R.id.action_buyer_Product_Add_to_NegotiatePrice, bundle)
        }
    }

}