package com.hmyh.moviecompose.ui.navagation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hmyh.moviecompose.ui.screen.DetailsScreen
import com.hmyh.moviecompose.ui.screen.HomeScreen

@Composable
fun MovieNavigation(){
    val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination = MovieScreens.HomeScreen.name){
        composable(MovieScreens.HomeScreen.name) {
            HomeScreen(navController)
        }
        composable(MovieScreens.DetailScreen.name) {
            DetailsScreen(navController)
        }
    }
}