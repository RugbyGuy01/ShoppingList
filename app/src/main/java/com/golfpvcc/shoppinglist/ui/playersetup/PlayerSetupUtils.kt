package com.golfpvcc.shoppinglist.ui.playersetup

import android.widget.Toast
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import com.golfpvcc.shoppinglist.ui.detail.CourseDetailViewModel
import com.golfpvcc.shoppinglist.ui.theme.shape

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
            focusedBorderColor = Color.Red,
            unfocusedBorderColor = Color.Blue,
            focusedLabelColor = Color.Red,
            unfocusedLabelColor = Color.Blue,
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