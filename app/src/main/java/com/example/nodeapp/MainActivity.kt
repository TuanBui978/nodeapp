package com.example.nodeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nodeapp.ui.theme.NodeappTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NodeappTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting(name = "aaaa")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(modifier = Modifier.fillMaxSize(), navController = navController, startDestination = "Login") {
        composable("Login") {
            LoginForm(modifier = Modifier
                .padding(horizontal = 10.dp), onNavigation = {
                navController.navigate("Sign Up")
            }, toHome = {
                navController.navigate("Home")
            })
        }
        composable("Sign Up") {
            RegisterScreen(modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp), onNavigation = {
                navController.navigate("Login")
            }, toHome = {
                navController.navigate("Home")
            })
        }
        composable("Home") {
            HomeScreen(modifier =  Modifier.fillMaxSize(), )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NodeappTheme {
        Greeting("Android")
    }
}