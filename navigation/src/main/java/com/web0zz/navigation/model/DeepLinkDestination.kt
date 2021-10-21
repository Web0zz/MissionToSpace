package com.web0zz.navigation.model

sealed class DeepLinkDestination(val address: String) {
    object HomeModule : DeepLinkDestination("web0zz://home")
    object DetailModule : DeepLinkDestination("web0zz://detail")
    object CountdownModule : DeepLinkDestination("web0zz://countdown")
}