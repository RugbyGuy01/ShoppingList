package com.golfpvcc.shoppinglist.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Embedded
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.golfpvcc.shoppinglist.data.room.model.Item
import com.golfpvcc.shoppinglist.data.room.model.ShoppingList
import com.golfpvcc.shoppinglist.data.room.model.Store
import kotlinx.coroutines.flow.Flow


@Dao
interface ItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: Item)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(item: Item)

    @Delete
    suspend fun delete(item: Item)

    @Query("SELECT * FROM items")
    fun getAllItems(): Flow<List<Item>>

    @Query("SELECT * FROM items WHERE item_id = :itemId")
    fun getItem(itemId: Int): Flow<Item>
}

@Dao
interface StoreDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(store: Store)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(store: Store)

    @Delete
    suspend fun delete(store: Store)

    @Query("SELECT * FROM stores")
    fun getAllStores(): Flow<List<Store>>

    @Query("SELECT * FROM stores WHERE store_id = :storeId")
    fun getStore(storeId: Int): Flow<Store>
}

@Dao
interface ListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShopppingList(shoppingList: ShoppingList)

    @Query(
        """SELECT * FROM items as I inner JOIN shopping_list as S 
        ON I.listId = S.list_id INNER JOIN stores AS ST
        ON I.storeIdFk = ST.store_id
        """
    )
    fun getItemsWithStoreAndList():Flow<List<ItemsWithStoreAndList>>

    @Query(
        """SELECT * FROM items as I inner JOIN shopping_list as S 
        ON I.listId = S.list_id INNER JOIN stores AS ST
        ON I.storeIdFk = ST.store_id WHERE S.list_id = :listID
        """
    )
    fun getItemsWithStoreAndListFilterById(listID:Int):Flow<List<ItemsWithStoreAndList>>

    @Query(
        """SELECT * FROM items as I inner JOIN shopping_list as S 
        ON I.listId = S.list_id INNER JOIN stores AS ST
        ON I.storeIdFk = ST.store_id WHERE I.item_id = :itemId
        """
    )
    fun getItemWithStoreAndListFilterById(itemId:Int):Flow<ItemsWithStoreAndList>
}
data class ItemsWithStoreAndList(
    @Embedded val item:Item,
    @Embedded val shoppingList: ShoppingList,
    @Embedded val store:Store
)