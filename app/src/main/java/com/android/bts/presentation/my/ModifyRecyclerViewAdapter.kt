package com.android.bts.presentation.my

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.bts.databinding.RecyclerviewCheckboxBinding

class ModifyRecyclerViewAdapter() :
    RecyclerView.Adapter<ModifyRecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: RecyclerviewCheckboxBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    val regionItems: Array<Region> = Region.entries.toTypedArray()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            RecyclerviewCheckboxBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return regionItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            cbRegion11.text = regionItems[position].regionName
        }
    }
}

