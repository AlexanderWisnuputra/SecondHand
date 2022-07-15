package com.example.secondhand.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.secondhand.Helper
import com.example.secondhand.R
import com.example.secondhand.api.ServiceBuilder
import com.example.secondhand.databinding.FragmentListBinding
import com.example.secondhand.entity.History
import com.example.secondhand.entity.Product
import com.example.secondhand.entity.User
import com.example.secondhand.entity.Wishlist
import com.example.secondhand.history.HistoryAdapter
import com.example.secondhand.history.HistoryState
import com.example.secondhand.history.HistoryVM
import com.example.secondhand.history.HistoryInterface
import com.example.secondhand.order.SOrderAdapter
import com.example.secondhand.order.SOrderInterface
import com.example.secondhand.order.SOrderVM
import com.example.secondhand.order.SorderState
import com.example.secondhand.wishlist.WishlistVM
import com.example.secondhand.wishlist.wishState
import com.example.secondhand.wishlist.WishlistAdapter
import com.example.secondhand.wishlist.WishlistInterface
import org.koin.androidx.viewmodel.ext.android.viewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SellList : Fragment(), HistoryInterface, SOrderInterface, WishlistInterface {
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

        binding.button4.setOnClickListener {
            findNavController().navigate(R.id.action_list_to_changeAcc)
        }
        binding.button2.setOnClickListener {
            setupRecyclerView3()
            getwish()
            observe3()
            binding.orderrecyclerview.visibility = View.INVISIBLE
            binding.wishlist.visibility = View.VISIBLE
            binding.historyrecyclerview.visibility = View.INVISIBLE
        }
        binding.button3.setOnClickListener {
            setupRecyclerView()
            getUserDetail()
            getdata()
            observe()
            binding.orderrecyclerview.visibility = View.INVISIBLE
            binding.wishlist.visibility = View.INVISIBLE
            binding.historyrecyclerview.visibility = View.VISIBLE

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

    private fun setupRecyclerView() {
        binding.historyrecyclerview.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = HistoryAdapter(mutableListOf(), this@SellList)
        }
    }
    private fun setupRecyclerView2() {
        binding.orderrecyclerview.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
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

    private fun getdata() {
        var x = sharedPref.getAT("AT")
        SellVM.getHistory(x)
    }
    private fun Sorder() {
        var x = sharedPref.getAT("AT")
        SOVM.getsoldProduct(x)
    }
    private fun Sorderid(id: Int) {
        var x = sharedPref.getAT("AT")
        SOVM.getsoldProductid(x,id)    }

    private fun getwish() {
        var x = sharedPref.getAT("AT")
        vmod.getWish(x)    }


    private fun getdatabyID(id:Int) {
        var x = sharedPref.getAT("AT")
        SellVM.getByID(x,id)
    }


    private fun observe3() {
        observeState3()
        observeProduct3()
    }

    private fun observeState3() = vmod.getState().observe(viewLifecycleOwner, Observer { handlestate3(it) })

    private fun observeProduct3() = vmod.getwish().observe(viewLifecycleOwner, Observer { handleproduct3(it) })

    private fun handlestate3(it: wishState) {
        when (it) {
            is wishState.Loading -> isLoading3(it.isLoading)
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

    private fun handleproduct3(sp: List<Wishlist>) {
        binding.wishlist.adapter?.let { a ->
            if (a is WishlistAdapter) {
                a.updateList(sp)
            }
        }
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

    private fun handleproduct2(sp: List<Product>) {
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
        var x = item.id
        getdatabyID(x)
        val mBundle = Bundle()
        mBundle.putString("name_product", item.productName)
        mBundle.putString("category_product", item.category)
        mBundle.putString("poster", item.imageUrl)
        mBundle.putString("status", item.status)
        mBundle.putString("price_product", "Price ${item.price}")
        findNavController().navigate(R.id.action_list_to_historyDetail, mBundle)
    }
    private fun getUserDetail() {
        var x = sharedPref.getAT("AT")
        ServiceBuilder.instance().getUser(x).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    Glide.with(requireContext())
                        .load(response.body()!!.imageUrl)
                        .into(binding.imgPoster)

                } else Toast.makeText(context, response.errorBody()!!.string(), Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                println(t.message)
            }
        })
    }

    override fun click(item: Product) {
        var x = item.id
        Sorderid(x!!)
        val mBundle = Bundle()
        mBundle.putString("name_product", item.name)
        mBundle.putString("category_product", item.name)
        mBundle.putString("poster", item.image)
        mBundle.putString("status", item.description)
        mBundle.putString("price_product", "Price ${item.basePrice}")
        findNavController().navigate(R.id.action_list_to_orderListDetail, mBundle)
    }

    override fun click(item: Wishlist) {
        TODO("Not yet implemented")
    }
}
