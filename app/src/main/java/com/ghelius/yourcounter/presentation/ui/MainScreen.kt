package com.ghelius.yourcounter.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ghelius.yourcounter.presentation.theme.YourcounterTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController, startDestination = NavigationItem.New.route) {
        composable(NavigationItem.Test.route) {
            TestScreen(koinViewModel())
        }
        composable(NavigationItem.New.route) {
            NewScreen()
        }
        composable(NavigationItem.History.route) {
            HistoryScreen()
        }
        composable(NavigationItem.Bindings.route) {
            SettingsScreen()
        }
    }
}

@Composable
fun MainScreen() {
    YourcounterTheme() {
        val navController = rememberNavController()
        Scaffold(
//                topBar = { TopBar() },
            bottomBar = { BottomNavigationBar(navController = navController) },
            content = { padding -> // We have to pass the scaffold inner padding to our content. That's why we use Box.
                Box(modifier = Modifier.padding(padding)) {
                    Navigation(navController = navController)
                }
            },
        )
    }
}


@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        NavigationItem.New,
        NavigationItem.History,
        NavigationItem.Bindings,
        NavigationItem.Test,
    )
    NavigationBar(
//            backgroundColor = colorResource(id = R.color.colorPrimary),
        contentColor = Color.White
    ) {
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(painterResource(id = item.icon), contentDescription = item.title) },
                label = { Text(text = item.title) },
//                    selectedContentColor = Color.White,
//                    unselectedContentColor = Color.White.copy(0.4f),
                alwaysShowLabel = true,
                selected = false,
                onClick = {
                    navController.navigate(item.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                }
            )
        }
    }
}