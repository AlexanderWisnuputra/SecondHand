package com.example.secondhand.sellerProduct
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.secondhand.R
import com.example.secondhand.entity.SellerProductItem


class Adapters(private val products: MutableList<SellerProductItem>, private val mainInterface:ProductInterface)
    : RecyclerView.Adapter<Adapters.ViewHolder>()/*, Filterable */{
    var allDataList : List<SellerProductItem> = listOf()
    var dataList : List<SellerProductItem> = listOf()


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var product_image = itemView.findViewById<ImageView>(R.id.seller_product_image)
        var product_price = itemView.findViewById<TextView>(R.id.product_price)
        var product_name = itemView.findViewById<TextView>(R.id.product_name)
        var product_category = itemView.findViewById<TextView>(R.id.product_category)

        fun bind(product: SellerProductItem) {
            val price = "Rp ${product.basePrice}"
            product_name.text = product.name
            product_category.text = product.categories[0].name
            product_price.text = price

            Glide.with(itemView)
                .load(product.imageUrl)
                .into(product_image)
            itemView.setOnClickListener {
//                mainInterface.click(product)
                val mBundle = Bundle()
                mBundle.putString("name_product", product.name)
                mBundle.putString("category_product", product.categories[0].name)
                mBundle.putString("poster", product.imageUrl)
                mBundle.putString("description_product", product.description)
                mBundle.putString("price_product", "Price ${product.basePrice}")
                it.findNavController().navigate(R.id.action_home_to_buyer_Product_Add, mBundle)
            }

        }

    }
    fun updateList(it: List<SellerProductItem>){
        products.clear()
        products.addAll(it)
        notifyDataSetChanged()
    }
   /* fun setData(dataList: List<SellerProductItem>) {
        //set all list to allDataList
        this.allDataList = dataList
        products.clear()
        products.addAll(dataList)
        //Show initial all list
        showListByCatagory("all")
        notifyDataSetChanged()

    }*/


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_sold, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = products.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(products[position])

}