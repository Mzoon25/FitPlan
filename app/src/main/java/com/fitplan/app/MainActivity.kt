package com.fitplan.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.fitplan.app.navigation.AppNavHost
import com.fitplan.app.ui.DemoFitPlanState
import com.fitplan.app.ui.theme.FitPlanTheme

class MainActivity : ComponentActivity() {
    private val state: DemoFitPlanState by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FitPlanTheme {
                AppNavHost(state)
            }
        }
    }
}
