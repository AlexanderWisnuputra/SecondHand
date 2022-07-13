package com.example.secondhand.banner

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.secondhand.entity.Banner
import com.example.secondhand.databinding.HomeBannerListLayoutBinding

class BannerAdapter(private val onClick: (Banner) -> Unit) :
    ListAdapter<BannerAdapter, BannerAdapter.ViewHolder>(CommunityComparator()) {


    class ViewHolder(private val binding: HomeBannerListLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            currentBanner: Banner,
            onClick: (Banner) -> Unit
        ) {
            binding.root.setOnClickListener {
                onClick(currentBanner)
            }
            Glide.with(binding.ivBanner).load(currentBanner.imageUrl).into(binding.ivBanner)

        }

    }

    class CommunityComparator : DiffUtil.ItemCallback<Banner>() {
        override fun areItemsTheSame(
            oldItem: Banner,
            newItem: Banner
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: Banner,
            newItem: Banner
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = HomeBannerListLayoutBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onClick)
    }

}