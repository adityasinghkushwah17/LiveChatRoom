package com.example.livechatroom.Screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.ui.text.TextStyle
import androidx.navigation.NavController
import com.example.livechatroom.DestinationScreen
import com.example.livechatroom.LCviewModel
import com.example.livechatroom.NavigateTo
import com.example.livechatroom.R
import com.example.livechatroom.checkSignin

@Composable
fun First_page(navController: NavController,vm: LCviewModel) {

    checkSignin(vm, navController)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = Color(android.graphics.Color.parseColor("#f8eeec")))
    ) {
        Image(
            painter = painterResource(id = R.drawable.top_background2),
            contentDescription = null, contentScale = ContentScale.Fit
        )
        Text(
            text = "Stay Connected\nwith your \nFriends and Family",
            color = Color(android.graphics.Color.parseColor("#3b608c")),
            modifier = Modifier.padding(top = 16.dp, start = 24.dp),
            fontSize = 40.sp,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.SemiBold,
            style = TextStyle(lineHeight = 38.sp)

        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, start = 24.dp, end = 24.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.shield),
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 8.dp, end = 4.dp)
                    .size(35.dp)
            )
            Text(
                text = "Secure,private Messaging ",
                color = Color(android.graphics.Color.parseColor("#3b608c")),
                modifier = Modifier.padding(start = 4.dp, top = 4.dp),
                fontSize = 22.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.SemiBold,
                style = TextStyle(lineHeight = 32.sp)

            )
        }

        Spacer(modifier = Modifier.height(85.dp))



        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, start = 24.dp, end = 24.dp)
        ) {
            Button(
                onClick = { NavigateTo(navController,DestinationScreen.Signup.route) },
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 10.dp, end = 8.dp)
                    .weight(0.5f)
                    .height(55.dp),
                border = BorderStroke(
                    1.dp,
                    Color(android.graphics.Color.parseColor("#4d4d4d"))
                ),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
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
                        text = "Get Started ",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(android.graphics.Color.parseColor("#2f4f86"))
                    )
                }

            }


        }

    }
}