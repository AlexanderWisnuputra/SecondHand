package com.example.secondhand.sellerProduct
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.secondhand.R
import com.example.secondhand.entity.SellerProductItem

class Adapters(private val products: MutableList<SellerProductItem>, private val mainInterface:ProductInterface)
    : RecyclerView.Adapter<Adapters.ViewHolder>(){

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var product_image = itemView.findViewById<ImageView>(R.id.history_image)
        var product_price = itemView.findViewById<TextView>(R.id.history_price)
        var product_name = itemView.findViewById<TextView>(R.id.history_name)
        var product_category = itemView.findViewById<TextView>(R.id.history_category)

        fun bind(product: SellerProductItem) {
            val price = "Rp ${product.basePrice}"
            product_name.text = product.name
            product_category.text = product.categories[0].name
            product_price.text = price

            Glide.with(itemView)
                .load(product.imageUrl)
                .into(product_image)
            itemView.setOnClickListener {
                mainInterface.click(product)

            }

        }

    }

    fun updateList(it: List<SellerProductItem>){
        products.clear()
        products.addAll(it)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_sold, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = products.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(products[position])

}