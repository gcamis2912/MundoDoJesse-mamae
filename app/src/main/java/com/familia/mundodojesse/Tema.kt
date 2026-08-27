package com.familia.mundodojesse

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val Grape = Color(0xFF8B5FBF)
val GrapeDeep = Color(0xFF6A3FA0)
val Coral = Color(0xFFFF6F61)
val Sun = Color(0xFFFFC93C)
val SunDeep = Color(0xFFF0A800)
val Leaf = Color(0xFF4CAF7D)
val Sky = Color(0xFF6EC6FF)
val Pink = Color(0xFFFF9EC7)
val Ink = Color(0xFF3B3450)
val Paper = Color(0xFFFDFBF6)
val Cream = Color(0xFFFFF6E9)

val GradienteFundoPrincipal = Brush.verticalGradient(colors = listOf(Grape, GrapeDeep))
val GradienteCoral = Brush.linearGradient(colors = listOf(Coral, Color(0xFFE85A4E)))
val GradienteLeaf = Brush.linearGradient(colors = listOf(Leaf, Color(0xFF3A9A68)))
val GradienteSky = Brush.linearGradient(colors = listOf(Sky, Color(0xFF4FA8E8)))
val GradientePink = Brush.linearGradient(colors = listOf(Pink, Color(0xFFE874AC)))
val GradienteSun = Brush.linearGradient(colors = listOf(Sun, SunDeep))
val GradienteGrape = Brush.linearGradient(colors = listOf(Grape, GrapeDeep))

private val CoresApp = lightColorScheme(
    primary = Grape, secondary = Coral, tertiary = Sun,
    background = Paper, surface = Paper, onPrimary = Paper, onBackground = Ink, onSurface = Ink
)

private val TipografiaApp = Typography(
    headlineLarge = TextStyle(fontWeight = FontWeight.Bold, fontSize = 34.sp, color = Ink),
    headlineMedium = TextStyle(fontWeight = FontWeight.Bold, fontSize = 26.sp, color = Ink),
    bodyLarge = TextStyle(fontWeight = FontWeight.Normal, fontSize = 20.sp, color = Ink),
    labelLarge = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 18.sp, color = Ink)
)

@Composable
fun MundoDoJesseTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = CoresApp, typography = TipografiaApp, content = content)
}
