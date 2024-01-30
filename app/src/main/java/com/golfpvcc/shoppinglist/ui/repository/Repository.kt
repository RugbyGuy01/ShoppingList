package com.golfpvcc.shoppinglist.ui.repository

import com.golfpvcc.shoppinglist.data.room.dao.ItemDao
import com.golfpvcc.shoppinglist.data.room.dao.ListDao
import com.golfpvcc.shoppinglist.data.room.dao.StoreDao
import com.golfpvcc.shoppinglist.data.room.model.Item
import com.golfpvcc.shoppinglist.data.room.model.ShoppingList
import com.golfpvcc.shoppinglist.data.room.model.Store

class Repository(
    private val listDao: ListDao,
    private val storeDao: StoreDao,
    private val itemDao: ItemDao
) {
    val store = storeDao.getAllStores()
    val getItemsWithListAndStore = listDao.getItemsWithStoreAndList()

    fun getItemWithStoreAndList(id: Int) = listDao
        .getItemWithStoreAndListFilterById(id)

    fun getItemWithStoreAndListFilterById(id: Int) =
        listDao.getItemsWithStoreAndListFilterById(id)

    suspend fun insertList(shoppingList: ShoppingList) {
        listDao.insertShopppingList(shoppingList)
    }

    suspend fun insertStore(store: Store) {
        storeDao.insert(store)
    }

    suspend fun insertItem(item: Item) {
        itemDao.insert(item)
    }

    suspend fun deleteItem(item: Item) {
        itemDao.delete(item)
    }

    suspend fun updateItem(item: Item) {
        itemDao.update(item)
    }
}