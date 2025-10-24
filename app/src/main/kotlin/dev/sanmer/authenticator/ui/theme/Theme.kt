package dev.sanmer.authenticator.ui.theme

import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext

@Composable
fun AppTheme(
    darkMode: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    SystemBarStyle(
        darkMode = darkMode
    )

    MaterialTheme(
        colorScheme = colorScheme(darkMode),
        shapes = Shapes,
        typography = Typography,
        content = content
    )
}

@Composable
private fun colorScheme(darkMode: Boolean): ColorScheme {
    val context = LocalContext.current
    
    // Dynamic color is available on Android 12+
    val supportsDynamicColor = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
    
    return when {
        supportsDynamicColor && darkMode -> dynamicDarkColorScheme(context)
        supportsDynamicColor && !darkMode -> dynamicLightColorScheme(context)
        darkMode -> darkColorScheme()
        else -> lightColorScheme()
    }
}

@Composable
private fun SystemBarStyle(
    darkMode: Boolean,
    statusBarScrim: Color = Color.Transparent,
    navigationBarScrim: Color = Color.Transparent
) {
    val context = LocalContext.current
    val activity = remember { context as ComponentActivity }

    SideEffect {
        activity.enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(
                statusBarScrim.toArgb(),
                statusBarScrim.toArgb(),
            ) { darkMode },
            navigationBarStyle = when {
                darkMode -> SystemBarStyle.dark(
                    navigationBarScrim.toArgb()
                )

                else -> SystemBarStyle.light(
                    navigationBarScrim.toArgb(),
                    navigationBarScrim.toArgb(),
                )
            }
        )
    }
}