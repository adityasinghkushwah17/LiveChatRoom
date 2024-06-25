package com.example.livechatroom.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
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
            .fillMaxSize()
            .padding(8.dp)
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.drawable.basic_ui__727_),
            contentDescription = null, modifier = Modifier
                .width(200.dp)
                .padding(top = 16.dp)
                .padding(8.dp).size(200.dp),
            contentScale = ContentScale.Fit
        )
        Text(
            text = "Forget Password!!",
            modifier = Modifier.padding(top = 4.dp),
            color = Color.Black,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,

            fontFamily = FontFamily.Serif
            )

        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "Enter your Registered email") },
            modifier = Modifier,
            placeholder = {}
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            vm.SendPasswordResetEmail(email.text) { success, message ->
                isSuccess = success
                Message = message

                if (isSuccess) {
                    navController.navigate(DestinationScreen.Login.route)
                }
            }


        }, modifier = Modifier, colors = ButtonDefaults.buttonColors(Color.Blue)) {
            Text(text = "Reset Password")
        }
        Spacer(modifier = Modifier.height(16.dp))

        if (Message.isNotEmpty()) {
            Text(text = Message)
        }

        TextButton(onClick = { navController.navigateUp() }) {
            Text(text = "Go Back to Login", fontSize = 16.sp, color = Color.Blue)

        }

    }

}


