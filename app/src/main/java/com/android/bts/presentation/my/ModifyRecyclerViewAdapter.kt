package com.android.bts.presentation.my

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.android.bts.databinding.RecyclerviewCheckboxBinding
import com.android.bts.presentation.search.ItemsEntity

class ModifyRecyclerViewAdapter(private val viewModel: MyVideoViewModel) :
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
        // 체크박스 상태 가져옴
        holder.binding.cbRegion11.setOnCheckedChangeListener { _, isChecked ->
            viewModel.updateChecked(regionItems[position].regionName, isChecked)
        }
    }
}
