package com.example.livechatroom.Screens


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.livechatroom.CommonProgressBar
import com.example.livechatroom.CommonRow
import com.example.livechatroom.DestinationScreen
import com.example.livechatroom.LCviewModel
import com.example.livechatroom.NavigateTo
import com.example.livechatroom.TitleText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatListScreen(navController: NavController, vm: LCviewModel) {
    val inProgress = vm.inprogresChats.value

    Box(modifier = Modifier.fillMaxSize()) {
        if (inProgress) {
            CommonProgressBar()
        } else {
            val chats = vm.Chats.value
            val userdata = vm.userData.value
            val showDialog = remember { mutableStateOf(false) }
            val onFabClick: () -> Unit = { showDialog.value = true }
            val onDismiss: () -> Unit = { showDialog.value = false }
            val onAddChat: (String) -> Unit = {
                vm.onAddChat(it)
                showDialog.value = false
            }

            Scaffold(
                floatingActionButton = {
                    FAB(
                        showDialog = showDialog.value,
                        onFabClick = onFabClick,
                        onDismiss = onDismiss,
                        onAddChat = onAddChat
                    )
                },
                content = { paddingValues ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                    ) {
                        TitleText(
                            text = "Chats",

                        )
                        if (chats.isEmpty()) {
                            EmptyChatListPlaceholder()
                        } else {
                            LazyColumn(
                                modifier = Modifier.weight(1f),
                                contentPadding = PaddingValues(vertical = 8.dp)
                            ) {
                                items(chats) { chat ->
                                    val chatuser = if (chat.user1.userID == userdata?.userID) chat.user2 else chat.user1
                                    CommonRow(
                                        imageurl = chatuser.imageURL,
                                        name = chatuser.name
                                    ) {
                                        chat.chatID?.let {
                                            NavigateTo(navController, DestinationScreen.SingleChat.createRoute(it))
                                        }
                                    }
                                }
                            }
                        }
                    }
                },
                bottomBar = {
                    BottomNavigationMenu(
                        BottomNavigationMenu.CHATLIST,
                        navController,
                        modifier = Modifier.fillMaxWidth()
                    )
                }, containerColor =Color(android.graphics.Color.parseColor("#f8eeec"))
            )
        }
    }
}

@Composable
fun EmptyChatListPlaceholder() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "No Chats Available",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
    }
}

@Composable
fun FAB(
    showDialog: Boolean,
    onFabClick: () -> Unit,
    onDismiss: () -> Unit,
    onAddChat: (String) -> Unit,
) {
    val addChatNumber = remember { mutableStateOf("") }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                onDismiss()
                addChatNumber.value = ""
            },
            confirmButton = {
                Button(onClick = { onAddChat(addChatNumber.value) }) {
                    Text(text = "Add")
                }
            },
            title = { Text(text = "Add Chat", style = MaterialTheme.typography.titleLarge) },
            text = {
                OutlinedTextField(
                    value = addChatNumber.value,
                    onValueChange = { addChatNumber.value = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    label = { Text(text = "Enter the phone number") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        )
    }

    FloatingActionButton(
        onClick = onFabClick,
        containerColor = Color(android.graphics.Color.parseColor("#E88910")),
        modifier = Modifier.padding(16.dp)
    ) {
        Icon(
            imageVector = Icons.Rounded.Add,
            contentDescription = "Add Chat",
            tint = Color.White
        )
    }
}
