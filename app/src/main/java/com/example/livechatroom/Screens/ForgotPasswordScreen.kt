package com.example.livechatroom.Screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.livechatroom.DestinationScreen
import com.example.livechatroom.LCviewModel
import com.example.livechatroom.R

@Composable
fun ForgotPasswordScreen(navController: NavController, vm: LCviewModel) {

    var email by remember { mutableStateOf(TextFieldValue()) }
    var Message by remember { mutableStateOf("") }
    var isSuccess by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = Color(android.graphics.Color.parseColor("#f8eeec")))
    ) {
        Image(
            painter = painterResource(id = R.drawable.top_background1),
            contentDescription = null, contentScale = ContentScale.Fit
        )
        Text(
            text = "Forget\nPassword",
            color = Color(android.graphics.Color.parseColor("#3b608c")),
            modifier = Modifier.padding(top = 16.dp, start = 24.dp),
            fontSize = 40.sp,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.SemiBold,
                    style = TextStyle(lineHeight = 40.sp)

        )

        TextField(
            value = email, onValueChange = { email = it },
            leadingIcon = {
                Image(
                    painter = painterResource(id = R.drawable.email),
                    contentDescription = null,
                    modifier = Modifier
                        .size(43.dp)
                        .padding(start = 6.dp)
                        .padding(3.dp)
                )
            }, shape = RoundedCornerShape(10.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = Color.White,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                textColor = Color(android.graphics.Color.parseColor("#5e5e5e")),
                unfocusedLabelColor = Color(android.graphics.Color.parseColor("#5e5e5e"))

            ), label = { Text(text = "Email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 24.dp, end = 24.dp)
                .background(Color.White, CircleShape)
        )




        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, start = 24.dp, end = 24.dp)
        ) {
            androidx.compose.material.Button(
                onClick = {
                    vm.SendPasswordResetEmail(email.text) { success, message ->
                        isSuccess = success
                        Message = message

                        if (isSuccess) {
                            navController.navigate(DestinationScreen.Login.route)
                        }
                    }
                },
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 10.dp, end = 8.dp)
                    .weight(0.5f)
                    .height(55.dp),
                border = BorderStroke(
                    1.dp,
                    Color(android.graphics.Color.parseColor("#4d4d4d"))
                ),
                colors = androidx.compose.material.ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(start = 14.dp)
                        .weight(0.7f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Send Reset Email !!",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(android.graphics.Color.parseColor("#2f4f86"))
                    )
                }

            }


        }
        Spacer(modifier = Modifier.height(16.dp))
        if (Message.isNotEmpty()) {
            Text(text = Message)
        }
        Image(painter = painterResource(id = R.drawable.arrows), contentDescription = null,
            modifier = Modifier
                .width(80.dp)
                .padding(top = 16.dp, start = 14.dp)
                .align(Alignment.Start)
                .padding(end = 24.dp)
                .size(50.dp)
                .clickable { navController.navigateUp() })

    }

}


