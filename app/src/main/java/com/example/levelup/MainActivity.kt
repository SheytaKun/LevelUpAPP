package com.example.levelup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavHostController
import com.example.levelup.navigation.AppNav

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val nav: NavHostController = rememberNavController()

            androidx.compose.runtime.LaunchedEffect(nav) {
                android.util.Log.d("NAV", "MainActivity nav=${System.identityHashCode(nav)}")
            }

            AppNav(nav)
        }
    }
}