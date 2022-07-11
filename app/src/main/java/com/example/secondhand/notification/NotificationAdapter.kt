package com.example.secondhand.notification

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.secondhand.R
import com.example.secondhand.entity.Notification

class NotificationAdapter(private val notification: MutableList<Notification>)
    : RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var history_image = itemView.findViewById<ImageView>(R.id.history_image)
        var history_price = itemView.findViewById<TextView>(R.id.history_price)
        var history_name = itemView.findViewById<TextView>(R.id.history_name)
        var history_category = itemView.findViewById<TextView>(R.id.history_category)
        var history_date = itemView.findViewById<TextView>(R.id.date)

        fun bind(product: Notification) {
            val price = "Rp. ${product.bidPrice}"
            val bp = "Rp. ${product.product.basePrice}"
            history_name.text = product.productName
            history_category.text = bp
            history_price.text = price
            history_date.text = product.transactionDate
            Glide.with(itemView)
                .load(product.imageUrl)
                .into(history_image)

        }
    }

    fun updateList(it: List<Notification>) {
        notification.clear()
        notification.addAll(it)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.notification_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = notification.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(notification[position])

}