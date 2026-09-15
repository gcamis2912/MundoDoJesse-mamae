package com.familia.mundodojesse

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.random.Random

// ================= BLOCOS DE COR (jogo de acertar a cor certa) =================
data class OpcaoCor(val nome: String, val cor: Color)
private val PALETA_BLOCOS = listOf(
    OpcaoCor("vermelho", Coral),
    OpcaoCor("amarelo", Sun),
    OpcaoCor("verde", Leaf),
    OpcaoCor("azul", Sky),
    OpcaoCor("roxo", Grape),
    OpcaoCor("rosa", Pink)
)

@Composable
fun BlocosScreen(nome: String, tts: TtsManager, repo: ProgressoRepository, aoVoltar: () -> Unit) {
    var nivel by remember { mutableStateOf(1) }
    val qtdCoresNivel = (1 + nivel).coerceAtMost(PALETA_BLOCOS.size)
    val metaNivel = (2 + nivel).coerceAtMost(8)
    var opcoesNivel by remember(nivel) { mutableStateOf(PALETA_BLOCOS.shuffled().take(qtdCoresNivel)) }
    var alvo by remember(nivel, opcoesNivel) { mutableStateOf(opcoesNivel.random()) }
    var pilha by remember(nivel) { mutableStateOf(listOf<Color>()) }
    var mostrarConfete by remember { mutableStateOf(false) }
    var dica by remember { mutableStateOf(false) }

    fun novaRodada() {
        alvo = opcoesNivel.random()
        dica = false
    }

    LaunchedEffect(nivel) {
        tts.falar("Vamos empilhar um bloco ${alvo.nome}, $nome! Toca na cor certa.")
    }

    FundoComEstrelas(gradiente = GradienteSky) {
        Column(Modifier.fillMaxSize().padding(20.dp)) {
            Text("⬅️", color = Color.White, fontSize = 20.sp, modifier = Modifier.clickable { aoVoltar() })
            Spacer(Modifier.height(6.dp))
            Text("Blocos de Cor — Nível $nivel", fontSize = 20.sp, color = Color.White, fontWeight = FontWeight.Bold)
            Text("${pilha.size} de $metaNivel blocos", fontSize = 15.sp, color = Color.White)
            Spacer(Modifier.height(10.dp))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                Text("Toque no bloco ", fontSize = 18.sp, color = Color.White)
                Box(Modifier.size(28.dp).clip(RoundedCornerShape(6.dp)).background(alvo.cor))
                Text(" ${alvo.nome}!", fontSize = 18.sp, color = Color.White, fontWeight = FontWeight.Bold)
            }

            Spacer(Modifier.height(16.dp))
            Column(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                pilha.reversed().forEach { cor ->
                    Box(Modifier.size(width = 90.dp, height = 34.dp).clip(RoundedCornerShape(10.dp)).background(cor))
                }
            }

            Spacer(Modifier.weight(1f))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                opcoesNivel.forEach { opcao ->
                    Box(
                        Modifier
                            .padding(6.dp)
                            .size(64.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(if (dica && opcao == alvo) Color.White else opcao.cor)
                            .clickable {
                                if (opcao == alvo) {
                                    pilha = pilha + opcao.cor
                                    tts.falar("${ELOGIOS[Random.nextInt(ELOGIOS.size)]} Isso é ${opcao.nome}!")
                                    if (pilha.size >= metaNivel) {
                                        repo.somarEstrela("contagem")
                                        mostrarConfete = true
                                        tts.falar("Uau, $nome! Você terminou o nível $nivel! Vamos pro próximo?")
                                    } else {
                                        novaRodada()
                                    }
                                } else {
                                    dica = true
                                    tts.falar(FRASES_APOIO_ERRO[Random.nextInt(FRASES_APOIO_ERRO.size)])
                                }
                            }
                    )
                }
            }
            Spacer(Modifier.height(30.dp))
        }
        ConfettiOverlay(visivel = mostrarConfete)
        LaunchedEffect(mostrarConfete) {
            if (mostrarConfete) {
                delay(1600)
                mostrarConfete = false
                pilha = emptyList()
                nivel++
            }
        }
    }
}

// ================= FALA E SONS (FONOTERAPIA) =================
data class SomImitar(val nome: String, val emoji: String, val onomatopeia: String)

