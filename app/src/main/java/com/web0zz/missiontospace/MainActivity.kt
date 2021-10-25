package com.web0zz.missiontospace

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.web0zz.navigation.Navigator
import com.web0zz.navigation.extensions.ToFlowNavigatable
import com.web0zz.navigation.model.NavigationFlow

class MainActivity : AppCompatActivity(), ToFlowNavigatable {
    private val navigator: Navigator = Navigator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
            ?.findNavController()

        navController?.let {
            navigator.navController = navController
        }
    }

    override fun navigateToFlow(flow: NavigationFlow) {
        navigator.navigateToFlow(flow)
    }
}