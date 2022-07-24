package com.example.secondhand.wishlist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.secondhand.R
import com.example.secondhand.entity.BidStatus
import com.example.secondhand.entity.Notification
import com.example.secondhand.entity.Wishlist
import com.example.secondhand.fragments.BuyerInfoDirections
import kotlinx.android.synthetic.main.wishlist_item.view.*

class WishlistAdapter2 (private val notification: MutableList<BidStatus>,
                        private val mainInterface: WishlistInterface
    ) : RecyclerView.Adapter<WishlistAdapter2.ViewHolder>() {

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            var history_image = itemView.findViewById<ImageView>(R.id.wishimage)
            var history_price = itemView.findViewById<TextView>(R.id.wishlistprice)
            var history_name = itemView.findViewById<TextView>(R.id.wishlistname)
            var history_category = itemView.findViewById<TextView>(R.id.wishstatus)
            var history_date = itemView.findViewById<TextView>(R.id.wishlistdesc)
            fun bind(product: BidStatus) {
                val price = "Penawaran Rp. ${product.priceID}"
                val bp = "Harga Rp. ${product.normalPrice}"
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

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(notification[position])
            holder.itemView.button10.setOnClickListener {
                //YEY HARGA KAMU DITERIMA!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                // TARO FUNGSI DELETE DARI FRAGEMENT UPDATE SELL STATUS
                holder.itemView.button7.visibility = View.GONE
                holder.itemView.button8.visibility = View.GONE
                holder.itemView.button9.visibility = View.GONE
                holder.itemView.button10.visibility = View.GONE

            }
            holder.itemView.button9.setOnClickListener {
                val action = BuyerInfoDirections.actionBuyerInfoToDecline2()
                holder.itemView.findNavController().navigate(action)
                holder.itemView.button7.visibility = View.GONE
                holder.itemView.button8.visibility = View.GONE
                holder.itemView.button9.visibility = View.GONE
                holder.itemView.button10.visibility = View.GONE
            }
                holder.itemView.button10.setOnClickListener {
                    var q = notification[position].id
                    var s = notification[position].productID
                    val action = BuyerInfoDirections.actionBuyerInfoToProductMatch(q,s)
                    holder.itemView.findNavController().navigate(action)
                }

        }
        }

