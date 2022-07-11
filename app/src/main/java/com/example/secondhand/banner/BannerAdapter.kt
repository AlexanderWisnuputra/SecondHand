package com.example.secondhand.banner

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.secondhand.entity.Banner
import com.example.secondhand.R
import com.example.secondhand.history.HistoryAdapter
import kotlinx.android.synthetic.main.fragment_home.view.*

class BannerAdapter (private val banner: MutableList<Banner>) : RecyclerView.Adapter<BannerAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var bannerPicture = itemView.findViewById<ImageView>(R.id.bannerr)

        fun bind(banner: Banner) {
            Glide.with(itemView)
                .load(banner.imageUrl)
                .into(bannerPicture)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.banner, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = banner.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(banner[position])

}