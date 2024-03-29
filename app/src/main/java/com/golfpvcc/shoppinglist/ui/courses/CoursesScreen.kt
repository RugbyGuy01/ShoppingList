package com.golfpvcc.shoppinglist.ui.courses

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.golfpvcc.shoppinglist.data.room.model.CourseRecord
import com.golfpvcc.shoppinglist.ui.navigation.Screen

// .popBackStack()  // move back to the last screen displayed
@Composable
fun CoursesScreen(
    navController: NavHostController
) {
    val courseViewModel = viewModel(modelClass = CoursesViewModel::class.java)
    val coursesState = courseViewModel.state

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(route = "CourseDetail?id={-1}")
            }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add"
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(it)
        ) {
            Row(
                modifier = Modifier
//                    .padding(it)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Select Golf Course", Modifier.padding(it),
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.Companion.size(12.dp))
            LazyColumn(modifier = Modifier.padding(2.dp)) {
                items(coursesState.courseRecords.size) { index ->
                    CourseItem(
                        coursesState = coursesState,
                        index = index,
                        navController = navController,
                        onDeleteCourse = courseViewModel::deleteCourse
                    )
                } // end of CourseItem
            }
        }
    }
}

@Composable
fun CourseItem(
    coursesState: CoursesState,
    index: Int,
    navController: NavHostController,
    onDeleteCourse: (CourseRecord) -> Unit
) {
    val courseRecord: CourseRecord = coursesState.courseRecords[index]
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .clickable {
                Log.d("VIN:", "CourseItem onItemClick navigate to Player Setup")
                navController.navigate(route = Screen.PlayerSetup.passId(courseRecord.mId))
            }
            .padding(2.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = courseRecord.mCoursename,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Normal
            )
            Spacer(modifier = Modifier.size(14.dp))
            IconButton(
                onClick = {
                    Log.d("VIN", "CourseDetail?id={${courseRecord.mId}}")
                    navController.navigate(route = Screen.DetailCourse.passId(courseRecord.mId))  // goto detail screen
                }
            ) {
                Icon(
                    imageVector = Icons.Rounded.Edit,
                    contentDescription = "Edit Course",
                    modifier = Modifier.size(35.dp),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
            IconButton(
                onClick = {
                    onDeleteCourse(courseRecord)
                }
            ) {
                Icon(
                    imageVector = Icons.Rounded.Delete,
                    contentDescription = "Delete Note",
                    modifier = Modifier.size(35.dp),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
            Spacer(modifier = Modifier.size(14.dp))

        }
    }
}
