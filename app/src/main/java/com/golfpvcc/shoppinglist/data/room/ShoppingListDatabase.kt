package com.golfpvcc.shoppinglist.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.golfpvcc.shoppinglist.data.room.converters.DataConverter
import com.golfpvcc.shoppinglist.data.room.dao.CourseDao
import com.golfpvcc.shoppinglist.data.room.dao.ItemDao
import com.golfpvcc.shoppinglist.data.room.dao.ListDao
import com.golfpvcc.shoppinglist.data.room.dao.StoreDao
import com.golfpvcc.shoppinglist.data.room.model.CourseRecord
import com.golfpvcc.shoppinglist.data.room.model.Item
import com.golfpvcc.shoppinglist.data.room.model.ShoppingList
import com.golfpvcc.shoppinglist.data.room.model.Store

@TypeConverters(value = [DataConverter::class])


@Database(
    entities = [CourseRecord::class, ShoppingList::class, Item::class, Store::class],
    version = 1,
    exportSchema = false
)
abstract class ShoppingListDatabase:RoomDatabase() {
    abstract fun listDao(): ListDao
    abstract fun itemDao(): ItemDao
    abstract fun storeDao(): StoreDao
    abstract fun courseDao(): CourseDao

    companion object{
        @Volatile
        var INSTANCE:ShoppingListDatabase? = null
        fun getDatabase(context:Context):ShoppingListDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context,
                    ShoppingListDatabase::class.java,
                    "shopping_db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}