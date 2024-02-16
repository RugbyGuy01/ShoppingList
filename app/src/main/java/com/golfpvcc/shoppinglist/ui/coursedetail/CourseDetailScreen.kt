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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.golfpvcc.shoppinglist.ui.Utils

import com.golfpvcc.shoppinglist.ui.detail.CourseDetailViewModel
import com.golfpvcc.shoppinglist.ui.theme.shape


@Composable
fun CourseDetailScreen(
    navHostController: NavHostController,
    id: Int?,
//    navigateUp: () -> Unit
) {
    Log.d("VIN", "CourseDetailScreen CourseDetail $id")
    val viewModel = viewModel<CourseDetailViewModel>(
        factory = CourseDetailViewModel.CourseDetailViewModelFactor(id)
    )

    Scaffold {
        Spacer(modifier = Modifier.padding(it))

        CourseDetailEntry(
            Modifier,
            viewModel,
            navHostController
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseDetailEntry(
    modifier: Modifier = Modifier,
    viewModel: CourseDetailViewModel,
    navHostController: NavHostController,
) {
    //viewModel.setHandicapAvailable()
    Column(
        modifier = modifier
            .padding(5.dp)
            .fillMaxHeight()
    ) {
        Row {
            GetCourseName(viewModel)
            DisplayFlipHdcpsButtons(viewModel)
        }
        Spacer(modifier = Modifier.size(12.dp))
        Divider(color = Color.Blue, thickness = 1.dp)
        Spacer(modifier = Modifier.size(12.dp))
        ShowHoleDetailsList(
            modifier = Modifier.weight(1f),
            viewModel,
        )
        Spacer(modifier = Modifier.size(12.dp))
        Divider(color = Color.Blue, thickness = 1.dp)
        Spacer(modifier = Modifier.size(12.dp))
        DisplaySaveCancelButtons(viewModel, navHostController)
    }
}

@Composable
fun GetCourseName(viewModel: CourseDetailViewModel) {
    val focusManager = LocalFocusManager.current

    val mMaxLength = 15
    val mContext = LocalContext.current

    OutlinedTextField(
        modifier = Modifier.width(200.dp),
        value = viewModel.state.mCoursename,
        textStyle = MaterialTheme.typography.bodyLarge,
        singleLine = true,
        onValueChange = {
            if (it.length <= mMaxLength) viewModel.onCourseNameChange(it)
            else Toast.makeText(
                mContext,
                "Cannot be more than $mMaxLength Characters",
                Toast.LENGTH_SHORT
            ).show()
        },
        label = { Text(text = "Course Name") },
        placeholder = { Text(text = "Enter Course Name") },
        shape = shape.large,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Red,
            unfocusedBorderColor = Blue,
            focusedLabelColor = Red,
            unfocusedLabelColor = Blue,
        ),
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Sentences,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
            }
        )

    )
}

@Composable
fun DisplayFlipHdcpsButtons(viewModel: CourseDetailViewModel) {
    Button(
        modifier = Modifier
            .padding(top = 20.dp, start = 20.dp)
            .height(40.dp),
        onClick = {
            viewModel.onFlipHdcpsChange(viewModel.state.mFlipHdcps)
        },
        shape = shape.large
    ) {
        if (viewModel.state.mFlipHdcps) Text(text = "Normal") else Text(text = "Hdcp Flip")
    }
}

@Composable
fun ShowHoleDetailsList(
    modifier: Modifier, viewModel: CourseDetailViewModel,
) {
    val parHoleInx = viewModel.getPopupSelectHolePar()
    val hdcpHoleInx = viewModel.getPopupSelectHoleHandicap()

    LazyColumn(modifier) {
        items(viewModel.state.mPar.size) { holeIdx ->
            HoleDetail(viewModel, holeIdx)
            Spacer(modifier = Modifier.size(10.dp))
        } // end of CourseItem
    }
    if (parHoleInx != -1) {     // the function is called many time, if click par/hdcp button then popup select hole will be set
        DropDownSelectHolePar(viewModel, parHoleInx)
    }
    if (hdcpHoleInx != -1) {
        DropDownSelectHoleHandicap(viewModel, hdcpHoleInx)
    }
}

