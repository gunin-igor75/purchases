package com.github.gunin_igor75.task_list.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.github.gunin_igor75.task_list.R
import com.github.gunin_igor75.task_list.databinding.ActivityMainBinding
import com.github.gunin_igor75.task_list.presentation.adapter.PurchaseAdapter
import com.github.gunin_igor75.task_list.presentation.adapter.PurchaseAdapter.Companion.MAX_POOL_SIZE
import com.github.gunin_igor75.task_list.presentation.adapter.PurchaseAdapter.Companion.VIEW_TYPE_DISABLE
import com.github.gunin_igor75.task_list.presentation.adapter.PurchaseAdapter.Companion.VIEW_TYPE_ENABLE
import com.google.android.material.floatingactionbutton.FloatingActionButton
import javax.inject.Inject


class MainActivity : AppCompatActivity(), PurchaseItemFragment.OnFinishedListener {

    @Inject
    lateinit var purchaseAdapter: PurchaseAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (application as PurchaseApp).component
    }

    private lateinit var viewModel: MainViewModel

    private lateinit var binding: ActivityMainBinding

    private lateinit var rvPurchases: RecyclerView

    private lateinit var btAddPurchase: FloatingActionButton

    private var purchaseItemContainer: FragmentContainerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        setupRecyclerView()
        observeViewModel()
        setupFloatingActionButton()

    }

    private fun initView() {
        rvPurchases = binding.rvPurchases
        btAddPurchase = binding.btAddPurchase
        purchaseItemContainer = binding.purchaseItemContainer
    }

    private fun observeViewModel() {
        viewModel.purchases.observe(this) { purchaseAdapter.submitList(it) }
    }

    private fun setupFloatingActionButton() {
        btAddPurchase.setOnClickListener {
            if (isOnePaneMode()) {
                val intent = PurchaseItemActivity.newIntent(this)
                startActivity(intent)
            } else {
                val fragment = PurchaseItemFragment.newInstanceAddItem()
                launchFragmentMode(fragment)
            }
        }
    }

    private fun setupRecyclerView() {
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
            if (isOnePaneMode()) {
                val intent = PurchaseItemActivity.newIntent(this, it.id)
                startActivity(intent)
            } else {
                val fragment = PurchaseItemFragment.newInstanceUpdateItem(it.id)
                launchFragmentMode(fragment)
            }
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

    private fun isOnePaneMode(): Boolean {
        return purchaseItemContainer == null
    }

    private fun launchFragmentMode(fragment: Fragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.purchase_item_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onFinished() {
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
        supportFragmentManager.popBackStack()
    }
}