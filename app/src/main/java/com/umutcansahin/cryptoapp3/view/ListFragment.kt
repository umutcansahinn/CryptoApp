package com.umutcansahin.cryptoapp3.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.umutcansahin.cryptoapp3.R
import com.umutcansahin.cryptoapp3.adapter.ListAdapter
import com.umutcansahin.cryptoapp3.databinding.FragmentListBinding
import com.umutcansahin.cryptoapp3.viewmodel.ListViewModel


class ListFragment : Fragment() {

    lateinit var binding : FragmentListBinding

    private lateinit var viewModel: ListViewModel
    private val listAdapter = ListAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        viewModel.getData()

        binding.listRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.listRecyclerView.adapter = listAdapter

        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.cryptos.observe(viewLifecycleOwner, Observer {
            binding.listRecyclerView.visibility = View.VISIBLE
            listAdapter.updateListAdapter(it)
        })

        viewModel.cryptoError.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                binding.listErrorMessage.visibility = View.VISIBLE
            }else {
                binding.listErrorMessage.visibility = View.GONE
            }
        })

        viewModel.cryptoLoading.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                binding.listCryptoLoading.visibility = View.VISIBLE
                binding.listErrorMessage.visibility = View.GONE
                binding.listRecyclerView.visibility = View.GONE
            }else {
                binding.listCryptoLoading.visibility = View.GONE
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}