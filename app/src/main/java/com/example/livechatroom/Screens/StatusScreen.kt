package com.example.livechatroom.Screens

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

    val inprocess = vm.inprogressStatus.value
    if (inprocess) {
        CommonProgressBar()
    } else {
        val statuses = vm.Status.value
        val userdata = vm.userData.value
        val mystatus = statuses.filter {
            it.user.userID == userdata?.userID
        }
        val otherstatus = statuses.filter {
            it.user.userID != userdata?.userID
        }
        val launcher =
            rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) {
          uri ->
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
            }, content = {
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
                        if (mystatus.isNotEmpty()) {
                            CommonRow(
                                imageurl = mystatus[0].user.imageURL,
                                name = mystatus[0].user.name
                            ) {
                                NavigateTo(
                                    navController,
                                    DestinationScreen.SingleStatus.createRoute(mystatus[0].user.userID!!)
                                )
                            }
                            CommonDivider()
                            val uniqueuser = otherstatus.map { it.user }.toSet().toList()
                            LazyColumn(modifier = Modifier.weight(1f)) {
                                items(uniqueuser) { user ->
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
            })

    }


}


@Composable
fun FABs(onFabClick: () -> Unit) {
    FloatingActionButton(
        onClick = { onFabClick.invoke() },
        containerColor = MaterialTheme.colorScheme.secondary,
        modifier = Modifier.padding(30.dp)
    ) {
        Icon(imageVector = Icons.Rounded.Edit, contentDescription = "Add status")
    }
}