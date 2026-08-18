package com.familia.mundodojesse

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin
import kotlin.random.Random

// ================= BOTÃO GRANDE (usado em telas mais simples) =================
@Composable
fun BotaoGrande(texto: String, emoji: String, cor: Color, modifier: Modifier = Modifier, aoClicar: () -> Unit) {
    val interacao = remember { MutableInteractionSource() }
    val pressionado by interacao.collectIsPressedAsState()
    val escala by animateFloatAsState(
        targetValue = if (pressionado) 0.92f else 1f,
        animationSpec = spring(dampingRatio = 0.5f), label = "escalaBotao"
    )
    Column(
        modifier = modifier
            .graphicsLayer { scaleX = escala; scaleY = escala }
            .clip(RoundedCornerShape(28.dp))
            .background(cor)
            .clickable(interactionSource = interacao, indication = null, onClick = aoClicar)
            .padding(vertical = 22.dp, horizontal = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(emoji, fontSize = 46.sp)
        Spacer(Modifier.height(6.dp))
        Text(texto, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
    }
}

// ================= CONFETE DE COMEMORAÇÃO =================
private data class Confetinho(val xInicial: Float, val corIndex: Int, val atraso: Int, val rotacaoBase: Float)
private val CoresConfete = listOf(
    Color(0xFFFF6F61), Color(0xFFFFC93C), Color(0xFF4CAF7D),
    Color(0xFF6EC6FF), Color(0xFF8B5FBF), Color(0xFFFF9EC7)
)

@Composable
fun ConfettiOverlay(visivel: Boolean) {
    if (!visivel) return
    val particulas = remember {
        List(45) {
            Confetinho(
                xInicial = Random.nextFloat(),
                corIndex = Random.nextInt(CoresConfete.size),
                atraso = Random.nextInt(400),
                rotacaoBase = Random.nextFloat() * 360f
            )
        }
    }
    val progresso = remember { Animatable(0f) }
    LaunchedEffect(visivel) {
        progresso.snapTo(0f)
        progresso.animateTo(1f, animationSpec = tween(1800, easing = LinearEasing))
    }
    Canvas(modifier = Modifier.fillMaxSize()) {
        val alturaTotal = size.height
        val larguraTotal = size.width
        particulas.forEach { p ->
            val t = (progresso.value * 1800 - p.atraso).coerceIn(0f, 1800f) / 1800f
            if (t <= 0f) return@forEach
            val y = t * (alturaTotal + 60f) - 30f
            val x = p.xInicial * larguraTotal
            rotate(p.rotacaoBase + t * 360f, pivot = Offset(x, y)) {
                drawRect(
                    color = CoresConfete[p.corIndex].copy(alpha = min(1f, 1.3f - t)),
                    topLeft = Offset(x, y),
                    size = androidx.compose.ui.geometry.Size(14f, 14f)
                )
            }
        }
    }
}

// ================= ÍCONES CARTOON DESENHADOS =================
enum class TipoIcone { LIVRO, SOMBRA, BOLA, BLOCOS, BALAO_FALA, PRESENTE, ESTRELA, CASA, LETRA }

@Composable
fun IconeCartoon(tipo: TipoIcone, tamanho: Dp = 56.dp, corPrincipal: Color = Color.White) {
    Canvas(modifier = Modifier.size(tamanho)) {
        val w = size.width
        val h = size.height
        when (tipo) {
            TipoIcone.LIVRO -> desenharLivro(w, h, corPrincipal)
            TipoIcone.SOMBRA -> desenharSombra(w, h, corPrincipal)
            TipoIcone.BOLA -> desenharBola(w, h, corPrincipal)
            TipoIcone.BLOCOS -> desenharBlocos(w, h, corPrincipal)
            TipoIcone.BALAO_FALA -> desenharBalaoFala(w, h, corPrincipal)
            TipoIcone.PRESENTE -> desenharPresente(w, h, corPrincipal)
            TipoIcone.ESTRELA -> desenharEstrela(w, h, corPrincipal)
            TipoIcone.CASA -> desenharCasa(w, h, corPrincipal)
            TipoIcone.LETRA -> desenharLetraAbc(w, h, corPrincipal)
        }
    }
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.desenharLivro(w: Float, h: Float, cor: Color) {
    val margem = w * 0.12f
    drawRoundRect(
        color = cor,
        topLeft = Offset(margem, h * 0.18f),
        size = androidx.compose.ui.geometry.Size(w - margem * 2, h * 0.64f),
        cornerRadius = androidx.compose.ui.geometry.CornerRadius(w * 0.08f)
    )
    drawLine(color = Color(0x33000000), start = Offset(w / 2, h * 0.18f), end = Offset(w / 2, h * 0.82f), strokeWidth = w * 0.025f)
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.desenharSombra(w: Float, h: Float, cor: Color) {
    val pontos = listOf(
        Offset(w * 0.5f, h * 0.1f), Offset(w * 0.62f, h * 0.38f), Offset(w * 0.92f, h * 0.4f),
        Offset(w * 0.68f, h * 0.6f), Offset(w * 0.78f, h * 0.9f), Offset(w * 0.5f, h * 0.72f),
        Offset(w * 0.22f, h * 0.9f), Offset(w * 0.32f, h * 0.6f), Offset(w * 0.08f, h * 0.4f),
        Offset(w * 0.38f, h * 0.38f)
    )
    val caminho = androidx.compose.ui.graphics.Path().apply {
        moveTo(pontos[0].x, pontos[0].y)
        pontos.drop(1).forEach { lineTo(it.x, it.y) }
        close()
    }
    drawPath(caminho, color = cor)
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.desenharBola(w: Float, h: Float, cor: Color) {
    val raio = w * 0.42f
    val centro = Offset(w / 2, h / 2)
    drawCircle(color = cor, radius = raio, center = centro)
    drawCircle(color = Color(0x33000000), radius = raio, center = centro, style = Stroke(width = w * 0.03f))
    val ptsPentagono = (0 until 5).map { i ->
        val angulo = Math.toRadians((-90 + i * 72).toDouble())
        Offset(centro.x + (raio * 0.42f) * cos(angulo).toFloat(), centro.y + (raio * 0.42f) * sin(angulo).toFloat())
    }
    val caminho = androidx.compose.ui.graphics.Path().apply {
        moveTo(ptsPentagono[0].x, ptsPentagono[0].y)
        ptsPentagono.drop(1).forEach { lineTo(it.x, it.y) }
        close()
    }
    drawPath(caminho, color = Color(0xFF3B3450))
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.desenharBlocos(w: Float, h: Float, cor: Color) {
    val tamanhoBloco = w * 0.36f
    drawRoundRect(color = cor, topLeft = Offset(w * 0.08f, h * 0.5f), size = androidx.compose.ui.geometry.Size(tamanhoBloco, tamanhoBloco), cornerRadius = androidx.compose.ui.geometry.CornerRadius(w * 0.06f))
    drawRoundRect(color = Color(0xE6FFFFFF), topLeft = Offset(w * 0.5f, h * 0.5f), size = androidx.compose.ui.geometry.Size(tamanhoBloco, tamanhoBloco), cornerRadius = androidx.compose.ui.geometry.CornerRadius(w * 0.06f))
    drawRoundRect(color = cor.copy(alpha = 0.75f), topLeft = Offset(w * 0.29f, h * 0.14f), size = androidx.compose.ui.geometry.Size(tamanhoBloco, tamanhoBloco), cornerRadius = androidx.compose.ui.geometry.CornerRadius(w * 0.06f))
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.desenharBalaoFala(w: Float, h: Float, cor: Color) {
    drawRoundRect(color = cor, topLeft = Offset(w * 0.1f, h * 0.12f), size = androidx.compose.ui.geometry.Size(w * 0.8f, h * 0.56f), cornerRadius = androidx.compose.ui.geometry.CornerRadius(w * 0.18f))
    val caminho = androidx.compose.ui.graphics.Path().apply {
        moveTo(w * 0.28f, h * 0.62f); lineTo(w * 0.2f, h * 0.86f); lineTo(w * 0.46f, h * 0.64f); close()
    }
    drawPath(caminho, color = cor)
    listOf(0.34f, 0.5f, 0.66f).forEach { fx -> drawCircle(color = Color(0xFFFFFFFF), radius = w * 0.045f, center = Offset(w * fx, h * 0.4f)) }
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.desenharPresente(w: Float, h: Float, cor: Color) {
    drawRoundRect(color = cor, topLeft = Offset(w * 0.16f, h * 0.4f), size = androidx.compose.ui.geometry.Size(w * 0.68f, h * 0.48f), cornerRadius = androidx.compose.ui.geometry.CornerRadius(w * 0.06f))
    drawRect(color = Color(0xFFFFFFFF), topLeft = Offset(w * 0.16f, h * 0.28f), size = androidx.compose.ui.geometry.Size(w * 0.68f, h * 0.14f))
    drawRect(color = Color(0xFFFFFFFF), topLeft = Offset(w * 0.46f, h * 0.28f), size = androidx.compose.ui.geometry.Size(w * 0.08f, h * 0.6f))
    drawCircle(color = cor.copy(alpha = 0.85f), radius = w * 0.12f, center = Offset(w * 0.38f, h * 0.24f))
    drawCircle(color = cor.copy(alpha = 0.85f), radius = w * 0.12f, center = Offset(w * 0.62f, h * 0.24f))
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.desenharEstrela(w: Float, h: Float, cor: Color) {
    val centro = Offset(w / 2, h / 2)
    val raioFora = w * 0.46f
    val raioDentro = w * 0.2f
    val pontos = (0 until 10).map { i ->
        val raio = if (i % 2 == 0) raioFora else raioDentro
        val angulo = Math.toRadians((-90 + i * 36).toDouble())
        Offset(centro.x + raio * cos(angulo).toFloat(), centro.y + raio * sin(angulo).toFloat())
    }
    val caminho = androidx.compose.ui.graphics.Path().apply {
        moveTo(pontos[0].x, pontos[0].y)
        pontos.drop(1).forEach { lineTo(it.x, it.y) }
        close()
    }
    drawPath(caminho, color = cor)
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.desenharCasa(w: Float, h: Float, cor: Color) {
    val telhado = androidx.compose.ui.graphics.Path().apply {
        moveTo(w * 0.5f, h * 0.12f); lineTo(w * 0.12f, h * 0.46f); lineTo(w * 0.88f, h * 0.46f); close()
    }
    drawPath(telhado, color = cor)
    drawRect(color = cor, topLeft = Offset(w * 0.2f, h * 0.46f), size = androidx.compose.ui.geometry.Size(w * 0.6f, h * 0.42f))
    drawRect(color = Color(0xFFFFFFFF), topLeft = Offset(w * 0.42f, h * 0.62f), size = androidx.compose.ui.geometry.Size(w * 0.16f, h * 0.26f))
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.desenharLetraAbc(w: Float, h: Float, cor: Color) {
    drawLine(cor, Offset(w * 0.2f, h * 0.85f), Offset(w * 0.2f, h * 0.15f), strokeWidth = w * 0.14f, cap = StrokeCap.Round)
    drawLine(cor, Offset(w * 0.2f, h * 0.15f), Offset(w * 0.75f, h * 0.15f), strokeWidth = w * 0.14f, cap = StrokeCap.Round)
    drawLine(cor, Offset(w * 0.2f, h * 0.5f), Offset(w * 0.62f, h * 0.5f), strokeWidth = w * 0.13f, cap = StrokeCap.Round)
    drawLine(cor, Offset(w * 0.2f, h * 0.85f), Offset(w * 0.75f, h * 0.85f), strokeWidth = w * 0.14f, cap = StrokeCap.Round)
}

// ================= CARTÃO DE ATIVIDADE (menu principal) =================
@Composable
fun CartaoAtividade(titulo: String, icone: TipoIcone, gradiente: Brush, corIcone: Color, modifier: Modifier = Modifier, aoClicar: () -> Unit) {
    val interacao = remember { MutableInteractionSource() }
    val pressionado by interacao.collectIsPressedAsState()
    val escala by animateFloatAsState(
        targetValue = if (pressionado) 0.90f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy), label = "escalaCartao"
    )
    val elevacao by animateDpAsState(targetValue = if (pressionado) 2.dp else 10.dp, label = "elevacaoCartao")

    Column(
        modifier = modifier
            .graphicsLayer { scaleX = escala; scaleY = escala }
            .shadow(elevation = elevacao, shape = RoundedCornerShape(32.dp), clip = false)
            .clip(RoundedCornerShape(32.dp))
            .background(gradiente)
            .clickable(interactionSource = interacao, indication = null, onClick = aoClicar)
            .padding(vertical = 18.dp, horizontal = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.size(64.dp).shadow(4.dp, CircleShape).clip(CircleShape).background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            IconeCartoon(tipo = icone, tamanho = 40.dp, corPrincipal = corIcone)
        }
        Spacer(Modifier.height(10.dp))
        Text(
            titulo, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center, lineHeight = 19.sp
        )
    }
}

// ================= FUNDO COM ESTRELINHAS =================
private data class Estrelinha(val x: Float, val y: Float, val raio: Float, val fase: Float)

@Composable
fun FundoComEstrelas(gradiente: Brush, modifier: Modifier = Modifier, conteudo: @Composable () -> Unit) {
    val estrelinhas = remember {
        List(22) {
            Estrelinha(x = Random.nextFloat(), y = Random.nextFloat(), raio = Random.nextFloat() * 3f + 2f, fase = Random.nextFloat() * 6.28f)
        }
    }
    val transicao = rememberInfiniteTransition(label = "brilho")
    val tempo by transicao.animateFloat(
        initialValue = 0f, targetValue = 6.28f,
        animationSpec = infiniteRepeatable(tween(4000, easing = LinearEasing), RepeatMode.Restart),
        label = "tempoBrilho"
    )
    Box(modifier = modifier.fillMaxSize().background(gradiente)) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            estrelinhas.forEach { estrela ->
                val brilho = (sin(tempo + estrela.fase) + 1f) / 2f
                drawCircle(
                    color = Color.White.copy(alpha = 0.25f + brilho * 0.5f),
                    radius = estrela.raio,
                    center = Offset(estrela.x * size.width, estrela.y * size.height)
                )
            }
        }
        conteudo()
    }
}
