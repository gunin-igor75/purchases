package com.github.gunin_igor75.task_list.presentation.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import com.github.gunin_igor75.task_list.R
import com.github.gunin_igor75.task_list.databinding.PurchaseItemEnableBinding
import com.github.gunin_igor75.task_list.databinding.PurchesItemDisableBinding
import com.github.gunin_igor75.task_list.domain.pojo.Purchase
import javax.inject.Inject

class PurchaseAdapter @Inject constructor() :
    ListAdapter<Purchase, PurchaseItemViewHolder>(PurchaseItemDiffCallback()) {

    var purchaseOnLongClickListener: ((Purchase) -> Unit)? = null

    var purchaseOnclickListener: ((Purchase) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PurchaseItemViewHolder {
        val layoutType = when (viewType) {
            VIEW_TYPE_ENABLE -> R.layout.purchase_item_enable
            VIEW_TYPE_DISABLE -> R.layout.purches_item_disable
            else -> throw RuntimeException("Unknown view type $viewType")
        }
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            layoutType,
            parent, false
        )
        return PurchaseItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PurchaseItemViewHolder, position: Int) {
        val purchase = getItem(position)
        val binding = holder.binding
        binding.root.setOnLongClickListener {
            purchaseOnLongClickListener?.invoke(purchase)
            true
        }
        binding.root.setOnClickListener {
            purchaseOnclickListener?.invoke(purchase)
        }

        when (binding) {
            is PurchaseItemEnableBinding -> {
                binding.purchase = purchase
            }

            is PurchesItemDisableBinding -> {
                binding.purchase = purchase
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val purchase = getItem(position)
        return if (purchase.enabled) VIEW_TYPE_ENABLE else VIEW_TYPE_DISABLE
    }

    companion object {
        const val VIEW_TYPE_ENABLE = 100
        const val VIEW_TYPE_DISABLE = 111
        const val MAX_POOL_SIZE = 30
    }
}