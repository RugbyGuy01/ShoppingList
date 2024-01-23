package com.golfpvcc.shoppinglist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.golfpvcc.shoppinglist.ui.ShoppingNavigation
import com.golfpvcc.shoppinglist.ui.home.HomeScreen
import com.golfpvcc.shoppinglist.ui.home.TutorialSwipeToDismissScreen
import com.golfpvcc.shoppinglist.ui.theme.ShoppingListTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShoppingListTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                   ShoppingListAppl()
                }
            }
        }
    }

    @Composable
    fun ShoppingListAppl(){
//        TutorialSwipeToDismissScreen()
        ShoppingNavigation()
    }
}

