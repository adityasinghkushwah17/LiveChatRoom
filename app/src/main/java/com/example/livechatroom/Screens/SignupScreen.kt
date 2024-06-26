package com.example.livechatroom.Screens

import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.livechatroom.CommonProgressBar
import com.example.livechatroom.DestinationScreen
import com.example.livechatroom.LCviewModel
import com.example.livechatroom.NavigateTo
import com.example.livechatroom.R
import com.example.livechatroom.checkSignin
import com.example.livechatroom.rememberImeState

@Composable
fun SignupScreen(navController: NavController, vm: LCviewModel) {

    checkSignin(vm, navController)


    val Name = remember { mutableStateOf(TextFieldValue()) }
    val Number = remember { mutableStateOf(TextFieldValue()) }
    val Email = remember { mutableStateOf(TextFieldValue()) }
    val Password = remember { mutableStateOf(TextFieldValue()) }
    val passwordVisibility = remember { mutableStateOf(false) }
    val icon =
        if (passwordVisibility.value) painterResource(id = R.drawable.baseline_visibility_24) else painterResource(
            id = R.drawable.baseline_visibility_off_24
        )
    val imeState = rememberImeState()
    val scrollState = rememberScrollState()

    LaunchedEffect(key1 = imeState.value) {
        if (imeState.value) {
            scrollState.animateScrollTo(scrollState.maxValue, tween(300))
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(scrollState)
            .background(color = Color(android.graphics.Color.parseColor("#f8eeec")))
    ) {
        Image(
            painter = painterResource(id = R.drawable.top_background2),
            contentDescription = null, contentScale = ContentScale.Fit
        )
        Text(
            text = "Sign Up !!",
            color = Color(android.graphics.Color.parseColor("#3b608c")),
            modifier = Modifier.padding(top = 16.dp, start = 24.dp),
            fontSize = 35.sp,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.SemiBold

        )

        TextField(
            value = Name.value, onValueChange = { Name.value = it },
            leadingIcon = {
                Image(
                    painter = painterResource(id = R.drawable.name),
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

            ), label = { Text(text = "Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 24.dp, end = 24.dp)
                .background(Color.White, CircleShape)
        )

        TextField(
            value = Number.value, onValueChange = { Number.value = it },
            leadingIcon = {
                Image(
                    painter = painterResource(id = R.drawable.ic_2),
                    contentDescription = null,
                    modifier = Modifier
                        .size(43.dp)
                        .padding(start = 6.dp)
                        .padding(6.dp)
                )
            }, shape = RoundedCornerShape(10.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = Color.White,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                textColor = Color(android.graphics.Color.parseColor("#5e5e5e")),
                unfocusedLabelColor = Color(android.graphics.Color.parseColor("#5e5e5e"))

            ), label = { Text(text = "Number") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),

            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 24.dp, end = 24.dp)
                .background(Color.White, CircleShape)
        )

        TextField(
            value = Email.value, onValueChange = { Email.value = it },
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

        TextField(
            value = Password.value, onValueChange = { Password.value = it },
            leadingIcon = {
                Image(
                    painter = painterResource(id = R.drawable.password),
                    contentDescription = null,
                    modifier = Modifier
                        .size(43.dp)
                        .padding(start = 6.dp)
                        .padding(6.dp)
                )
            }, trailingIcon = {
                IconButton(onClick = { passwordVisibility.value = !passwordVisibility.value }) {
                    Icon(
                        painter = icon,
                        contentDescription = "Visibilty Icon"
                    )

                }
            },
            visualTransformation = if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation(),
            shape = RoundedCornerShape(10.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = Color.White,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                textColor = Color(android.graphics.Color.parseColor("#5e5e5e")),
                unfocusedLabelColor = Color(android.graphics.Color.parseColor("#5e5e5e"))

            ), label = { Text(text = "Password") },
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
                    vm.Signup(
                        Name.value.text, Number.value.text,
                        Email.value.text, Password.value.text
                    )
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
                Text(
                    text = "Create My Account",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(android.graphics.Color.parseColor("#2f4f86")),
                    modifier = Modifier
                        .padding(horizontal = 2.dp)
                        .fillMaxWidth()
                )
            }
            androidx.compose.material.Button(
                onClick = { NavigateTo(navController, DestinationScreen.Login.route) },
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
                Text(
                    text = "Sign in Page ",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(android.graphics.Color.parseColor("#2f4f86")),
                    modifier = Modifier
                        .padding(horizontal = 14.dp)
                        .fillMaxWidth()
                )
            }
        }


    }

    if (vm.inprogres.value) {
        CommonProgressBar()
    }
}
