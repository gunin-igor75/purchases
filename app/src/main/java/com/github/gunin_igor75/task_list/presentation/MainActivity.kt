package com.github.gunin_igor75.task_list.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.github.gunin_igor75.task_list.databinding.ActivityMainBinding
import com.github.gunin_igor75.task_list.presentation.adapter.PurchaseAdapter
import com.github.gunin_igor75.task_list.presentation.adapter.PurchaseAdapter.Companion.MAX_POOL_SIZE
import com.github.gunin_igor75.task_list.presentation.adapter.PurchaseAdapter.Companion.VIEW_TYPE_DISABLE
import com.github.gunin_igor75.task_list.presentation.adapter.PurchaseAdapter.Companion.VIEW_TYPE_ENABLE
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    private lateinit var purchaseAdapter: PurchaseAdapter

    private lateinit var binding: ActivityMainBinding

    private lateinit var rvPurchases: RecyclerView

    private lateinit var btAddPurchase: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        setupRecyclerView()
        observeViewModel()
        setupFloatingActionButton()
    }

    private fun observeViewModel() {
        viewModel.purchases.observe(this) { purchaseAdapter.submitList(it) }
    }

    private fun setupFloatingActionButton() {
        btAddPurchase.setOnClickListener{
            val intent = PurchaseItemActivity.newIntent(this)
            startActivity(intent)
        }
    }

    private fun initView() {
        rvPurchases = binding.rvPurchases
        btAddPurchase = binding.btAddPurchase
    }
    private fun setupRecyclerView() {
        purchaseAdapter = PurchaseAdapter()
        with(rvPurchases) {
            adapter = purchaseAdapter
            recycledViewPool.setMaxRecycledViews(VIEW_TYPE_ENABLE, MAX_POOL_SIZE)
            recycledViewPool.setMaxRecycledViews(VIEW_TYPE_DISABLE, MAX_POOL_SIZE)
        }
        setupLongClickListener()
        setupClickListener()
        setupSwipeListener(rvPurchases)
    }

    private fun setupLongClickListener() {
        purchaseAdapter.purchaseOnLongClickListener = { viewModel.changeEnablePurchases(it) }
    }

    private fun setupClickListener() {
        purchaseAdapter.purchaseOnclickListener = {
            val intent = PurchaseItemActivity.newIntent(this, it.id)
            startActivity(intent)
        }
    }

    private fun setupSwipeListener(rvPurchases: RecyclerView) {
        val calBack = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val purchase = purchaseAdapter.currentList[position]
                viewModel.deletePurchase(purchase)
            }
        }
        val itemTouchHelper = ItemTouchHelper(calBack)
        itemTouchHelper.attachToRecyclerView(rvPurchases)
    }
}