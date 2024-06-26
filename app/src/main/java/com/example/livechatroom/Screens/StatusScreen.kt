package com.example.livechatroom.Screens

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.livechatroom.CommonDivider
import com.example.livechatroom.CommonProgressBar
import com.example.livechatroom.CommonRow
import com.example.livechatroom.DestinationScreen
import com.example.livechatroom.LCviewModel
import com.example.livechatroom.NavigateTo
import com.example.livechatroom.TitleText
@Composable
fun StatusScreen(navController: NavController, vm: LCviewModel) {
    val inProcess = vm.inprogressStatus.value

    if (inProcess) {
        CommonProgressBar()
    } else {
        val statuses = vm.Status.value ?: emptyList()
        val userData = vm.userData.value

        // Debug logs for filtered statuses
        Log.d("My statuses:", "$statuses")
        Log.d("User data:", "$userData")

        val myStatus = statuses.filter { it.user.userID == userData?.userID }
        val otherStatus = statuses.filter { it.user.userID != userData?.userID }

        // Debug logs for filtered statuses
        Log.d("My statuses:", "$myStatus")
        Log.d("Other statuses:", "$otherStatus")

        val launcher =
            rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
                uri?.let {
                    vm.uploadStatus(uri)
                }
            }

        Scaffold(
            floatingActionButton = {
                FABs {
                    launcher.launch("image/*")
                }
            },
            bottomBar = {
                BottomNavigationMenu(
                    BottomNavigationMenu.STATUS,
                    navController,
                    modifier = Modifier
                )
            },
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                ) {
                    TitleText(text = "Status")

                    if (statuses.isEmpty()) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(text = "No Status Available")
                        }
                    } else {
                        if (myStatus.isNotEmpty()) {
                            CommonRow(
                                imageurl = myStatus[0].user.imageURL,
                                name = myStatus[0].user.name
                            ) {
                                NavigateTo(
                                    navController,
                                    DestinationScreen.SingleStatus.createRoute(myStatus[0].user.userID!!)
                                )
                            }
                            CommonDivider()
                        }

                        val uniqueUsers = otherStatus.map { it.user }.toSet().toList()
                        LazyColumn(modifier = Modifier.weight(1f)) {
                            items(uniqueUsers) { user ->
                                CommonRow(imageurl = user.imageURL, name = user.name) {
                                    NavigateTo(
                                        navController,
                                        DestinationScreen.SingleStatus.createRoute(user.userID!!)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        )
    }
}

@Composable
fun FABs(onFabClick: () -> Unit) {
    FloatingActionButton(
        onClick = { onFabClick.invoke() },
        containerColor = Color(android.graphics.Color.parseColor("#E88910")),
        modifier = Modifier.padding(30.dp)
    ) {
        Icon(imageVector = Icons.Rounded.Edit, contentDescription = "Add status", tint = Color.White)
    }
}
