package com.example.secondhand.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.secondhand.Helper
import com.example.secondhand.R
import com.example.secondhand.api.ServiceBuilder
import com.example.secondhand.databinding.FragmentBuyerInfoBinding
import com.example.secondhand.databinding.FragmentListBinding
import com.example.secondhand.entity.BidStatus
import com.example.secondhand.entity.User
import com.example.secondhand.history.HistoryInterface
import com.example.secondhand.history.HistoryVM
import com.example.secondhand.order.SOrderInterface
import com.example.secondhand.order.SOrderVM
import com.example.secondhand.wishlist.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BuyerInfo : Fragment(),WishlistInterface {
    private lateinit var sharedPref: Helper
    private lateinit var binding: FragmentBuyerInfoBinding
    private val vmod: WishlistVM by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fbinding = FragmentBuyerInfoBinding.inflate(inflater, container, false)
        binding = fbinding
        return fbinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref = Helper(requireContext())
        setupRecyclerView3()
        getwish()
        observe3()
        getUserDetail()
        binding.imageView4.setOnClickListener {
            findNavController().popBackStack()
        }
    }


    private fun getUserDetail() {
        var x = sharedPref.getAT("AT")
        ServiceBuilder.instance().getUser(x).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    binding.namaBuyer.text = response.body()?.fullName.toString()
                    binding.kotaPembeli.text = response.body()?.address.toString()

                    Glide.with(requireContext())
                        .load(response.body()?.imageUrl.toString())
                        .into(binding.profilBuyer)

                } else Toast.makeText(context, response.errorBody()!!.string(), Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                println(t.message)
            }
        })
    }

    private fun getwish() {
        var x = sharedPref.getAT("AT")
        vmod.getordered(x, "pending")    }

        private fun setupRecyclerView3() {
            binding.wishlist2.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                adapter = WishlistAdapter2(mutableListOf(), this@BuyerInfo)
            }
        }

        private fun observe3() {
            observeState3()
            observeProduct3()
        }

        private fun observeState3() =
            vmod.getState().observe(viewLifecycleOwner, Observer { handlestate3(it) })

        private fun observeProduct3() =
            vmod.getwish().observe(viewLifecycleOwner, Observer { handleproduct3(it) })

        private fun handlestate3(it: bidStatus) {
            when (it) {
                is bidStatus.Loading -> isLoading3(it.isLoading)
            }
        }

        private fun isLoading3(b: Boolean) {
            if (b) {
                binding.progressBar.visibility = View.VISIBLE
                binding.imageView2.visibility = View.VISIBLE

            } else {
                binding.progressBar.visibility = View.GONE
                binding.imageView2.visibility = View.GONE
            }
        }

        private fun handleproduct3(sp: List<BidStatus>) {
            binding.wishlist2.adapter?.let { a ->
                if (a is WishlistAdapter2) {
                    a.updateList(sp)
                }

            }
        }

    override fun click(item: BidStatus) {
        var id = item.id
        var pid = item.productID
        var x = sharedPref.getAT("AT")
        sharedPref.putSell("LOCK", "$id")
        sharedPref.putSell("SUCCESS","$id")
        sharedPref.putSell("DELETE","$pid")
        bidetail(id)
    }
    fun bidetail(id: Int) {
        val api = ServiceBuilder.instance()
        var x = sharedPref.getAT("AT")
    //// CEK KEGUNAANNYA, ABIS DIPINDAHIN SOALNYA
        api.getorderedbyid(x, id).enqueue(object : Callback<BidStatus> {
            override fun onResponse(call: Call<BidStatus>, response: Response<BidStatus>) {
                if (response.isSuccessful) {
                    val value = response.body()
                    value.let {
                        val name = value?.name
                        val stat = value?.status
                        val poster = value?.img
                        val price2 = value?.priceID
                        val price = value?.normalPrice
                        val x = value?.productID
                        val s = value?.id
                        val mBundle = Bundle()
                        mBundle.putInt("id", x!!)
                        mBundle.putInt("ids", s!!)
                        mBundle.putString("name_product", name)
                        mBundle.putString("stats", stat)
                        mBundle.putString("poster", poster)
                        mBundle.putString("price_product", "Rp. $price")
                        mBundle.putString("price", "Rp. $price2")

                    }
                }
            }

            override fun onFailure(call: Call<BidStatus>, t: Throwable) {
                println(t.message)
            }
        })
    }
}
