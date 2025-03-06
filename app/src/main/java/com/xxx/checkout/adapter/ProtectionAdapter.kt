package com.xxx.checkout.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.xxx.checkout.base.BaseAdapter
import com.xxx.checkout.databinding.ItemProtectionBinding
import com.xxx.checkout.model.Protection

class ProtectionAdapter :
    BaseAdapter<Protection, ProtectionAdapter.ViewHolder>(Protection.entries) {
    override fun doCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemProtectionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position) ?: return
        with(holder.binding) {
            image.setImageResource(item.iconRes)
            tv.setText(item.textRes)
        }
    }

    class ViewHolder(val binding: ItemProtectionBinding) : RecyclerView.ViewHolder(binding.root)
}