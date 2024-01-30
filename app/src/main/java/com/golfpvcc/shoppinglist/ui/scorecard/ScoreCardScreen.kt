package com.golfpvcc.shoppinglist.ui.scorecard

import android.app.Activity
import android.content.pm.ActivityInfo
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.golfpvcc.shoppinglist.ui.theme.shape

@Composable
fun ScoreCardScreen(
    id: Int,
    navigateUp: () -> Unit
) {
    val viewModel = viewModel<ScoreCardViewModel>(
        factory = ScoreCardViewModel.ScoreCardViewModelFactor(id)
    )

    Scaffold {
        Spacer(modifier = Modifier.padding(it))
        ScoreCardEntry(
            scoreCardState = viewModel.state,
            onCourseNameChange = viewModel::onCourseNameChange,
            onDisplayFront9Change = viewModel::onDisplayFront9Change,
//            onParChange = viewModel::onParChange,
//            onHandicapChange = viewModel::onHandicapChange,
//            saveCourseRecord = viewModel::saveCourseRecord,
//            onPopupSelectHolePar = viewModel::onPopupSelectHolePar,
        ) {
            navigateUp()
        }
    }
}

@Composable
fun ScoreCardEntry(
    scoreCardState: ScoreCardState,
    onCourseNameChange: (String) -> Unit,
    onDisplayFront9Change: (Boolean) -> Unit,
    onNavigate: (Int) -> Unit
) {
    val activity = (LocalContext.current as Activity)
    activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

    var displayHolesRange = IntRange(0, 8)
    Column {
        Row {
            GetCourseName(
                scoreCardState,
                onCourseNameChange
            )
            DisplayFrontBacklButtons(scoreCardState, onDisplayFront9Change)
        }
        Spacer(modifier = Modifier.size(12.dp))


        BuildScoreCardRow(Modifier, displayHolesRange, scoreCardState)
        BuildScoreCardRow(Modifier, displayHolesRange, scoreCardState)
        BuildScoreCardRow(Modifier, displayHolesRange, scoreCardState)
        BuildScoreCardRow(Modifier, displayHolesRange, scoreCardState)
    }
}

@Composable
fun GetCourseName(scoreCardState: ScoreCardState, onCourseNameChange: (String) -> Unit) {
    val mMaxLength = 15
    val mContext = LocalContext.current

    OutlinedTextField(
        modifier = Modifier.width(200.dp),
        value = scoreCardState.mCoursename,
        textStyle = LocalTextStyle.current.copy(
        ),
        singleLine = true,
        onValueChange = {
            if (it.length <= mMaxLength) onCourseNameChange(it)
            else Toast.makeText(
                mContext,
                "Cannot be more than $mMaxLength Characters",
                Toast.LENGTH_SHORT
            ).show()
        },
        label = { Text(text = "Course Name") },
        shape = shape.small,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Red,
            unfocusedBorderColor = Color.Gray,
            focusedLabelColor = Color.White,
            unfocusedLabelColor = Color.Black,
        )
    )
}

@Composable
fun DisplayFrontBacklButtons(
    scoreCardState: ScoreCardState,
    onDisplayFront9Change: (Boolean) -> Unit,
) {
    Button(
        modifier = Modifier
            .padding(top = 20.dp)
            .height(40.dp),
        onClick = {
            onDisplayFront9Change(scoreCardState.mDisplayFrontNine)
        },
        shape = shape.large
    ) {
        if (scoreCardState.mDisplayFrontNine) Text(text = "Front 9") else Text(text = "Back 9")
    }
}

@Composable
fun BuildScoreCardRow(
    modifier: Modifier = Modifier,
    range: IntRange,
    scoreCardState: ScoreCardState,
) {
    Box(modifier = modifier) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth(1f)
        ) {
            Box(
                modifier = Modifier
                    .weight(.3f)
                    .height(30.dp)
                    .border(1.dp, Color.Red),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = " Hole",
                    fontSize = 21.sp,
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
            for (holeIdx in range) {
                Box(
                    modifier = Modifier
                        .weight(.1f)
                        .height(30.dp)
                        .border(1.dp, Color.Red),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = " ${holeIdx + 1}",
                        fontSize = 21.sp,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
            }
            Box(
                modifier = Modifier
                    .weight(.2f)
                    .height(30.dp)
                    .border(1.dp, Color.Red),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Total",
                    fontSize = 21.sp,
                    style = MaterialTheme.typography.bodyLarge,
                )
            }

        }
    }
}
