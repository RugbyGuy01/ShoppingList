package com.golfpvcc.shoppinglist

import android.content.Context
import com.golfpvcc.shoppinglist.data.room.ShoppingListDatabase
import com.golfpvcc.shoppinglist.ui.repository.CourseRepository

object Graph {
    lateinit var db:ShoppingListDatabase
        private set

    val courseRepository by lazy {
        CourseRepository(
            courseDao = db.courseDao()
        )
    }
    fun provide(context: Context){
        db = ShoppingListDatabase.getDatabase(context)
    }
}