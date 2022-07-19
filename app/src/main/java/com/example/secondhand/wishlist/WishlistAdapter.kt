package com.example.secondhand.wishlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.secondhand.R
import com.example.secondhand.entity.BidStatus
import com.example.secondhand.entity.Notification
import com.example.secondhand.entity.Wishlist

// ganti jadi sellder order
class WishlistAdapter (private val notification: MutableList<BidStatus>,
        private val mainInterface: WishlistInterface
    ) : RecyclerView.Adapter<WishlistAdapter.ViewHolder>() {

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            var history_image = itemView.findViewById<ImageView>(R.id.wishimage)
            var history_price = itemView.findViewById<TextView>(R.id.wishlistprice)
            var history_name = itemView.findViewById<TextView>(R.id.wishlistname)
            var history_category = itemView.findViewById<TextView>(R.id.wishstatus)
            var history_date = itemView.findViewById<TextView>(R.id.wishlistdesc)

            fun bind(product: BidStatus) {
                val price = "Harga Rp. ${product.priceID}"
                val bp = "Penawaran Rp. ${product.normalPrice}"
                history_name.text = product.name
                history_category.text = bp
                history_price.text = price
                history_date.text = product.status
                Glide.with(itemView)
                    .load(product.img)
                    .into(history_image)
                itemView.setOnClickListener {
                    mainInterface.click(product)
                }
            }
        }

        fun updateList(it: List<BidStatus>) {
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
