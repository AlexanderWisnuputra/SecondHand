package com.example.secondhand.sellerProduct
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.secondhand.R


class Adapter(private val sellerProductList: List<SellerProductItem>) :RecyclerView.Adapter<Adapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view  = LayoutInflater.from(parent.context).inflate(R.layout.product_sold,parent,false)
        return ViewHolder(view)
    }


    override fun getItemCount(): Int {

        return sellerProductList.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("Response", "List Count :${sellerProductList.size} ")


/*
        return holder.bind(sellerProductList[position])
*/

    }
    class ViewHolder(itemView : View) :RecyclerView.ViewHolder(itemView) {


       /* var imageView = itemView.findViewById<ImageView>(R.id.ivFlag)
        var tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        var tvCases = itemView.findViewById<TextView>(R.id.tvCases)
        fun bind(product: SellerProductItem) {

            val name ="Rp ${product.basePrice}"
            tvTitle.text = product.country
            tvCases.text = name
            Picasso.get().load(product.countryInfo.flag).into(imageView)*/
        }

    }
