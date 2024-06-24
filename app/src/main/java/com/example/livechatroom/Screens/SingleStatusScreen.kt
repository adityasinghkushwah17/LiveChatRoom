package com.example.livechatroom.Screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.livechatroom.CommonImage
import com.example.livechatroom.LCviewModel


enum class States {
    INITIAL, ACTIVE, COMPLETED
}

@Composable
fun SingleStatusScreen(navController: NavController, vm: LCviewModel, userid: String) {
    val status = vm.Status.value.filter {
        it.user.userID == userid
    }
    if (status.isNotEmpty()) {
        val currentstatus = remember {
            mutableStateOf(0)
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {
            CommonImage(
                data = status[currentstatus.value].imageURL,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )
            Row(modifier = Modifier.fillMaxWidth()) {
                status.forEachIndexed { index, statuselement ->
                    CustomProgressIndicator(
                        modifier = Modifier
                            .weight(1f)
                            .height(7.dp)
                            .padding(1.dp),
                        state = if (currentstatus.value < index) States.INITIAL else if (currentstatus.value == index) States.ACTIVE else States.COMPLETED
                    ) {
                        if(currentstatus.value< status.size -1) currentstatus.value++ else navController.popBackStack()
                    }


                }
            }
        }
    }
}

        @Composable
        fun CustomProgressIndicator(modifier: Modifier, state: States, onComplete: () -> Unit) {

            var progrss = if (state == States.INITIAL) 1f else 0f

            if (state == States.ACTIVE) {
                val toggleState = remember {
                    mutableStateOf(false)
                }
                LaunchedEffect(toggleState) {
                    toggleState.value = true

                }
                val p: Float by animateFloatAsState(
                    if (toggleState.value) 1f else 0f,
                    animationSpec = tween(5000),
                    finishedListener = { onComplete.invoke() })
                progrss = p
            }
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth(), color = Color.Red, progress = { progrss })


        }