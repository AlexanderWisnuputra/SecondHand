package com.example.secondhand.order

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.secondhand.R
import com.example.secondhand.entity.Product
import com.example.secondhand.entity.ProductResponse

// WORK IN PROGRESS CONTINUE
class SOrderAdapter(private val order: MutableList<ProductResponse>,private val maininterface: SOrderInterface)
    : RecyclerView.Adapter<SOrderAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var Image = itemView.findViewById<ImageView>(R.id.sorder_image)
        var history_price = itemView.findViewById<TextView>(R.id.sorder_price)
        var history_name = itemView.findViewById<TextView>(R.id.sorder_name)
        var history_category = itemView.findViewById<TextView>(R.id.sorder_category)

        fun bind(product: ProductResponse) {

            history_name.text = product.name
            history_category.text = product.categories.firstOrNull()?.name
            history_price.text = product.basePrice.toString()
            Glide.with(itemView)
                .load(product.image)
                .into(Image)
            itemView.setOnClickListener {
                maininterface.click(product)
            }
        }
    }

    fun updateList(it: List<ProductResponse>) {
        order.clear()
        order.addAll(it)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.sorder_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = order.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(order[position])

}