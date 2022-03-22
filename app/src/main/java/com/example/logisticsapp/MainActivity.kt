package com.example.logisticsapp

import NotificationMessage
import SignUpScreen
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.logisticsapp.ui.theme.LogisticsAppTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.processor.internal.definecomponent.codegen._dagger_hilt_android_components_ViewModelComponent

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LogisticsAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    logisticApp()
                }
            }
        }
    }
}

sealed class DestinationScreen(val route: String){
    object SignUp: DestinationScreen("signup")
}

@Composable
fun logisticApp(){
    val vm = hiltViewModel<LogisticViewModel>()
    val navController = rememberNavController()
    NotificationMessage(vm = vm)
    NavHost(navController = navController, startDestination = DestinationScreen.SignUp.route){
        composable(DestinationScreen.SignUp.route){
            SignUpScreen(navController = navController, vm = vm)
        }
    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LogisticsAppTheme {
        logisticApp()
    }
}