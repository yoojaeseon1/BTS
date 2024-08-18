package com.android.bts.presentation.my

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.android.bts.databinding.RecyclerviewCheckboxBinding
import com.android.bts.presentation.search.ItemsEntity

class ModifyRecyclerViewAdapter(private val viewModel: MyVideoViewModel) :
    RecyclerView.Adapter<ModifyRecyclerViewAdapter.ViewHolder>() {

    val regionItems: Array<Region> = Region.entries.toTypedArray()
    private val selectedRegionItems = mutableListOf<String>()

    inner class ViewHolder(val binding: RecyclerviewCheckboxBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(text: String) {
            binding.cbRegion11.text = text
            binding.cbRegion11.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    selectedRegionItems.add(text)
                } else {
                    selectedRegionItems.remove(text)
                }
                Log.d("TAG", "bind: ${selectedRegionItems.size}")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            RecyclerviewCheckboxBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return regionItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(regionItems[position].regionName)
//        holder.binding.apply {
//            cbRegion11.text = regionItems[position].regionName
//        }

//        holder.binding.cbRegion11.isChecked

    }

    fun getSelectedRegionItems(): List<String> = selectedRegionItems
}