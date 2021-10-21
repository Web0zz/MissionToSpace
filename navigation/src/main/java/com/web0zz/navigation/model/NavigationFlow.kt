package com.web0zz.navigation.model

sealed class NavigationFlow {
    object HomeFlow : NavigationFlow()
    object DetailFlow : NavigationFlow()
    object CountdownFlow : NavigationFlow()
}