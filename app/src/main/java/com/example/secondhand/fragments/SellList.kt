package com.example.secondhand.fragments

import android.app.Activity
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.secondhand.Helper
import com.example.secondhand.R
import com.example.secondhand.api.ServiceBuilder
import com.example.secondhand.databinding.FragmentListBinding
import com.example.secondhand.entity.*
import com.example.secondhand.history.HistoryAdapter
import com.example.secondhand.history.HistoryState
import com.example.secondhand.history.HistoryVM
import com.example.secondhand.history.HistoryInterface
import com.example.secondhand.order.SOrderAdapter
import com.example.secondhand.order.SOrderInterface
import com.example.secondhand.order.SOrderVM
import com.example.secondhand.order.SorderState
import com.example.secondhand.wishlist.WishlistAdapter
import com.example.secondhand.wishlist.WishlistInterface
import com.example.secondhand.wishlist.WishlistVM
import com.example.secondhand.wishlist.bidStatus
import org.koin.androidx.viewmodel.ext.android.viewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SellList : Fragment(), HistoryInterface, SOrderInterface,WishlistInterface {
    private lateinit var binding: FragmentListBinding
    private lateinit var sharedPref: Helper
    private val SellVM: HistoryVM by viewModel()
    private val SOVM: SOrderVM by viewModel()
    private val vmod: WishlistVM by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val listBinding = FragmentListBinding.inflate(inflater, container, false)
        binding = listBinding
        return listBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref = Helper(requireContext())
        setupRecyclerView2()
        Sorder()
        observe2()
        getUserDetail()
        binding.orderrecyclerview.visibility = View.VISIBLE

        binding.button4.setOnClickListener {
            sharedPref.putAT("status","1")
            findNavController().navigate(R.id.action_list_to_changeAcc)
        }
        binding.button2.setOnClickListener {
            getwish()
            observe3()
            setupRecyclerView3()

            binding.orderrecyclerview.visibility = View.INVISIBLE
            binding.historyrecyclerview.visibility = View.INVISIBLE
            binding.wishlist.visibility = View.VISIBLE

        }
        binding.button3.setOnClickListener {
            setupRecyclerView()
            getdata()
            observe()
            binding.orderrecyclerview.visibility = View.INVISIBLE
            binding.historyrecyclerview.visibility = View.VISIBLE
            binding.wishlist.visibility = View.INVISIBLE

        }
        binding.listallproduct.setOnClickListener {
            setupRecyclerView2()
            Sorder()
            observe2()
            binding.orderrecyclerview.visibility = View.VISIBLE
            binding.historyrecyclerview.visibility = View.INVISIBLE
            binding.wishlist.visibility = View.INVISIBLE
        }
    }

    private fun Toast.showCustomToast(message: String, activity: Activity) {
        val layout = activity.layoutInflater.inflate (
            R.layout.toast_layout,
            activity.findViewById(R.id.toastCustom)
        )

        // set the text of the TextView of the message
        val textView = layout.findViewById<TextView>(R.id.toastText)
        textView.text = message

        // use the application extension function
        this.apply {
            setGravity(Gravity.TOP, 0, 250)
            duration = Toast.LENGTH_LONG
            view = layout
            show()
        }
    }

    private fun setupRecyclerView() {
        binding.historyrecyclerview.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = HistoryAdapter(mutableListOf(), this@SellList)
        }
    }
    private fun setupRecyclerView2() {
        binding.orderrecyclerview.apply {
                layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = SOrderAdapter(mutableListOf(), this@SellList)
        }
    }
    private fun setupRecyclerView3() {
        binding.wishlist.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = WishlistAdapter(mutableListOf(), this@SellList)
        }
    }
    private fun getwish() {
        var x = sharedPref.getAT("AT")
        vmod.getordered(x, "pending")    }



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
        binding.wishlist.adapter?.let { a ->
            if (a is WishlistAdapter) {
                a.updateList(sp)
            }
        }
    }

    private fun getdata() {
        var x = sharedPref.getAT("AT")
        SellVM.getHistory(x)
    }
    private fun Sorder() {
        var x = sharedPref.getAT("AT")
        SOVM.getsoldProduct(x)
    }

    private fun observe2() {
        observeState2()
        observeProduct2()
    }

    private fun observeState2() = SOVM.getState().observe(viewLifecycleOwner, Observer { handlestate2(it) })

    private fun observeProduct2() = SOVM.getOrder().observe(viewLifecycleOwner, Observer { handleproduct2(it) })

    private fun handlestate2(it: SorderState) {
        when (it) {
            is SorderState.Loading -> isLoading2(it.isLoading)
        }
    }

    private fun isLoading2(b: Boolean) {
        if (b) {
            binding.progressBar.visibility = View.VISIBLE
            binding.imageView2.visibility = View.VISIBLE

        } else {
            binding.progressBar.visibility = View.GONE
            binding.imageView2.visibility = View.GONE
        }
    }

    private fun handleproduct2(sp: List<ProductResponse>) {
        binding.orderrecyclerview.adapter?.let { a ->
            if (a is SOrderAdapter) {
                a.updateList(sp)
            }
        }
    }

    private fun observe() {
        observeState()
        observeProduct()
    }

    private fun observeState() = SellVM.getState().observe(viewLifecycleOwner, Observer { handlestate(it) })

    private fun observeProduct() = SellVM.getHistory().observe(viewLifecycleOwner, Observer { handleproduct(it) })

    private fun handlestate(it: HistoryState) {
        when (it) {
            is HistoryState.Loading -> isLoading(it.isLoading)
        }
    }

    private fun isLoading(b: Boolean) {
        if (b) {
            binding.progressBar.visibility = View.VISIBLE
            binding.imageView2.visibility = View.VISIBLE

        } else {
            binding.progressBar.visibility = View.GONE
            binding.imageView2.visibility = View.GONE
        }
    }

    private fun handleproduct(sp: List<History>) {
        binding.historyrecyclerview.adapter?.let { a ->
            if (a is HistoryAdapter) {
                a.updateList(sp)
            }
        }
    }

    override fun click(item: History) {
        var x = sharedPref.getAT("AT")
        var s = item.id
                historydetail(x,s)

    }
    private fun getUserDetail() {
        var x = sharedPref.getAT("AT")
        ServiceBuilder.instance().getUser(x).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    binding.textView5.text = response.body()?.fullName.toString()
                    binding.textView6.text = response.body()?.address.toString()

                    Glide.with(requireContext())
                        .load(response.body()?.imageUrl.toString())
                        .into(binding.imgPoster)

                } else Toast.makeText(context, response.errorBody()!!.string(), Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                println(t.message)
            }
        })
    }

    override fun click(item: ProductResponse) {
        var x = item.id
        var s = sharedPref.getAT("AT")
        productListbyid(s,x!!)
    }

