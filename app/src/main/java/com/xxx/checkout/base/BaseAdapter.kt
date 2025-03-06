package com.xxx.checkout.base

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T, VH : RecyclerView.ViewHolder>(
    private var items: List<T> = emptyList()
) : RecyclerView.Adapter<VH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return doCreateViewHolder(parent, viewType)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    abstract fun doCreateViewHolder(parent: ViewGroup, viewType: Int): VH

    @SuppressLint("NotifyDataSetChanged")
    fun updateDataset(newItems: List<T>, useDiffUtil: Boolean = true) {
        if (useDiffUtil) {
            val diff = Diff(items, newItems)
            val result = DiffUtil.calculateDiff(diff)
            items = newItems
            result.dispatchUpdatesTo(this)
        } else {
            items = newItems
            notifyDataSetChanged()
        }
    }

    fun getItem(position: Int): T? = items.getOrNull(position)

    fun getItems() = items

    class Diff<T>(private val old: List<T>, val new: List<T>) : DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return old.size
        }

        override fun getNewListSize(): Int {
            return new.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return old[oldItemPosition] == new[newItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return old[oldItemPosition] == new[newItemPosition]
        }
    }
}