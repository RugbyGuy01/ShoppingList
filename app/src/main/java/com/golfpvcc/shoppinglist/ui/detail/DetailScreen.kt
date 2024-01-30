package com.golfpvcc.shoppinglist.ui.detail


import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.lifecycle.viewmodel.compose.viewModel
import com.golfpvcc.shoppinglist.ui.Category
import com.golfpvcc.shoppinglist.ui.Utils
import com.golfpvcc.shoppinglist.ui.home.CategoryItem
import com.golfpvcc.shoppinglist.ui.home.formatDate
import com.golfpvcc.shoppinglist.ui.theme.shape
import java.util.Date

@Composable
fun DetailScreen(
    id: Int,
    navigateUp: () -> Unit
) {
    val viewModel = viewModel<DetailViewModel>(factory = DetailViewModel.DetailViewModelFactor(id))
    Scaffold {
        Spacer(modifier = Modifier.padding(it))
        DetailEntry(
            state = viewModel.state,
            onDateSelected = viewModel::onDateChange,
            onStoreChange = viewModel::onStoreChange,
            onItemChange = viewModel::onItemChange,
            onQtyChange = viewModel::onQtyChange,
            onCategoryChange = viewModel::onCategoryChange,
            onDialogDismissed = viewModel::onScreenDialogDismissed,
            onSaveStore = viewModel::addStore,
            upDateItem = { (viewModel::updateShoppingListItem)(id) },
            saveItem = viewModel::addShoppingItem
        ) {
            navigateUp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailEntry(
    modifier: Modifier = Modifier,
    state: DetailState,
    onDateSelected: (Date) -> Unit,
    onStoreChange: (String) -> Unit,
    onItemChange: (String) -> Unit,
    onQtyChange: (String) -> Unit,
    onCategoryChange: (Category) -> Unit,
    onDialogDismissed: (Boolean) -> Unit,
    onSaveStore: () -> Unit,
    upDateItem: () -> Unit,
    saveItem: () -> Unit,
    navigateUp: () -> Unit
) {
    var isNewEnabled by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier.padding(16.dp)
    ) {
        TextField(
            value = state.item,
            onValueChange = { onItemChange(it) },
            label = { Text(text = "Item") },
//            colors =
            shape = shape.large
        )
        Spacer(modifier = Modifier.Companion.size(12.dp))
        Row {
            TextField(
                value = state.store,
                onValueChange = {
//                    if (isNewEnabled) onStoreChange.invoke(it)
                    if (isNewEnabled) onStoreChange(it)
                },
                modifier = Modifier.weight(1f),
                shape = shape.large,
                label = { Text(text = "Store") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = null,
                        Modifier.clickable {
                            onDialogDismissed.invoke(!state.isScreenDialogDismissed)
                        }
                    )
                },
            ) // end of text field
            if (!state.isScreenDialogDismissed) {
                Popup(
                    onDismissRequest = {
                        onDialogDismissed.invoke(!state.isScreenDialogDismissed)
                    }
                ) {
                    Surface(modifier = Modifier.padding(16.dp)) {
                        Column {
                            state.storeList.forEach {
                                Text(
                                    text = it.storeName,
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .clickable {
                                            onStoreChange.invoke(it.storeName)
                                            onDialogDismissed(!state.isScreenDialogDismissed)
                                        }
                                )
                            }
                        }
                    }
                }
            } // end if

            TextButton(onClick = {
                isNewEnabled = if (isNewEnabled) {
//                    onSaveStore.invoke()
                    Log.d("VIN:", "Button to Add store")
                    onSaveStore()       // add store to the database
                    !isNewEnabled
                } else {
                    !isNewEnabled
                }
            }) {
                Log.d("VIN", "Field changed")
                Text(text = if (isNewEnabled) "Save" else "New")

            }
        } // end of row
        Spacer(modifier = Modifier.Companion.size(12.dp))
        Row(horizontalArrangement = Arrangement.SpaceEvenly) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.size(4.dp))
                Text(text = formatDate(state.date))
                Spacer(modifier = Modifier.size(4.dp))
// date picker Video time 1:09

            }
            TextField(
                value = state.qty,
                onValueChange = { onQtyChange(it) },
                label = { Text(text = "Qty") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                shape = shape.large
            )
        }
        Spacer(modifier = Modifier.Companion.size(12.dp))
        onCategoryChange(state.category)
        LazyRow(Modifier.height(75.dp)) {
            items(Utils.category) { category ->
                CategoryItem(
                    iconRes = category.resId,
                    title = category.title,
                    selected = category == state.category
                ) {
                    onCategoryChange(category)
                }
                Spacer(modifier = Modifier.Companion.size(16.dp))
            }
        } // end lazyrow
        val buttonTitle = if (state.isUpdatingItem) "Update Item" else "Add Item"
        Button(
            onClick = {
                when (state.isUpdatingItem) {
                    true -> {
                        Log.d("VIN", "Field upDateItem")
                        upDateItem()
                    }

                    false -> {
                        Log.d("VIN", "Field saveItem")
                        saveItem()
                    }
                }
                navigateUp()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),  //vpg
            enabled = state.item.isNotEmpty() &&
                    state.store.isNotEmpty() &&
                    state.qty.isNotEmpty(),
            shape = shape.large
        ) {
            Text(text = buttonTitle)
        }
    }
}


