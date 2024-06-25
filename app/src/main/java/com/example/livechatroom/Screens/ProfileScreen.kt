package com.example.livechatroom.Screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.livechatroom.CommonDivider
import com.example.livechatroom.CommonImage
import com.example.livechatroom.CommonProgressBar
import com.example.livechatroom.DestinationScreen
import com.example.livechatroom.LCviewModel
import com.example.livechatroom.NavigateTo
import com.example.livechatroom.R

@Composable
fun ProfileScreen(navController: NavController, vm: LCviewModel) {
    val in_Progress = vm.inprogres.value

    Box(modifier = Modifier.fillMaxSize()) {
        if (in_Progress) {
            CommonProgressBar()
        } else {
            val userdata = vm.userData.value
            var name by rememberSaveable {
                mutableStateOf(userdata?.name ?: "")
            }
            var number by rememberSaveable {
                mutableStateOf(userdata?.number ?: "")
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                ProfileContent(
                    modifier = Modifier
                        .weight(1f)
                        .padding(4.dp),
                    vm = vm,
                    Name = name,
                    Number = number,
                    onNameChange = { name = it },
                    onNumberChange = { number = it },
                    onBack = { navController.navigateUp() },
                    onSave = { vm.CreateorUpdateProfile(name, number) },
                    onLogOut = {
                        vm.logout()
                        NavigateTo(navController, DestinationScreen.Login.route)
                    },

                    )
            }
            BottomNavigationMenu(
                selecteditem = BottomNavigationMenu.PROFILE,
                navController = navController,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}

@Composable
fun ProfileContent(
    vm: LCviewModel, Name: String, Number: String,
    modifier: Modifier,
    onNameChange: (String) -> Unit,
    onNumberChange: (String) -> Unit,
    onLogOut: () -> Unit,

    onBack: () -> Unit,
    onSave: () -> Unit
) {
    val PimageURL = vm.userData.value?.imageURL
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .wrapContentHeight()
                .background(Color.White),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val isbackButtonSelected = remember {
                mutableStateOf(false)
            }
            val isSaveButtonSelected = remember {
                mutableStateOf(false)
            }

            IconButton(onClick = {
                onBack.invoke()
                isbackButtonSelected.value = !isbackButtonSelected.value
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = "Back Button", modifier = Modifier.size(35.dp),
                    tint = if (isbackButtonSelected.value) Color.Blue else Color.Gray
                )

            }
            IconButton(onClick = {
                onSave.invoke()
                isSaveButtonSelected.value = !isSaveButtonSelected.value
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.save_instagram),
                    contentDescription = "Save Button", modifier = Modifier.size(35.dp),
                    tint = if (isSaveButtonSelected.value) Color.Blue else Color.Gray
                )

            }
        }
        CommonDivider()
        ProfileImage(imageURL = PimageURL, vm = vm)
        CommonDivider()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Name", modifier = Modifier.width(100.dp))
            TextField(
                value = Name, onValueChange = onNameChange,
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent
                )
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Number", modifier = Modifier.width(100.dp))
            TextField(
                value = Number, onValueChange = onNumberChange,
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent
                )
            )
        }
        CommonDivider()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            TextButton(onClick = { onLogOut.invoke() }) {
                Text(text = "Logout")

            }

        }


    }


}


@Composable
fun ProfileImage(imageURL: String?, vm: LCviewModel) {
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                vm.UploadProfileImage(it)
            }
        }

    Box(modifier = Modifier.height(IntrinsicSize.Min)) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .clickable {
                    launcher.launch("image/*")
                }, horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                shape = CircleShape, modifier = Modifier
                    .padding(8.dp)
                    .size(100.dp)
            ) {
                CommonImage(data = imageURL)
            }
            Text(text = "Change Profile Picture")
        }
        if (vm.inprogres.value) {
            CommonProgressBar()
        }
    }
}


