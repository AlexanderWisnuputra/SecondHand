package com.example.secondhand.wishlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.secondhand.R
import com.example.secondhand.entity.Notification
import com.example.secondhand.entity.Wishlist


class WishlistAdapter (private val notification: MutableList<Wishlist>,
        private val mainInterface: WishlistInterface
    ) : RecyclerView.Adapter<WishlistAdapter.ViewHolder>() {

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            var history_image = itemView.findViewById<ImageView>(R.id.wishimage)
            var history_price = itemView.findViewById<TextView>(R.id.wishlistprice)
            var history_name = itemView.findViewById<TextView>(R.id.wishlistname)
            var history_category = itemView.findViewById<TextView>(R.id.wishstatus)
            var history_date = itemView.findViewById<TextView>(R.id.wishlistdesc)

            fun bind(product: Wishlist) {
                val price = "Rp. ${product.basePrice}"
                val bp = "Rp. ${product.location}"
                history_name.text = product.name
                history_category.text = bp
                history_price.text = price
                history_date.text = product.description
                Glide.with(itemView)
                    .load(product.imageUrl)
                    .into(history_image)
                itemView.setOnClickListener {
                    mainInterface.click(product)
                }
            }
        }

        fun updateList(it: List<Wishlist>) {
            notification.clear()
            notification.addAll(it)
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.wishlist_item, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount() = notification.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) =
            holder.bind(notification[position])

    }