@Composable
fun HoleDetail(viewModel: CourseDetailViewModel, holeIdx: Int) {

    Card(
        border = BorderStroke(1.dp, Color.Black),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
    ) {

        Row(
            modifier = Modifier
                .width(210.dp)
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(
                onClick = { viewModel.onPopupSelectHolePar(holeIdx) },
            ) {
                Text(
                    text = "Par ${viewModel.state.mPar[holeIdx]}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Text(
                text = "Hole ${holeIdx + 1}",
                style = MaterialTheme.typography.bodyLarge,
            )
            TextButton(
                onClick = { viewModel.onPopupSelectHoleHdcp(holeIdx) },
            ) {
                val displayText =
                    if (viewModel.state.mHandicap[holeIdx] == 0) " --  " else viewModel.state.mHandicap[holeIdx]
                Text(
                    text = "Hdcp $displayText",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable     // holeIdx is zero base index
fun DropDownSelectHoleHandicap(viewModel: CourseDetailViewModel, holeIdx: Int) {
    var expanded = if (viewModel.getPopupSelectHoleHandicap() < 0) false else true
    val currentHoleHdcp = viewModel.getHoleHandicap(holeIdx)
    val courseHdcp = viewModel.state.availableHandicap
    val displayFrontNineHdcp: Int
    val FlipHdcps = viewModel.getFlipHdcps()

    if (FlipHdcps) {
        displayFrontNineHdcp =
            if (holeIdx < 9) 0 else 1 // used to display the handicap holes to select
    } else {
        displayFrontNineHdcp =
            if (holeIdx < 9) 1 else 0 // used to display the handicap holes to select
    }

    Popup(
        alignment = Alignment.CenterEnd,
        onDismissRequest = {
            viewModel.onPopupSelectHoleHdcp(-1)
            expanded
        }
    ) {     // Composable content to be shown in the Popup
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

                courseHdcp.forEachIndexed { inx, holeHdcp ->
                    if ((inx % 2) == displayFrontNineHdcp) {
                        if (courseHdcp[inx].available || currentHoleHdcp == courseHdcp[inx].holeHandicap) {
                            Divider(color = Color.Green, thickness = 1.dp)
                            Text(
                                text = "      ${courseHdcp[inx].holeHandicap}     ",
                                textAlign = TextAlign.Center,
                                color = if (currentHoleHdcp == courseHdcp[inx].holeHandicap) White else Color.Unspecified,
                                style = if (currentHoleHdcp == courseHdcp[inx].holeHandicap) TextStyle(
                                    background = Black
                                ) else TextStyle(
                                    background = Color.Yellow
                                ),
                                modifier = Modifier
                                    .padding(8.dp)
                                    .clickable {
                                        if (currentHoleHdcp != courseHdcp[inx].holeHandicap) {
                                            val returnHdcpToPool =
                                                courseHdcp.find { it.holeHandicap == currentHoleHdcp }
                                            if (returnHdcpToPool != null) {
                                                returnHdcpToPool.available = true
                                                Log.d(
                                                    "VIN",
                                                    "returnHdcpToPool ${returnHdcpToPool.holeHandicap}"
                                                )
                                            }
                                        }

                                        viewModel.onHandicapChange(
                                            holeIdx,
                                            courseHdcp[inx].holeHandicap
                                        )
                                        courseHdcp[inx].available =
                                            false        // this handicap has been selected
                                        Log.d(
                                            "VIN",
                                            "clickable Card Hole $holeIdx set to Hdcp ${courseHdcp[inx].holeHandicap} "
                                        )
                                        viewModel.onPopupSelectHoleHdcp(-1)
                                    }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable  // holeIdx is zero base index
fun DropDownSelectHolePar(viewModel: CourseDetailViewModel, holeIdx: Int) {
    var expanded = if (viewModel.getPopupSelectHolePar() < 0) false else true
    val currentHolePar = viewModel.getHolePar(holeIdx)
    Popup(
        alignment = Alignment.CenterEnd,
        onDismissRequest = {
            viewModel.onPopupSelectHolePar(-1)
            expanded
        }
    ) {     // Composable content to be shown in the Popup
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
                                viewModel.onParChange(holeIdx, it.Par)
                                viewModel.onPopupSelectHolePar(-1)
                            }
                    )
                }
            }
        }
    }
}

@Composable
fun DisplaySaveCancelButtons(viewModel: CourseDetailViewModel, navHostController: NavHostController,) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            onClick = {
                viewModel.saveCourseRecord()
                navHostController.navigate(route = "Configuration") // display courses screen
            },
            modifier = Modifier
                .height(40.dp),  //vpg
            shape = shape.large
        ) {
            Text(
                text = if (viewModel.state.isUpdatingCourse) {
                    "Update"
                } else {
                    "Save"
                }
            )
        }
        Button(
            onClick = {
                navHostController.navigate(route = "Configuration") // display courses screen
            },
            modifier = Modifier
                .height(40.dp),  //vpg
            shape = shape.large
        ) {
            Text(text = "Cancel")
        }
    }
}
