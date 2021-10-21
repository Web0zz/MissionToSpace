package com.web0zz.navigation

import androidx.navigation.NavController
import com.web0zz.navigation.model.NavigationFlow

class Navigator {
    lateinit var navController: NavController

    fun navigateToFlow(navigationFlow: NavigationFlow) = when (navigationFlow) {
        NavigationFlow.HomeFlow ->
            navController.navigate(MainNavGraphDirections.actionGlobalHomeNav())
        NavigationFlow.DetailFlow ->
            navController.navigate(MainNavGraphDirections.actionGlobalDetailNav())
        NavigationFlow.CountdownFlow ->
            navController.navigate(MainNavGraphDirections.actionGlobalCountdownNav())
    }
}