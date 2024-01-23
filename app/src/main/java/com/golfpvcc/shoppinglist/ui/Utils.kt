package com.golfpvcc.shoppinglist.ui

import androidx.annotation.DrawableRes
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import com.golfpvcc.shoppinglist.R
import java.text.SimpleDateFormat
import java.util.Calendar

object Utils {
    val category = listOf(
        Category(title = "Drinks", resId = R.drawable.ic_drinks, id = 0),
        Category(title = "Vegetable", resId = R.drawable.ic_vegitables, id = 1),
        Category(title = "Fruits", resId = R.drawable.ic_fruits, id = 2),
        Category(title = "Cleaning", resId = R.drawable.ic_cleaning, id = 3),
        Category(title = "Electronic", resId = R.drawable.ic_electronic, id = 4),
        Category(title = "None", resId =R.drawable.ic_baseline_not_interested_24 ,id = 10001)
    )
}

data class Category(
    @DrawableRes val resId: Int = -1,
    val title: String = "",
    val id: Int = 10001,
)