private val SONS_FONOTERAPIA = listOf(
    SomImitar("Cachorro", "🐶", "Au au!"),
    SomImitar("Gato", "🐱", "Miau!"),
    SomImitar("Vaca", "🐄", "Muuu!"),
    SomImitar("Carro", "🚗", "Vrum vrum!"),
    SomImitar("Trem", "🚂", "Tuc tuc tuc!"),
    SomImitar("Chuva", "🌧️", "Pling plong!"),
    SomImitar("Passarinho", "🐦", "Piu piu!"),
    SomImitar("Boi", "🐮", "Mooo!"),
    SomImitar("Beijo", "😘", "Mwah!"),
    SomImitar("Coração", "❤️", "Tum tum!")
)

@Composable
fun FonoterapiaScreen(nome: String, tts: TtsManager, repo: ProgressoRepository, aoVoltar: () -> Unit) {
    LaunchedEffect(Unit) {
        tts.falar("Vamos brincar com os sons, $nome? Toca e depois tenta repetir comigo!")
    }
    FundoComEstrelas(gradiente = GradientePink) {
        Column(Modifier.fillMaxSize().padding(20.dp)) {
            Text("⬅️", color = Color.White, fontSize = 20.sp, modifier = Modifier.clickable { aoVoltar() })
            Spacer(Modifier.height(10.dp))
            Text("Fala e Sons 🗣️", fontSize = 24.sp, color = Color.White)
            Spacer(Modifier.height(16.dp))
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(SONS_FONOTERAPIA) { som ->
                    Column(
                        Modifier
                            .clip(RoundedCornerShape(22.dp))
                            .background(Color.White)
                            .clickable {
                                tts.falar("${som.nome} faz assim: ${som.onomatopeia} Agora tenta você, $nome!")
                                repo.somarEstrela("frases")
                            }
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(som.emoji, fontSize = 40.sp)
                        Spacer(Modifier.height(6.dp))
                        Text(som.nome, fontSize = 16.sp, color = Ink)
                        Text(som.onomatopeia, fontSize = 14.sp, color = Grape)
                    }
                }
            }
        }
    }
}

// ================= FRASES GENTIS (boas maneiras) =================
@Composable
fun BoasManeirasScreen(nome: String, tts: TtsManager, repo: ProgressoRepository, aoVoltar: () -> Unit) {
    LaunchedEffect(Unit) {
        tts.falar("Vamos aprender palavrinhas gentis, $nome? Toca numa pra ouvir!")
    }
    FundoComEstrelas(gradiente = GradientePink) {
        Column(Modifier.fillMaxSize().padding(20.dp)) {
            Text("⬅️", color = Color.White, fontSize = 20.sp, modifier = Modifier.clickable { aoVoltar() })
            Spacer(Modifier.height(10.dp))
            Text("Frases Gentis 💬", fontSize = 24.sp, color = Color.White)
            Spacer(Modifier.height(16.dp))
            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(FRASES_GENTIS) { item ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(18.dp))
                            .background(Color.White)
                            .clickable {
                                tts.falar("${item.frase}. ${item.quando}")
                                repo.somarEstrela("frases")
                            }
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(item.emoji, fontSize = 30.sp)
                        Spacer(Modifier.width(14.dp))
                        Column {
                            Text(item.frase, fontSize = 17.sp, color = Ink)
                            Text(item.quando, fontSize = 13.sp, color = Grape)
                        }
                    }
                }
            }
        }
    }
}

