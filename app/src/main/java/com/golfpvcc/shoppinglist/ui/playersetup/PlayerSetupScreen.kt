package com.golfpvcc.shoppinglist.ui.playersetup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController


@Composable
fun PlayerSetupScreen(
    navController: NavHostController,
    id: Int?,
) {
    val viewModel = viewModel<PlayerSetupViewModel>(
        factory = PlayerSetupViewModel.PlayerSetupViewModelFactor(id)
    )

    Scaffold {
        Spacer(modifier = Modifier.padding(it))

        PlayerSetupEntry(
            Modifier,
            viewModel,
            navController
        )
    }
}

@Composable
fun PlayerSetupEntry(
    modifier: Modifier,
    viewModel: PlayerSetupViewModel,
    navController: NavHostController,
) {
    Column(
        modifier = modifier
            .padding(5.dp)
            .fillMaxHeight()
    ) {
        Row {
            Text(text = "Course Name ${viewModel.state.mCourseName}")
        }
    }
}