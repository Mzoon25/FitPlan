package com.fitplan.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val HeaderGreen = Color(0xFF1B5E20)
private val FitPlanColors = lightColorScheme(
    primary = Color(0xFF2E7D32),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFE8F5E9),
    onPrimaryContainer = HeaderGreen,
    secondaryContainer = Color(0xFFE8F5E9),
    onSecondaryContainer = HeaderGreen,
    error = Color(0xFFC62828),
    background = Color(0xFFF5F5F5),
    surface = Color.White,
)

private val defaults = Typography()
private val FitPlanTypography = Typography(
    titleLarge = defaults.titleLarge.copy(fontSize = 22.sp, fontWeight = FontWeight.Bold),
    titleMedium = defaults.titleMedium.copy(fontSize = 16.sp, fontWeight = FontWeight.Bold),
    headlineMedium = defaults.headlineMedium.copy(fontSize = 36.sp, fontWeight = FontWeight.Bold)
)

@Composable
fun FitPlanTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = FitPlanColors, typography = FitPlanTypography, content = content)
}
