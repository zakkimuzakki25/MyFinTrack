package com.papb.myfintrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.papb.myfintrack.ui.screens.MainScreen
import com.papb.myfintrack.ui.screens.RecapScreen
import com.papb.myfintrack.data.database.FinanceDatabase
import com.papb.myfintrack.data.repository.FinanceRepository
import com.papb.myfintrack.ui.components.BottomNavigationBar
import com.papb.myfintrack.ui.theme.FinanceManagerAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FinanceManagerAppTheme {
                val navController = rememberNavController()

                val appDatabase = FinanceDatabase.getDatabase(applicationContext)
                val financeRepository = FinanceRepository(appDatabase.financeDao())

                var selectedTab by remember { mutableStateOf("main") }

                Scaffold(
                    bottomBar = {
                        BottomNavigationBar(selectedTab = selectedTab, onTabSelected = { selectedTab = it })
                    }
                ) { paddingValues ->
                    NavHost(navController = navController, startDestination = selectedTab, modifier = Modifier.padding(paddingValues)) {
                        composable("main") {
                            MainScreen(financeRepository = financeRepository)
                        }
                        composable("recap") {
                            RecapScreen(financeRepository = financeRepository)
                        }
                    }
                }
            }
        }
    }
}