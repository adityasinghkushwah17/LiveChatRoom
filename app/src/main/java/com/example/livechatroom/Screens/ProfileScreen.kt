package com.example.livechatroom.Screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.livechatroom.CommonImage
import com.example.livechatroom.CommonProgressBar
import com.example.livechatroom.DestinationScreen
import com.example.livechatroom.LCviewModel
import com.example.livechatroom.NavigateTo
import com.example.livechatroom.R
import com.example.livechatroom.TitleText

@Composable
fun ProfileScreen(navController: NavController, vm: LCviewModel) {
    val inProgress = vm.inprogres.value

    Box(modifier = Modifier.fillMaxSize()) {
        if (inProgress) {
            CommonProgressBar()
        } else {
            val userdata = vm.userData.value
            var name by rememberSaveable { mutableStateOf(userdata?.name ?: "") }
            var number by rememberSaveable { mutableStateOf(userdata?.number ?: "") }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                TitleText(text = "Profile")
                ProfileContent(
                    modifier = Modifier
                        .weight(1f)
                        .padding(12.dp),
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
                    }
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
    val pImageURL = vm.userData.value?.imageURL
    Column(modifier = modifier) {
        ProfileTopBar(onBack, onSave)
        Spacer(modifier = Modifier.height(16.dp))
        ProfileImage(imageURL = pImageURL, vm = vm)
        Spacer(modifier = Modifier.height(24.dp))
        ProfileTextField("Name", Name, onNameChange)
        Spacer(modifier = Modifier.height(16.dp))
        ProfileTextField("Number", Number, onNumberChange)
        Spacer(modifier = Modifier.height(24.dp))
        LogoutButton(onLogOut)
    }
}

@Composable
fun ProfileTopBar(onBack: () -> Unit, onSave: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBack) {
            Icon(
                painter = painterResource(id = R.drawable.arrows),
                contentDescription = "Back",
                modifier = Modifier.size(28.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        }
        IconButton(onClick = onSave) {
            Icon(
                painter = painterResource(id = R.drawable.bookmark),
                contentDescription = "Save",
                modifier = Modifier.size(28.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun ProfileImage(imageURL: String?, vm: LCviewModel) {
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
            uri?.let { vm.UploadProfileImage(it) }
        }

    Box(modifier = Modifier.height(IntrinsicSize.Min)) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .clickable { launcher.launch("image/*") },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                shape = CircleShape,
                modifier = Modifier
                    .size(120.dp)
                    .padding(8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                CommonImage(data = imageURL)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Change Profile Picture",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
        if (vm.inprogres.value) {
            CommonProgressBar()
        }
    }
}

@Composable
fun ProfileTextField(label: String, value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurface
        )
    )
}

@Composable
fun LogoutButton(onLogOut: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {


        Button(
            onClick = onLogOut,
            modifier = Modifier,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(
                    android.graphics.Color.parseColor(
                        "#E88910"
                    )
                ),
            )

        ) {
            Text("Logout", color = MaterialTheme.colorScheme.onError,fontSize = 16.sp,
                fontWeight = FontWeight.Bold)
        }
    }
}