package com.github.gunin_igor75.task_list.presentation.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.gunin_igor75.task_list.R

class PurchaseItemViewHolder(val view: View):RecyclerView.ViewHolder(view) {
    val tvNamePurchase = view.findViewById<TextView>(R.id.tv_mame_purchase)
    val tvCountPurchase = view.findViewById<TextView>(R.id.tv_count_purchase)
}