package com.github.gunin_igor75.task_list.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.github.gunin_igor75.task_list.domain.pojo.Purchase

class PurchaseItemDiffCallback: DiffUtil.ItemCallback<Purchase>() {
    override fun areItemsTheSame(oldItem: Purchase, newItem: Purchase): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Purchase, newItem: Purchase): Boolean {
        return oldItem == newItem
    }
}