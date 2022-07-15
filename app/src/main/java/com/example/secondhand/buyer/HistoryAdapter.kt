/*
package com.example.secondhand.buyer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.secondhand.R
import com.example.secondhand.entity.History

class HistoryAdapter(private val sejarah: MutableList<History>, private val mainInterface: HistoryInterface)
    : RecyclerView.Adapter<HistoryAdapter.ViewHolder>(){

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var history_image = itemView.findViewById<ImageView>(R.id.sorder_image)
        var history_price = itemView.findViewById<TextView>(R.id.sorder_price)
        var history_name = itemView.findViewById<TextView>(R.id.sorder_name)
        var history_category = itemView.findViewById<TextView>(R.id.sorder_category)
        var history_status = itemView.findViewById<TextView>(R.id.history_status)
        var history_date = itemView.findViewById<TextView>(R.id.date)

        fun bind(product: History) {
            val price = "Rp ${product.price}"
            history_name.text = product.productName
            history_category.text = product.category
            history_price.text = price
            history_status.text = product.status
            history_date.text = product.transactionDate
            Glide.with(itemView)
                .load(product.imageUrl)
                .into(history_image)
            itemView.setOnClickListener {
                mainInterface.click(product)
            }
        }
    }
    fun updateList(it: List<History>){
        sejarah.clear()
        sejarah.addAll(it)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.history_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = sejarah.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(sejarah[position])

}*/
