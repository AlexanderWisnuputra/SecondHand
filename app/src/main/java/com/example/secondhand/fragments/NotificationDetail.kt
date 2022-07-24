package com.example.secondhand.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.secondhand.R
import com.example.secondhand.databinding.FragmentHistoryDetailBinding
import com.example.secondhand.databinding.FragmentNotificationDetailBinding

class NotificationDetail : Fragment() {
    private lateinit var binding: FragmentNotificationDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentNotificationDetailBinding = FragmentNotificationDetailBinding.inflate(inflater, container, false)
        binding = fragmentNotificationDetailBinding
        return fragmentNotificationDetailBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val ids = arguments?.getInt("id")
        val nameProduct = arguments?.getString("name_product")
        val categoryProduct = arguments?.getString("category_product")
        val priceProduct = arguments?.getString("price_product")
        val poster = arguments?.getString("poster")

        Glide.with(this)
            .load(poster)
            .centerCrop()
            .into(binding.imageView3)


       binding.imageView4.setOnClickListener {
            it.findNavController().navigate(R.id.action_notificationDetail_to_notification)
        }

        binding.detailHistoryName.text = nameProduct
        binding.detailHistoryCategory.text = categoryProduct
        binding.detailHistoryPrice.text = priceProduct
    }
}