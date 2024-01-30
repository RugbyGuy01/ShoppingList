package com.golfpvcc.shoppinglist

import android.content.Context
import com.golfpvcc.shoppinglist.data.room.ShoppingListDatabase
import com.golfpvcc.shoppinglist.ui.repository.CourseRepository
import com.golfpvcc.shoppinglist.ui.repository.Repository

object Graph {
    lateinit var db:ShoppingListDatabase
        private set

    val repository by lazy {
        Repository(
            listDao = db.listDao(),
            storeDao = db.storeDao(),
            itemDao = db.itemDao()
        )
    }
    val courseRepository by lazy {
        CourseRepository(
            courseDao = db.courseDao()
        )
    }
    fun provide(context: Context){
        db = ShoppingListDatabase.getDatabase(context)
    }
}