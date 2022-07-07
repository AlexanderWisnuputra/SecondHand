package com.example.secondhand.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.secondhand.Helper
import com.example.secondhand.databinding.FragmentNotificationBinding
import com.example.secondhand.entity.Notification
import com.example.secondhand.notification.NotificationAdapter
import com.example.secondhand.notification.NotificationVM
import com.example.secondhand.notification.notificationState

class Notification : Fragment() {
    private lateinit var binding: FragmentNotificationBinding
    private lateinit var notifVM: NotificationVM
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
        setupViewModel()
        getdata()
        observe()
    }


    private fun setupRecyclerView() {
        binding.rvData.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = NotificationAdapter(mutableListOf())
        }
    }

    private fun setupViewModel() {
        notifVM = ViewModelProvider(this).get(NotificationVM::class.java)
    }
    private fun getdata() {
        var x = sharedPref.getAT("AT")
        notifVM.getnotification(x)
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

}