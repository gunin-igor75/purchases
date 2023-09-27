package com.github.gunin_igor75.task_list.presentation.adapter


import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.github.gunin_igor75.task_list.domain.pojo.Purchase

class PurchaseAdapter: ListAdapter<Purchase, PurchaseItemViewHolder>(PurchaseItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PurchaseItemViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: PurchaseItemViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemViewType(position: Int): Int {
        TODO("Not yet implemented")
    }
}