package se.oscarb.quicko.core.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers.GREEN_DOMINATED_EXAMPLE
import androidx.compose.ui.unit.dp

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun QuickoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}


@Composable
@Preview(
    showBackground = true,
    wallpaper = GREEN_DOMINATED_EXAMPLE,
)
private fun Material3ColorsPreview() {
    val colors = MaterialTheme.colorScheme

    QuickoTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Material 3 Colors",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Primary colors
            ColorRow("Primary", colors.primary)
            ColorRow("On Primary", colors.onPrimary)
            ColorRow("Primary Container", colors.primaryContainer)
            ColorRow("On Primary Container", colors.onPrimaryContainer)
            ColorRow("Inverse Primary", colors.inversePrimary)

            // Secondary colors
            ColorRow("Secondary", colors.secondary)
            ColorRow("On Secondary", colors.onSecondary)
            ColorRow("Secondary Container", colors.secondaryContainer)
            ColorRow("On Secondary Container", colors.onSecondaryContainer)

            // Tertiary colors
            ColorRow("Tertiary", colors.tertiary)
            ColorRow("On Tertiary", colors.onTertiary)
            ColorRow("Tertiary Container", colors.tertiaryContainer)
            ColorRow("On Tertiary Container", colors.onTertiaryContainer)

            // Background and surface colors
            ColorRow("Background", colors.background)
            ColorRow("On Background", colors.onBackground)
            ColorRow("Surface", colors.surface)
            ColorRow("On Surface", colors.onSurface)
            ColorRow("Surface Variant", colors.surfaceVariant)
            ColorRow("On Surface Variant", colors.onSurfaceVariant)
            ColorRow("Inverse Surface", colors.inverseSurface)
            ColorRow("Inverse On Surface", colors.inverseOnSurface)
            ColorRow("Surface Bright", colors.surfaceBright)
            ColorRow("Surface Dim", colors.surfaceDim)
            ColorRow("Surface Container", colors.surfaceContainer)
            ColorRow("Surface Container Lowest", colors.surfaceContainerLowest)
            ColorRow("Surface Container Low", colors.surfaceContainerLow)
            ColorRow("Surface Container High", colors.surfaceContainerHigh)
            ColorRow("Surface Container Highest", colors.surfaceContainerHighest)
            ColorRow("Surface Tint", colors.surfaceTint)

            // Error colors
            ColorRow("Error", colors.error)
            ColorRow("On Error", colors.onError)
            ColorRow("Error Container", colors.errorContainer)
            ColorRow("On Error Container", colors.onErrorContainer)

            // Outline and scrim
            ColorRow("Outline", colors.outline)
            ColorRow("Outline Variant", colors.outlineVariant)
            ColorRow("Scrim", colors.scrim)
        }
    }

}

@Composable
fun ColorRow(label: String, color: Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(color = color, shape = RoundedCornerShape(8.dp))
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}