// ================= ALFABETIZAÇÃO (traçar o nome) — corrigida =================
@Composable
fun AlfabetizacaoScreen(nome: String, tts: TtsManager, repo: ProgressoRepository, aoVoltar: () -> Unit) {
    val letras = remember(nome) { nome.uppercase().toList().filter { it.isLetter() } }
    var indiceLetra by remember { mutableStateOf(0) }
    val letraAtual = letras.getOrElse(indiceLetra) { letras.first() }

    val pontosDesenhados = remember(indiceLetra) { mutableStateListOf<Offset>() }
    val caminho = remember(indiceLetra) { Path() }
    var mostrarConfete by remember { mutableStateOf(false) }
    var mostrarDica by remember { mutableStateOf(false) }
    var tamanhoCanvas by remember { mutableStateOf(androidx.compose.ui.geometry.Size.Zero) }

    val pulso = rememberInfiniteTransition(label = "pulsoSeta")
    val escalaSeta by pulso.animateFloat(
        initialValue = 1f, targetValue = 1.3f,
        animationSpec = infiniteRepeatable(tween(500), RepeatMode.Reverse), label = "escalaSeta"
    )

    LaunchedEffect(indiceLetra) {
        mostrarDica = false
        tts.falar("Vamos traçar a letra $letraAtual, $nome? Passa o dedinho por cima dela.")
        delay(6000)
        if (pontosDesenhados.size < 10) {
            mostrarDica = true
            tts.falar(FRASES_APOIO_ERRO[Random.nextInt(FRASES_APOIO_ERRO.size)])
        }
    }

    fun verificarCobertura() {
        if (tamanhoCanvas == androidx.compose.ui.geometry.Size.Zero || pontosDesenhados.isEmpty() || mostrarConfete) return
        val colunas = 8
        val linhas = 8
        val celulasTocadas = mutableSetOf<Pair<Int, Int>>()
        pontosDesenhados.forEach { p ->
            val cx = (p.x / tamanhoCanvas.width * colunas).toInt().coerceIn(0, colunas - 1)
            val cy = (p.y / tamanhoCanvas.height * linhas).toInt().coerceIn(0, linhas - 1)
            celulasTocadas.add(cx to cy)
        }
        val fracao = celulasTocadas.size.toFloat() / (colunas * linhas)
        if (fracao >= 0.14f) {
            mostrarConfete = true
            mostrarDica = false
            repo.somarEstrela("letras")
            tts.falar("${ELOGIOS[Random.nextInt(ELOGIOS.size)]} Você escreveu o $letraAtual, $nome!")
        }
    }

    FundoComEstrelas(gradiente = GradienteSun) {
        Column(Modifier.fillMaxSize().padding(20.dp)) {
            Text("⬅️", color = Color.White, fontSize = 20.sp, modifier = Modifier.clickable { aoVoltar() })
            Spacer(Modifier.height(6.dp))
            Text("Minhas Letrinhas — $nome", fontSize = 20.sp, color = Color.White, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(16.dp))

            Box(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clip(RoundedCornerShape(28.dp))
                    .background(Color.White)
                    .pointerInput(indiceLetra) {
                        detectDragGestures(
                            onDragStart = { offset -> caminho.moveTo(offset.x, offset.y); pontosDesenhados.add(offset) },
                            onDrag = { change, _ ->
                                caminho.lineTo(change.position.x, change.position.y)
                                pontosDesenhados.add(change.position)
                                verificarCobertura()
                            },
                            onDragEnd = { verificarCobertura() }
                        )
                    }
            ) {
                Canvas(modifier = Modifier.fillMaxSize().onGloballyPositioned { coords ->
                    tamanhoCanvas = androidx.compose.ui.geometry.Size(coords.size.width.toFloat(), coords.size.height.toFloat())
                }) {
                    pontosDesenhados.size // faz a tela redesenhar a cada toque novo
                    drawContext.canvas.nativeCanvas.apply {
                        val paint = android.graphics.Paint().apply {
                            color = android.graphics.Color.parseColor("#E6DEF5")
                            textSize = size.height * 0.68f
                            textAlign = android.graphics.Paint.Align.CENTER
                            isAntiAlias = true
                        }
                        drawText(letraAtual.toString(), size.width / 2, size.height / 2 + size.height * 0.24f, paint)
                    }
                    drawPath(caminho, color = Sky, style = Stroke(width = 26f, cap = StrokeCap.Round, join = StrokeJoin.Round))
                }
                if (mostrarDica) {
                    Text(
                        "👉", fontSize = 54.sp,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .escalar(escalaSeta, escalaSeta)
                    )
                    Text(
                        "Tenta assim, devagarinho!",
                        fontSize = 16.sp,
                        color = Grape,
                        modifier = Modifier.align(Alignment.BottomCenter).padding(12.dp)
                    )
                }
            }

            Spacer(Modifier.height(14.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    "🧹 Limpar",
                    color = Color.White,
                    fontSize = 15.sp,
                    modifier = Modifier
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color(0x33FFFFFF))
                        .clickable { pontosDesenhados.clear(); caminho.reset() }
                        .padding(horizontal = 16.dp, vertical = 10.dp)
                )
                Text("Letra ${indiceLetra + 1} de ${letras.size}", color = Color.White, fontSize = 15.sp)
            }
        }
        ConfettiOverlay(visivel = mostrarConfete)
        LaunchedEffect(mostrarConfete) {
            if (mostrarConfete) {
                delay(1600)
                mostrarConfete = false
                indiceLetra = (indiceLetra + 1) % letras.size
            }
        }
    }
}

private fun Modifier.escalar(sx: Float, sy: Float): Modifier =
    this.then(Modifier.scale(sx, sy))
