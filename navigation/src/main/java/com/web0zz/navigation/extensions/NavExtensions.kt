package com.web0zz.navigation.extensions

import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavOptions
import com.web0zz.navigation.model.DeepLinkDestination

fun buildDeepLinkRequest(destination: DeepLinkDestination) =
    NavDeepLinkRequest.Builder
        .fromUri(destination.address.toUri())
        .build()

fun NavController.deepLinkNavigateTo(
    deepLinkDestination: DeepLinkDestination,
    popUpTo: Boolean = false
) {
    val builder = NavOptions.Builder()

    if (popUpTo) builder.setPopUpTo(graph.startDestination, true)

    navigate(
        buildDeepLinkRequest(deepLinkDestination),
        builder.build()
    )
}
