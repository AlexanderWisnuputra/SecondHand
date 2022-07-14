package com.example.secondhand.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.secondhand.Helper
import com.example.secondhand.R
import com.example.secondhand.databinding.FragmentNotificationBinding
import com.example.secondhand.entity.Notification
import com.example.secondhand.notification.NotificationAdapter
import com.example.secondhand.notification.NotificationInterface
import com.example.secondhand.notification.NotificationVM
import com.example.secondhand.notification.notificationState
import org.koin.androidx.viewmodel.ext.android.viewModel

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
    private fun getdatabyID(id:Int) {
        var x = sharedPref.getAT("AT")
        notifVM.getByID(x,id)
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
    }

    override fun click(item: Notification) {
        var x = item.id
        getdatabyID(x)
        val mBundle = Bundle()
        mBundle.putString("name_product", item.productName)
        mBundle.putString("category_product", item.bidPrice.toString())
        mBundle.putString("poster", item.imageUrl)
        mBundle.putString("status", item.status)
        mBundle.putString("price_product", "Price ${item.product.basePrice}")
        findNavController().navigate(R.id.action_notification_to_notificationDetail, mBundle)
    }

}