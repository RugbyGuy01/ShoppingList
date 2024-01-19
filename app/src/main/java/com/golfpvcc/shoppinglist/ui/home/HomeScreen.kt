package com.golfpvcc.shoppinglist.ui.home

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import com.golfpvcc.shoppinglist.ui.theme.shape

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.golfpvcc.shoppinglist.ui.Category
import com.golfpvcc.shoppinglist.ui.Utils


@Composable
fun HomeScreen(
    onNavigate: (Int) -> Unit
) {
    val homeViewModel = viewModel(modelClass = HomeViewModel::class.java)
    val homeState = homeViewModel.state

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { onNavigate(-1) }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add"
                )
            }
        }
    ) {
        LazyColumn(modifier = Modifier.padding(it)) {
            item {
                LazyRow {
                    items(Utils.category) { category: Category ->
                        CategoryItem(
                            category.resId, category.title, category == homeState.category
                        ) {
                            homeViewModel.onCategoryChange(category)        // this is the onClick function call back
                        }
                        Spacer(modifier = Modifier.size(16.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryItem(
    @DrawableRes iconRes: Int,
    title: String,
    selected: Boolean,
    onItemClciked: () -> Unit
) {

    Card(
        modifier = Modifier
            .padding(top = 8.dp, bottom = 8.dp, start = 8.dp)
            .selectable(
                selected = selected,
                interactionSource = MutableInteractionSource(),
                indication = rememberRipple(),
                onClick = { onItemClciked.invoke() }
            ),
        border = BorderStroke(
            1.dp,
            if (selected) MaterialTheme.colorScheme.primary.copy(.5f)
            else MaterialTheme.colorScheme.onSurface
        ),
        shape = shape.large,
        colors = CardDefaults.cardColors(
            if (selected) MaterialTheme.colorScheme.primary.copy(.5f)
            else MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                modifier = Modifier.aspectRatio(16f / 9f)
            )

            //            Icon(
//                painter = painterResource(id = iconRes),
//                contentDescription = null,
//                modifier = Modifier.padding(24.dp)
//            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
        }
    }
}