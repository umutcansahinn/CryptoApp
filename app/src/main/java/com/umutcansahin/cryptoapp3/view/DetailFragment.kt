package com.umutcansahin.cryptoapp3.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.umutcansahin.cryptoapp3.R
import com.umutcansahin.cryptoapp3.databinding.FragmentDetailBinding
import com.umutcansahin.cryptoapp3.viewmodel.DetailViewModel

class DetailFragment : Fragment() {

    lateinit var binding : FragmentDetailBinding
    private lateinit var viewModel: DetailViewModel
    var cryptoId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            cryptoId = DetailFragmentArgs.fromBundle(it).cryptoId
        }
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        viewModel.getCrypto(cryptoId)

        observeLiveData()

    }


    private fun observeLiveData() {
        viewModel.crypto.observe(viewLifecycleOwner, Observer {
        binding.detailCrypto = it

        /*
            binding.detailCryptoName.text = it.currency
            binding.detailCryptoPrice.text = it.price
            */
        })
    }


}