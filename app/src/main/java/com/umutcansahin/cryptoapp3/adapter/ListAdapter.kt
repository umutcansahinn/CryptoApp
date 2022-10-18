package com.umutcansahin.cryptoapp3.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.umutcansahin.cryptoapp3.R
import com.umutcansahin.cryptoapp3.databinding.ListRecyclerRowBinding
import com.umutcansahin.cryptoapp3.model.Crypto
import com.umutcansahin.cryptoapp3.view.ListFragmentDirections
import kotlinx.android.synthetic.main.list_recycler_row.view.*

class ListAdapter(val list: ArrayList<Crypto>): RecyclerView.Adapter<ListAdapter.ListViewHolder>(),CryptoClickListener {

    class ListViewHolder(var view: ListRecyclerRowBinding): RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ListRecyclerRowBinding>(inflater, R.layout.list_recycler_row,parent,false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.view.crypto = list[position]
        holder.view.listener = this
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateListAdapter(newList: List<Crypto>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCryptoClicked(v: View) {
        val id = v.cryptoIdList.text.toString().toInt()
        val action = ListFragmentDirections.actionListFragmentToDetailFragment(id)
        Navigation.findNavController(v).navigate(action)
    }

}