package com.example.secondhand.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.secondhand.Helper
import com.example.secondhand.R
import com.example.secondhand.databinding.FragmentListBinding
import com.example.secondhand.entity.History
import com.example.secondhand.history.HistoryAdapter
import com.example.secondhand.history.HistoryState
import com.example.secondhand.history.HistoryVM
import com.example.secondhand.history.HistoryInterface


class SellList : Fragment(), HistoryInterface {
    private lateinit var binding: FragmentListBinding
    private lateinit var sharedPref: Helper
    private lateinit var SellVM: HistoryVM


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
        setupRecyclerView()
        setupViewModel()
        getdata()
        observe()
    }

    private fun setupRecyclerView() {
        binding.notifRecyclerview.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = HistoryAdapter(mutableListOf(), this@SellList)
        }
    }

    private fun setupViewModel() {
        SellVM = ViewModelProvider(this).get(HistoryVM::class.java)
    }

    private fun getdata() {
        var x = sharedPref.getAT("AT")
        SellVM.getHistory(x)
    }
    private fun getdatabyID(id:Int) {
        var x = sharedPref.getAT("AT")
        SellVM.getByID(x,id)
    }


    private fun observe() {
        observeState()
        observeProduct()
    }

    private fun observeState() =
        SellVM.getState().observe(viewLifecycleOwner, Observer { handlestate(it) })

    private fun observeProduct() =
        SellVM.getHistory().observe(viewLifecycleOwner, Observer { handleproduct(it) })

    private fun handlestate(it: HistoryState) {
        when (it) {
            is HistoryState.Loading -> isLoading(it.isLoading)
        }
    }

    private fun isLoading(b: Boolean) {
        if (b) {
            binding.imageView2.visibility = View.VISIBLE
        } else {
            binding.imageView2.visibility = View.GONE
        }
    }

    private fun handleproduct(sp: List<History>) {
        binding.notifRecyclerview.adapter?.let { a ->
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
}
