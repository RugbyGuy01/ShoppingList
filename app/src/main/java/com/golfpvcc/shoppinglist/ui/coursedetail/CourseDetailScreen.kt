package com.golfpvcc.shoppinglist.ui.coursedetail

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

import com.golfpvcc.shoppinglist.ui.detail.CourseDetailState
import com.golfpvcc.shoppinglist.ui.detail.CourseDetailViewModel
import com.golfpvcc.shoppinglist.ui.theme.shape


@Composable
fun CourseDetailScreen(
    id: Int,
    navigateUp: () -> Unit
) {
    val viewModel = viewModel<CourseDetailViewModel>(
        factory = CourseDetailViewModel.CourseDetailViewModelFactor(id)
    )

    Scaffold {
        Spacer(modifier = Modifier.padding(it))
//        CourseDetailEntry(
//            courseDetailState = viewModel.state,
//            onCourseNameChange = viewModel::onCourseNameChange,
//            onDisplayFront9Change = viewModel::onDisplayFront9Change,
//            onParChange = viewModel::onParChange,
//            onHandicapChange = viewModel::onHandicapChange,
//            saveCourseRecord = viewModel::saveCourseRecord,
//            onPopupSelectHolePar = viewModel::onPopupSelectHolePar,
//        ) {
//            navigateUp()
//        }
    }
}

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun CourseDetailEntry(
//    modifier: Modifier = Modifier,
//    courseDetailState: CourseDetailState,
//    onCourseNameChange: (String) -> Unit,
//    onDisplayFront9Change: (Boolean) -> Unit,
//    onParChange: (Int, Int) -> Unit,
//    onHandicapChange: (Int, Int) -> Unit,
//    saveCourseRecord: () -> Unit,
//    onPopupSelectHolePar: (Boolean) -> Unit,
//    navigateUp: () -> Unit
//) {
//
//    Column(
//        modifier = modifier
//            .padding(5.dp)
//            .fillMaxHeight()
//    ) {
//        Row {
//            GetCourseName(courseDetailState, onCourseNameChange)
////            DisplayFrontBacklButtons(courseDetailState, onDisplayFront9Change)
//        }
//        Spacer(modifier = Modifier.size(12.dp))
//        ShowHoleDetailsList(
//            modifier = Modifier.weight(1f),
//            courseDetailState,
//            onParChange,
//            onHandicapChange,
//            onPopupSelectHolePar
//        )
//        Spacer(modifier = Modifier.size(12.dp))
//        DisplaySaveCancelButtons(saveCourseRecord, navigateUp)
//    }
//}

//@Composable
//fun GetCourseName(courseDetailState: CourseDetailState, onCourseNameChange: (String) -> Unit) {
//    val mMaxLength = 15
//    val mContext = LocalContext.current
//
//    OutlinedTextField(
//        modifier = Modifier.width(200.dp),
//        value = courseDetailState.mCoursename,
//        textStyle = LocalTextStyle.current.copy(
//        ),
//        singleLine = true,
//        onValueChange = {
//            if (it.length <= mMaxLength) onCourseNameChange(it)
//            else Toast.makeText(
//                mContext,
//                "Cannot be more than $mMaxLength Characters",
//                Toast.LENGTH_SHORT
//            ).show()
//        },
//        label = { Text(text = "Course Name") },
//        shape = shape.large,
//        colors = OutlinedTextFieldDefaults.colors(
//            focusedBorderColor = White,
//            unfocusedBorderColor = White,
//            focusedLabelColor = White,
//            unfocusedLabelColor = White,
//        )
//    )
//}
//
//@Composable
//fun DisplayFrontBacklButtons(
//    courseDetailState: CourseDetailState,
//    onDisplayFront9Change: (Boolean) -> Unit,
//) {
//    Button(
//        modifier = Modifier
//            .padding(top = 20.dp)
//            .height(40.dp),
//        onClick = {
//            onDisplayFront9Change(courseDetailState.mDisplayFrontNine)
//        },
//        shape = shape.large
//    ) {
//        if (courseDetailState.mDisplayFrontNine) Text(text = "Front 9") else Text(text = "Back 9")
//    }
//}

@Composable
fun ShowHoleDetailsList(
    modifier: Modifier,
    courseDetailState: CourseDetailState,
    onParChange: (Int, Int) -> Unit,
    onHandicapChange: (Int, Int) -> Unit,
    onPopupSelectHolePar: (Boolean) -> Unit,
) {
    LazyColumn(modifier) {
        items(courseDetailState.mPar.size) { holeIdx ->
            HoleDetail(
                holeIdx,
                courseDetailState.mPar[holeIdx],
                courseDetailState.mHandicap[holeIdx],
                onParChange,
                onPopupSelectHolePar,
                onHandicapChange,
                courseDetailState,
            )
            Spacer(modifier = Modifier.size(10.dp))
        } // end of CourseItem
    }
}


@Composable
fun HoleDetail(
    holeIdx: Int,
    par: Int,
    handicap: Int,
    onParChange: (Int, Int) -> Unit,
    onPopupSelectHolePar: (Boolean) -> Unit,
    onHandicapChange: (Int, Int) -> Unit,
    courseDetailState: CourseDetailState,
) {
    var newParValue: Int

    Card(
        border = BorderStroke(1.dp, Color.Black),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
    ) {

        Row(
            modifier = Modifier
                .width(200.dp)
                .padding(15.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(
                onClick = { onPopupSelectHolePar(true) },
            ) {
                Text(
                    text = "Par $par",
                    style = MaterialTheme.typography.bodyMedium
                )
                if (courseDetailState.isPopupSelectHolePar) {
                    newParValue = PopupSelectHolePar(onPopupSelectHolePar)
                    onParChange(holeIdx, newParValue)
                    Log.d("VIN", "New par value  $holeIdx, $newParValue")
                }
            }

            Text(
                text = "Hole ${holeIdx + 1}",
                style = MaterialTheme.typography.bodyLarge,
            )
            Text(
                text = "Hdcp $handicap",
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Composable
fun PopupSelectHolePar(onPopupSelectHolePar: (Boolean) -> Unit): Int {
    var expanded = true
    var newParValue : Int = 4

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { onPopupSelectHolePar(false)}
    ) {
        DropdownMenuItem(
            text = { Text("Par 3") },
            onClick = { newParValue = 3 }
        )
        DropdownMenuItem(
            text = { Text("Par 4") },
            onClick = { newParValue = 4 }
        )
        DropdownMenuItem(
            text = { Text("Par 5") },
            onClick = { newParValue = 5 }
        )
    }
    return newParValue
}

@Composable
fun DisplaySaveCancelButtons(
    saveCourseRecord: () -> Unit,
    navigateUp: () -> Unit
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            onClick = {
                saveCourseRecord()
                navigateUp()
            },
            modifier = Modifier
                .height(40.dp),  //vpg
            shape = shape.large
        ) {
            Text(text = "Save")
        }
        Button(
            onClick = {
                navigateUp()
            },
            modifier = Modifier
                .height(40.dp),  //vpg
            shape = shape.large
        ) {
            Text(text = "Cancel")
        }
    }
}
