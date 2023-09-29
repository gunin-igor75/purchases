package com.github.gunin_igor75.task_list.presentation.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.github.gunin_igor75.task_list.R
import com.github.gunin_igor75.task_list.domain.pojo.Purchase
import java.lang.RuntimeException

class PurchaseAdapter: ListAdapter<Purchase, PurchaseItemViewHolder>(PurchaseItemDiffCallback()) {

    var purchaseOnLongClickListener: ((Purchase) -> Unit)? = null

    var purchaseOnclickListener: ((Purchase) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PurchaseItemViewHolder {
        val layoutType = when (viewType) {
            VIEW_TYPE_ENABLE -> R.layout.purchase_item_enable
            VIEW_TYPE_DISABLE -> R.layout.purches_item_disable
            else -> throw RuntimeException("Unknown view type $viewType")
        }
        val view = LayoutInflater.from(parent.context).inflate(layoutType, parent, false)
        return PurchaseItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: PurchaseItemViewHolder, position: Int) {
        val purchase = getItem(position)

        holder.view.setOnLongClickListener{
            purchaseOnLongClickListener?.invoke(purchase)
            true
        }
        holder.view.setOnClickListener {
            purchaseOnclickListener?.invoke(purchase)
        }
        holder.tvNamePurchase.text = purchase.name
        holder.tvCountPurchase.text = purchase.count.toString()
    }

    override fun getItemViewType(position: Int): Int {
        val purchase = getItem(position)
        return if (purchase.enabled) VIEW_TYPE_ENABLE else VIEW_TYPE_DISABLE
    }

    companion object{
        const val VIEW_TYPE_ENABLE = 100
        const val VIEW_TYPE_DISABLE = 111
        const val MAX_POOL_SIZE = 30
    }
}