package com.golfpvcc.shoppinglist.ui.coursedetail

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.Popup
import androidx.lifecycle.viewmodel.compose.viewModel
import com.golfpvcc.shoppinglist.ui.HoleParList
import com.golfpvcc.shoppinglist.ui.Utils

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
        CourseDetailEntry(
            courseDetailState = viewModel.state,
            onCourseNameChange = viewModel::onCourseNameChange,
            onDisplayFront9Change = viewModel::onDisplayFront9Change,
            onParChange = viewModel::onParChange,
            onHandicapChange = viewModel::onHandicapChange,
            saveCourseRecord = viewModel::saveCourseRecord,
            onPopupSelectHolePar = viewModel::onPopupSelectHolePar,
            getPopupSelectHolePar = viewModel::getPopupSelectHolePar,
            getHolePar = viewModel::getHolePar,
        ) {
            navigateUp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseDetailEntry(
    modifier: Modifier = Modifier,
    courseDetailState: CourseDetailState,
    onCourseNameChange: (String) -> Unit,
    onDisplayFront9Change: (Boolean) -> Unit,
    onParChange: (Int, Int) -> Unit,
    onHandicapChange: (Int, Int) -> Unit,
    saveCourseRecord: () -> Unit,
    onPopupSelectHolePar: (Int) -> Unit,
    getPopupSelectHolePar: () -> Int,
    getHolePar: (Int) -> Int,
    navigateUp: () -> Unit
) {

    Column(
        modifier = modifier
            .padding(5.dp)
            .fillMaxHeight()
    ) {
        Row {
            GetCourseName(courseDetailState, onCourseNameChange)
            DisplayFrontBacklButtons(courseDetailState, onDisplayFront9Change)
        }
        Spacer(modifier = Modifier.size(12.dp))
        Divider(color = Color.Blue, thickness = 1.dp)
        ShowHoleDetailsList(
            modifier = Modifier.weight(1f),
            courseDetailState,
            onParChange,
            onHandicapChange,
            onPopupSelectHolePar,
            getPopupSelectHolePar,
            getHolePar,
        )
        Divider(color = Color.Blue, thickness = 1.dp)
        Spacer(modifier = Modifier.size(12.dp))
        DisplaySaveCancelButtons(saveCourseRecord, navigateUp, courseDetailState.isUpdatingCourse)
    }
}

@Composable
fun GetCourseName(courseDetailState: CourseDetailState, onCourseNameChange: (String) -> Unit) {
    val mMaxLength = 15
    val mContext = LocalContext.current

    OutlinedTextField(
        modifier = Modifier.width(200.dp),
        value = courseDetailState.mCoursename,
        textStyle = MaterialTheme.typography.headlineMedium,
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
        shape = shape.large,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Red,
            unfocusedBorderColor = White,
            focusedLabelColor = Red,
            unfocusedLabelColor = White,
        )
    )
}

@Composable
fun DisplayFrontBacklButtons(
    courseDetailState: CourseDetailState,
    onDisplayFront9Change: (Boolean) -> Unit,
) {
    Button(
        modifier = Modifier
            .padding(top = 20.dp)
            .height(40.dp),
        onClick = {
            onDisplayFront9Change(courseDetailState.mDisplayFrontNine)
        },
        shape = shape.large
    ) {
        if (courseDetailState.mDisplayFrontNine) Text(text = "Front 9") else Text(text = "Back 9")
    }
}

@Composable
fun ShowHoleDetailsList(
    modifier: Modifier,
    courseDetailState: CourseDetailState,
    onParChange: (Int, Int) -> Unit,
    onHandicapChange: (Int, Int) -> Unit,
    onPopupSelectHolePar: (Int) -> Unit,
    getPopupSelectHolePar: () -> Int,
    getHolePar: (Int) -> Int,
) {
    val parHoleInx = getPopupSelectHolePar()

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
    if (parHoleInx != -1) {
        val newParValue = DropDownMenuSelectHolePar(
            parHoleInx,
            onPopupSelectHolePar,
            onParChange,
            getPopupSelectHolePar,
            getHolePar,
        )
    }
}


@Composable
fun HoleDetail(
    holeIdx: Int,
    par: Int,
    handicap: Int,
    onParChange: (Int, Int) -> Unit,
    onPopupSelectHolePar: (Int) -> Unit,
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
                onClick = { onPopupSelectHolePar(holeIdx) },
            ) {
                Text(
                    text = "Par $par",
                    style = MaterialTheme.typography.bodyMedium
                )
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
fun DropDownMenuSelectHolePar(
    holeIdx: Int,
    onPopupSelectHolePar: (Int) -> Unit,
    onParChange: (Int, Int) -> Unit,
    getPopupSelectHolePar: () -> Int,
    getHolePar: (Int) -> Int,
) {
    var expanded = if (getPopupSelectHolePar() < 0) false else true
    val currentHolePar = getHolePar(holeIdx)
    Popup(
        alignment = Alignment.CenterEnd,
        onDismissRequest = {
            onPopupSelectHolePar(-1)
            expanded
        }
    ) {
        Surface(
            modifier = Modifier.padding(1.dp),
            shape = RoundedCornerShape(12.dp),
            color = MaterialTheme.colorScheme.surface,
            border = BorderStroke(1.dp, Red)
        ) //Well, its a border)
        {
            Column(
                modifier = Modifier
                    .padding(3.dp)
                    .width(110.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.padding(15.dp),
                    text = "Hole ${holeIdx + 1}"
                )
                Divider(color = Color.Blue, thickness = 1.dp)
                Utils.holeParList.forEach {
                    Divider(color = Color.Green, thickness = 1.dp)
                    Text(
                        text = "      ${it.Par}     ",
                        textAlign = TextAlign.Center,
                        color = if (currentHolePar == it.Par) White else Color.Unspecified,
                        style = if (currentHolePar == it.Par) TextStyle(background = Black) else TextStyle(
                            background = Color.Yellow
                        ),
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable {
                                onParChange(holeIdx, it.Par)
                                onPopupSelectHolePar(-1)
                            }
                    )
                }
            }
        }
    }
}

@Composable
fun DisplaySaveCancelButtons(
    saveCourseRecord: () -> Unit,
    navigateUp: () -> Unit,
    isUpdatingCourse: Boolean,
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
            Text(
                text = if (isUpdatingCourse) {
                    "Update"
                } else {
                    "Save"
                }
            )
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
