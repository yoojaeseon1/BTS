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
            cbRegion11.text = regionItems[position].toString()
        }
    }
}

enum class Region(regionName: String) {
    서울("서울"),
    Gyeonggi("경기"),
    Gangwon("강원"),
    Incheon("인천"),
    JeJu("제주"),
    Gyeongnam("경남"),
    Gyeongbuk("경북"),
    Jeonnam("전남"),
    Jeonbuk("전북"),
    Chungnam("충남"),
    chungbuk("충북");

}