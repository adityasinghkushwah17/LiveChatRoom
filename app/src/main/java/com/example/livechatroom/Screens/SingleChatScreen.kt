package com.example.livechatroom.Screens

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.livechatroom.CommonDivider
import com.example.livechatroom.CommonImage
import com.example.livechatroom.Data.ChatUser
import com.example.livechatroom.Data.Message
import com.example.livechatroom.LCviewModel
import com.example.livechatroom.R

@Composable
fun SingleChatScreen(navController: NavController, vm: LCviewModel, chatid: String) {
    var reply by rememberSaveable { mutableStateOf("") }

    val myuser = vm.userData.value
    val curentChat = vm.Chats.value.first { it.chatID == chatid } // we will get current chat from this

    val chatuser = (if (myuser?.userID == curentChat.user1.userID) curentChat.user2 else curentChat.user1)
    val chatmessages=vm.ChatMessages.value
    Log.d("CHECKING OWN","own :${myuser?.userID ?: ""}")
    Log.d("CHECKING Other","oth :${chatuser}")
  //  Log.d("CCCCC","${vm.onSendReply(chatid, reply)}")
    LaunchedEffect(key1 = Unit) {
        vm.populateMessages(chatid)
    }
    BackHandler {
        vm.depopulatemessages()
    }
    Column {
        ChatHeader(name = chatuser.name ?: "---", imageUrl = vm.userData.value?.imageURL) {
            navController.navigateUp()
            vm.depopulatemessages()
        }
        MessageBox(
            modifier = Modifier.weight(1f),
            chatmessages = chatmessages,
            currentuserid = myuser?.userID ?: ""

        )
        ReplyBox(reply = reply, onReplyChange = { reply = it }, onSendReply = {
            vm.onSendReply(chatid, reply)
            reply = ""
        })
    }
}

@Composable
fun MessageBox(modifier: Modifier, chatmessages: List<Message>, currentuserid: String) {
    LazyColumn(modifier = modifier.fillMaxWidth()) {
        items(chatmessages) { msg ->
            val isCurrentUser = msg.sendby == currentuserid
            Log.d("MessageBox", "Message: ${msg.message}, sendby: ${msg.sendby}, IsCurrentUser: $isCurrentUser")
            val alignment = if (isCurrentUser) Alignment.End else Alignment.Start
            val messageColor = if (isCurrentUser)
                colorResource(id = R.color.light_green)
            else
                colorResource(id = R.color.grey_light)

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp, horizontal = 8.dp),
                horizontalAlignment = alignment
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(messageColor)
                        .padding(12.dp)
                ) {
                    Text(
                        text = msg.message ?: "",
                        color = colorResource(id = R.color.white),
                        fontWeight = FontWeight.Normal
                    )
                }
            }
        }
    }
}

@Composable
fun ChatHeader(name: String, imageUrl: String?, onBackClicked: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
            contentDescription = null,
            modifier = Modifier
                .clickable { onBackClicked.invoke() }
                .padding(8.dp)
        )
        CommonImage(
            data = imageUrl,
            modifier = Modifier
                .padding(8.dp)
                .size(50.dp)
                .clip(CircleShape)
        )
        Text(
            text = name,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Composable
fun ReplyBox(reply: String, onReplyChange: (String) -> Unit, onSendReply: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        CommonDivider()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextField(
                value = reply,
                onValueChange = { onReplyChange(it) },
                maxLines = 3,
                modifier = Modifier.weight(3f)
            )
            Button(onClick = { onSendReply() }, modifier = Modifier.weight(1f)) {
                Text(text = "Send")
               // Log.d("CCCCC","${onSendReply}")
            }
        }
    }
}
