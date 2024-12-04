package com.papb.myfintrack.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun FinanceManagerAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = Color(0xFFFF9800),
            onPrimary = Color.White,
            secondary = Color(0xFFFF5722),
            onSecondary = Color.White,
            background = Color(0xFFF5F5F5),
            surface = Color.White,
            onBackground = Color.Black,
            onSurface = Color.Black,
            tertiary = Color(0xFFFFC107),
            onTertiary = Color.Black,
            error = Color(0xFFB00020),
            onError = Color.White,
            scrim = Color.Black.copy(alpha = 0.32f),
            outline = Color(0xFFBDBDBD),
            surfaceDim = Color(0x80FFFFFF),
            surfaceTint = Color(0xFFEEEEEE),
            onTertiaryContainer = Color.Black,
            surfaceContainerLow = Color(0xF4F4F4F4),
            inverseOnSurface = Color.White,
            onErrorContainer = Color.White,
            onSurfaceVariant = Color.Black,
            primaryContainer = Color(0xFFFFB74D),
            surfaceContainer = Color.White,
            onSecondaryContainer = Color.Black,
            surfaceContainerHigh = Color(0xFFEEEEEE),
            tertiaryContainer = Color(0xFFFFF3E0),
            onPrimaryContainer = Color.Black,
            secondaryContainer = Color(0xFFFFC107),
            errorContainer = Color(0xFFEF9A9A),
            inversePrimary = Color(0xFFF57C00),
            inverseSurface = Color(0xFF212121),
            outlineVariant = Color(0xFF757575),
            surfaceVariant = Color(0xFFEEEEEE),
            surfaceBright = Color(0xFFFAFAFA),
            surfaceContainerLowest = Color(0xFFF1F1F1),
            surfaceContainerHighest = Color(0xFFBDBDBD)
        ),
        typography = Typography,
        content = content
    )
}
