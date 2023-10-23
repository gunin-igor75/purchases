package com.github.gunin_igor75.task_list.data.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.github.gunin_igor75.task_list.data.db.PurchaseDao
import com.github.gunin_igor75.task_list.data.entity.PurchaseDbModel
import com.github.gunin_igor75.task_list.presentation.PurchaseApp
import javax.inject.Inject


class PurchaseProvider : ContentProvider() {

    @Inject
    lateinit var appDao: PurchaseDao

    private val component by lazy {
        (context as PurchaseApp).component
    }

    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
        addURI("com.github.gunin_igor75.task_list", "purchases/", GET_PURCHASES_QUERY)
        addURI("com.github.gunin_igor75.task_list", "purchases/#", GET_PURCHASE_BY_ID_QUERY)
        addURI("com.github.gunin_igor75.task_list", "purchase_post/", POST_PURCHASE_QUERY)
        addURI("com.github.gunin_igor75.task_list", "purchase_put/", PUT_PURCHASE_QUERY)
        addURI("com.github.gunin_igor75.task_list", "purchase_delete/", DELETE_PURCHASE_QUERY)
    }

    override fun onCreate(): Boolean {
        component.inject(this)
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        return when (uriMatcher.match(uri)) {
            GET_PURCHASES_QUERY -> {
                appDao.getPurchasesCursor()
            }

            GET_PURCHASE_BY_ID_QUERY -> {
                val id = uri.toString().substringAfterLast("/").trim().toInt()
                appDao.getPurchaseByIdCursor(id)
            }

            else -> {
                return null
            }
        }
    }

    override fun getType(uri: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        when (uriMatcher.match(uri)) {
            POST_PURCHASE_QUERY -> {
                if (values == null) return null
                val id = 0
                val name = values.getAsString(KEY_NAME)
                val count = values.getAsInteger(KEY_COUNT)
                val enabled = values.getAsBoolean(KEY_ENABLED)
                appDao.addPurchaseProvider(
                    PurchaseDbModel(
                        id,
                        name,
                        count,
                        enabled
                    )
                )
            }
        }
        return null
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        when (uriMatcher.match(uri)) {
            DELETE_PURCHASE_QUERY -> {
                val id = selectionArgs?.get(0)?.toInt() ?: -1
                return appDao.deletePurchaseProvider(id)
            }
        }
        return 0
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        when (uriMatcher.match(uri)) {
            POST_PURCHASE_QUERY -> {
                if (values == null) return 0
                val id = values.getAsInteger(KEY_ID)
                val name = values.getAsString(KEY_NAME)
                val count = values.getAsInteger(KEY_COUNT)
                val enabled = values.getAsBoolean(KEY_ENABLED)
                appDao.addPurchaseProvider(
                    PurchaseDbModel(
                        id,
                        name,
                        count,
                        enabled
                    )
                )
            }
        }
        return 1
    }

    companion object {
        private const val TAG = "PurchaseProvider"
        private const val GET_PURCHASES_QUERY = 100
        private const val GET_PURCHASE_BY_ID_QUERY = 111
        private const val POST_PURCHASE_QUERY = 222
        private const val PUT_PURCHASE_QUERY = 333
        private const val DELETE_PURCHASE_QUERY = 444

        private const val KEY_ID = "id"
        private const val KEY_NAME = "name"
        private const val KEY_COUNT = "count"
        private const val KEY_ENABLED = "enabled"
    }
}