package com.web0zz.navigation.extensions

import com.web0zz.navigation.model.NavigationFlow

interface ToFlowNavigatable {
    fun navigateToFlow(flow: NavigationFlow)
}