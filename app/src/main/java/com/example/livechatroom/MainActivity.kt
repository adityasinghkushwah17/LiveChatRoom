package com.example.livechatroom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.livechatroom.Screens.ChatListScreen
import com.example.livechatroom.Screens.First_page
import com.example.livechatroom.Screens.ForgotPasswordScreen
import com.example.livechatroom.Screens.LoginScreen
import com.example.livechatroom.Screens.ProfileScreen
import com.example.livechatroom.Screens.SignupScreen
import com.example.livechatroom.Screens.SingleChatScreen
import com.example.livechatroom.Screens.SingleStatusScreen
import com.example.livechatroom.Screens.StatusScreen
import com.example.livechatroom.ui.theme.LiveChatRoomTheme
import dagger.hilt.android.AndroidEntryPoint


//Navigation Graph
sealed class DestinationScreen(var route: String){
    object Login : DestinationScreen("login_screen")
    object Signup : DestinationScreen("signup_screen")
    object Profile : DestinationScreen("profile_screen")
    object ChatList : DestinationScreen("chat_list_screen")
    object SingleChat : DestinationScreen("single_chat_screen/{chat_Id}"){
        fun createRoute(chat_Id: String) = "single_chat_screen/$chat_Id"
    }
    object StatusList : DestinationScreen("status_screen")
    object SingleStatus : DestinationScreen("single_status_screen/{status_Id}"){
        fun createRoute(status_Id: String) = "single_status_screen/$status_Id"
    }
    object ForgotPassword : DestinationScreen("forgot_password_screen")
    object FirstPage : DestinationScreen("first_page_screen")


}
@AndroidEntryPoint   //Hilt Entry Point
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LiveChatRoomTheme {
                Surface( modifier=Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background) {
                ChatAppNavigation()
            }
        }
    }

    }

    @Composable
    fun ChatAppNavigation() {

        val navController = rememberNavController()
        val vm=hiltViewModel<LCviewModel>()
        NavHost(navController = navController,
            startDestination =DestinationScreen.FirstPage.route )
        {
            composable(DestinationScreen.Signup.route){
                SignupScreen(navController,vm)
            }
            composable(DestinationScreen.Login.route){
                LoginScreen(navController,vm)
            }
            composable(DestinationScreen.Profile.route){
                ProfileScreen(navController,vm)
            }
            composable(DestinationScreen.ChatList.route){
                ChatListScreen(navController,vm)
            }
            composable(DestinationScreen.StatusList.route){
             StatusScreen(navController,vm)
            }
            composable(DestinationScreen.SingleChat.route){
                val chat_Id=it.arguments?.getString("chat_Id")
                chat_Id?.let {
                    SingleChatScreen(navController,vm,chat_Id)
                }

            }
            composable(DestinationScreen.SingleStatus.route){
                val user_id=it.arguments?.getString("status_Id")
                user_id?.let {
                    SingleStatusScreen(navController,vm,user_id)
                }


            }
            composable(DestinationScreen.ForgotPassword.route){
               ForgotPasswordScreen(navController,vm)
            }
            composable(DestinationScreen.FirstPage.route){
                First_page(navController,vm)
            }



        }
    }
}

