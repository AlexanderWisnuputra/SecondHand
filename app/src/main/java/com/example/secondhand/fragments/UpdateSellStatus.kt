package com.example.secondhand.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.secondhand.Helper
import com.example.secondhand.R
import com.example.secondhand.api.ServiceBuilder
import com.example.secondhand.databinding.FragmentUpdateSellStatusBinding
import com.example.secondhand.databinding.NegotiatePriceBinding
import com.example.secondhand.entity.Bid
import com.example.secondhand.order.SOrderVM
import com.example.secondhand.repository.SOrderRepo
import com.example.secondhand.wishlist.WishlistVM
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateSellStatus: BottomSheetDialogFragment() {
    private lateinit var sharedPref: Helper
    private val vmod: SOrderVM by viewModel()
    private val vmod2: WishlistVM by viewModel()

    private lateinit var binding: FragmentUpdateSellStatusBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val updateStatus = FragmentUpdateSellStatusBinding.inflate(inflater, container, false)
        binding = updateStatus
        return updateStatus.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref = Helper(requireContext())
        val mBundle = Bundle()
        var ids = arguments?.getInt("id")
        var id = arguments?.getInt("ids")

        var x = sharedPref.getAT("AT")
        binding.button.setOnClickListener {
            if(binding.radioButton.isChecked){
                vmod2.patchStatus(x,id!!,"accepted")
                deleteProduct(ids!!)
                vmod.patch(x, ids!!, "sold")
                findNavController().popBackStack()
                vmod.patch(x, ids!!, "sold")

            }
            else if (binding.radioButton2.isChecked){
                vmod2.patchStatus(x,id!!,"decline")
                vmod.patch(x, ids!!, "available")
                findNavController().popBackStack()
                vmod.patch(x, ids!!, "available")
            }
            else{
                Toast.makeText(context,"Gagal Update", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
            }
        }
    fun deleteProduct(id: Int) {
        val api = ServiceBuilder.instance()
        var x = sharedPref.getAT("AT")
        api.deleteProduct(x, id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
            }
            override fun onFailure(call: Call<Void>, t: Throwable) {
                println(t.message)
            }
        })

    }
    }







