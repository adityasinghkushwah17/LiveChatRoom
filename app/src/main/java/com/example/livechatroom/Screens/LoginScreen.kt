package com.example.livechatroom.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.livechatroom.CommonProgressBar
import com.example.livechatroom.DestinationScreen
import com.example.livechatroom.LCviewModel
import com.example.livechatroom.NavigateTo
import com.example.livechatroom.R
import com.example.livechatroom.checkSignin


@Composable
fun LoginScreen( navController: NavController,vm: LCviewModel){

    val Email = remember { mutableStateOf(TextFieldValue()) }
    val Password = remember { mutableStateOf(TextFieldValue()) }

    checkSignin(vm,navController)
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)){
        Column(modifier = Modifier
            .fillMaxSize()
            .wrapContentHeight()
            .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Image(painter = painterResource(id = R.drawable.basic_ui__727_),
                contentDescription =null, modifier = Modifier
                    .width(200.dp)
                    .padding(top = 16.dp)
                    .padding(8.dp),
                contentScale = ContentScale.Fit)

            Text(text = "Sign In", modifier = Modifier.padding(8.dp), fontSize = 24.sp,
                fontFamily = FontFamily.Serif, fontWeight = FontWeight.Bold)

            OutlinedTextField(value = Email.value , onValueChange = { Email.value=it},label= { Text(text = "Email")},
                modifier = Modifier.padding(8.dp),keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
            OutlinedTextField(value = Password.value , onValueChange = { Password.value=it},label= { Text(text = "Password")},
                modifier = Modifier.padding(8.dp),keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password))


            Button(onClick = { vm.Login(Email.value.text,Password.value.text) }
                ,modifier = Modifier.padding(8.dp),
                colors= ButtonDefaults.buttonColors(Color.Blue)){
                Text(text = "Sign In")

            }

            Text(text = "New User? Go to Sign Up",
                fontSize = 16.sp,color= Color.Blue,modifier = Modifier.clickable
                { NavigateTo(navController, DestinationScreen.Signup.route) }) }




    }
    if(vm.inprogres.value){
        CommonProgressBar()
    }

}