fun productListbyid(accesstoken: String?, id: Int) {
    val api = ServiceBuilder.instance()
    api.getproductsoldbyID(accesstoken, id).enqueue(object : Callback<ProductResponse> {
        override fun onResponse(call: Call<ProductResponse>, response: Response<ProductResponse>) {
            if (response.isSuccessful) {
                val value = response.body()
                value.let {
                    val mBundle = Bundle()
                    val ids = value?.id
                    val name = value?.name
                    val desc =  value?.description
                    val price = value?.basePrice.toString()
                    val poster = value?.image
                    val category = value?.categories?.firstOrNull()?.name
                    mBundle.putInt("ids",ids!!)
                    mBundle.putString("name_product", name)
                    mBundle.putString("poster", poster)
                    mBundle.putString("status", desc)
                    mBundle.putString("price_product", "Price ${price}")
                    mBundle.putString("category_product",category)
                    findNavController().navigate(R.id.action_list_to_orderListDetail, mBundle)
                }
            }
        }

        override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
            println(t.message)

        }
    })
}

    fun historydetail(accesstoken: String?, id: Int) {
        val api = ServiceBuilder.instance()
        api.getHistorybyID(accesstoken, id).enqueue(object : Callback<History> {
            override fun onResponse(call: Call<History>, response: Response<History>) {
                if (response.isSuccessful) {
                    val value = response.body()
                    value.let {
                        val mBundle = Bundle()
                        val name = value?.productName
                        val price = value?.price
                        val poster = value?.imageUrl
                        val status = value?.status
                        mBundle.putString("name_product",name)
                        mBundle.putString("poster", poster)
                        mBundle.putString("status", status)
                        mBundle.putString("price_product", "Rp ${price}")
                        findNavController().navigate(R.id.action_list_to_historyDetail, mBundle)

                    }
                }
            }
            override fun onFailure(call: Call<History>, t: Throwable) {
                println(t.message)

            }
        })
    }

    override fun click(item: BidStatus) {
        findNavController().navigate(R.id.action_list_to_buyerInfo)
    }

}
