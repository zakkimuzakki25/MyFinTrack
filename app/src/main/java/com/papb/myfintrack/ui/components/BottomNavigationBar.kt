package com.papb.myfintrack.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.material3.*

@Composable
fun BottomNavigationBar(selectedTab: String, onTabSelected: (String) -> Unit) {
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Dashboard") },
            label = { Text("Dashboard") },
            selected = selectedTab == "main",
            onClick = { onTabSelected("main") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.List, contentDescription = "Recap") },
            label = { Text("Recap") },
            selected = selectedTab == "recap",
            onClick = { onTabSelected("recap") }
        )
    }
}
