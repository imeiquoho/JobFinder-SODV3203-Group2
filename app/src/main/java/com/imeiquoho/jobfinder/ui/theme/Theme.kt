package com.imeiquoho.jobfinder.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = Evergreen,
    onPrimary = SoftIvory,
    primaryContainer = MintCream,
    onPrimaryContainer = Ink,
    secondary = SunlitGold,
    onSecondary = Ink,
    secondaryContainer = WarmSand,
    onSecondaryContainer = Ink,
    background = Linen,
    onBackground = Ink,
    surface = SoftIvory,
    onSurface = Ink,
    surfaceVariant = Mist,
    onSurfaceVariant = Slate
)

private val DarkColors = darkColorScheme(
    primary = SunlitGold,
    onPrimary = Ink,
    primaryContainer = DeepForest,
    onPrimaryContainer = SoftIvory,
    secondary = Seafoam,
    onSecondary = Ink,
    secondaryContainer = CharcoalBlue,
    onSecondaryContainer = SoftIvory,
    background = Night,
    onBackground = SoftIvory,
    surface = MidnightSurface,
    onSurface = SoftIvory,
    surfaceVariant = CharcoalBlue,
    onSurfaceVariant = PaleSlate
)

@Composable
fun JobFinderTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = AppTypography,
        content = content
    )
}
