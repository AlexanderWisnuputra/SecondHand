package com.example.secondhand.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.secondhand.Helper
import com.example.secondhand.R
import com.example.secondhand.api.ServiceBuilder
import com.example.secondhand.databinding.FragmentNotificationBinding
import com.example.secondhand.entity.Notification
import com.example.secondhand.notification.NotificationAdapter
import com.example.secondhand.notification.NotificationInterface
import com.example.secondhand.notification.NotificationVM
import com.example.secondhand.notification.notificationState
import org.koin.androidx.viewmodel.ext.android.viewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Notification : Fragment(), NotificationInterface {
    private lateinit var binding: FragmentNotificationBinding
    private val notifVM: NotificationVM by viewModel()
    private lateinit var sharedPref: Helper


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val listBinding = FragmentNotificationBinding.inflate(inflater, container, false)
        binding = listBinding
        return listBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref = Helper(requireContext())
        setupRecyclerView()
        getdata()
        observe()

        val notifData = view.findViewById<ImageView>(R.id.redDot)
        val notifstat = sharedPref.getNotif("notif")
        val itemclicked = "${notifstat}"
        if (itemclicked == "asd"){
            notifData?.visibility = View.GONE
        }
    }

    private fun setupRecyclerView() {
        binding.rvData.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = NotificationAdapter(mutableListOf(), this@Notification)
        }
    }

    private fun getdata() {
        var x = sharedPref.getAT("AT")
        notifVM.getnotification(x)
    }
    fun productListbyid(accesstoken: String?, id: Int) {
        val api = ServiceBuilder.instance()
        api.notifbyID(accesstoken, id).enqueue(object : Callback<Notification> {
            override fun onResponse(call: Call<Notification>, response: Response<Notification>) {
                if (response.isSuccessful) {
                    val value = response.body()
                    value.let {
                        val mBundle = Bundle()
                        val name = value?.productName
                        val price = value?.bidPrice
                        val desc = value?.status
                        val poster = value?.imageUrl
                        val category = value?.sellerName
                        val ids = value?.id
                        mBundle.putInt("id", ids!!)
                        mBundle.putString("name_product", name)
                        mBundle.putString("poster", poster)
                        mBundle.putString("status", desc.toString())
                        mBundle.putString("price_product", "Price ${price}")
                        mBundle.putString("category_product", category)
                        findNavController().navigate(R.id.action_notification_to_notificationDetail, mBundle)
                    }
                }
            }

            override fun onFailure(call: Call<Notification>, t: Throwable) {
                println(t.message)
            }
        })
    }
    private fun getdatabuyer(type:String) {
        var x = sharedPref.getAT("AT")
        notifVM.getBytype(x,type)
    }
    private fun getdataseller(type:String) {
        var x = sharedPref.getAT("AT")
        notifVM.getBytype(x,type)
    }
    private fun observe() {
        observeState()
        observeProduct()
    }

    private fun observeState() =
        notifVM.getState().observe(viewLifecycleOwner, Observer { handlestate(it) })

    private fun observeProduct() =
        notifVM.getnotification().observe(viewLifecycleOwner, Observer { handleproduct(it) })

    private fun handlestate(it: notificationState) {
        when (it) {
            is notificationState.Loading -> isLoading(it.isLoading)
        }
    }

    private fun isLoading(b: Boolean) {
        if (b) {
            binding.pbMovie.visibility = View.VISIBLE
        } else {
            binding.pbMovie.visibility = View.GONE
        }
    }

    private fun handleproduct(sp: List<Notification>) {
        binding.rvData.adapter?.let { a ->
            if (a is NotificationAdapter) {
                a.updateList(sp)
            }
        }
        binding.buyerNotif.setOnClickListener {
            binding.rvData.adapter?.let { a ->
                if (a is NotificationAdapter) {
                    getdatabuyer("buyer")
                    a.updateList(sp)
                    a.notifyDataSetChanged()
                }
            }
        }
            binding.all.setOnClickListener {
                binding.rvData.adapter?.let { a ->
                    if (a is NotificationAdapter) {
                        getdata()
                        a.updateList(sp)
                        a.notifyDataSetChanged()
                    }
                }
            }
                binding.sellerNotif.setOnClickListener {
                    binding.rvData.adapter?.let { a ->
                        if (a is NotificationAdapter) {
                           getdataseller("seller")
                            a.updateList(sp)
                            a.notifyDataSetChanged()
                        }
                    }
                }
            }


        override fun click(item: Notification) {
            sharedPref.putNotif("notif", "asd")
            val x = sharedPref.getAT("AT")
            val y = item.id
            productListbyid(x, y)
        }

}


