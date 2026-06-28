package com.example.bkashclone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bkashclone.screens.HomeScreen
import com.example.bkashclone.screens.LoginScreen
import com.example.bkashclone.screens.SignupScreen
import com.example.bkashclone.ui.theme.BkashCloneTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BkashCloneTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "signup"
                    ) {
                        composable("signup") {
                            SignupScreen(
                                onSignup = { navController.navigate("login") },
                                onLogin  = { navController.navigate("login") }
                            )
                        }
                        composable("login") {
                            LoginScreen(
                                onNext   = { navController.navigate("home") },
                                onSignup = { navController.navigate("signup") }
                            )
                        }
                        composable("home") {
                            HomeScreen()
                        }
                    }
                }
            }
        }
    }
}