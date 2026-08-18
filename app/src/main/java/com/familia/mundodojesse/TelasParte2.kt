package com.familia.mundodojesse

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

// ================= LISTA DE HISTÓRIAS =================
@Composable
fun HistoriasScreen(nome: String, aoAbrirHistoria: (String) -> Unit, aoVoltar: () -> Unit) {
    FundoComEstrelas(gradiente = GradienteCoral) {
        Column(Modifier.fillMaxSize().padding(20.dp)) {
            Text("⬅️", color = Color.White, fontSize = 20.sp, modifier = Modifier.clickable { aoVoltar() })
            Spacer(Modifier.height(10.dp))
            Text("Historinhas 📖", fontSize = 24.sp, color = Color.White)
            Spacer(Modifier.height(16.dp))
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(HISTORIAS) { historia ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color.White)
                            .clickable { aoAbrirHistoria(historia.id) }
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            Modifier.size(48.dp).clip(CircleShape).background(Cream),
                            contentAlignment = Alignment.Center
                        ) { Text(historia.emoji, fontSize = 26.sp) }
                        Spacer(Modifier.width(14.dp))
                        Text(historia.titulo.comNome(nome), fontSize = 17.sp, color = Ink)
                    }
                }
            }
        }
    }
}

// ================= DETALHE DE UMA HISTÓRIA =================
@Composable
fun HistoriaDetalheScreen(historiaId: String, nome: String, tts: TtsManager, repo: ProgressoRepository, aoVoltar: () -> Unit) {
    val historia = remember { HISTORIAS.first { it.id == historiaId } }
    var indice by remember { mutableStateOf(0) }
    var mostrarConfete by remember { mutableStateOf(false) }
    val slide = historia.slides[indice]
    val ultimo = indice == historia.slides.size - 1

    LaunchedEffect(indice) {
        tts.falar(slide.texto.comNome(nome))
    }

    FundoComEstrelas(gradiente = GradienteCoral) {
        Column(Modifier.fillMaxSize().padding(20.dp)) {
            Text("⬅️", color = Color.White, fontSize = 20.sp, modifier = Modifier.clickable { aoVoltar() })
            Spacer(Modifier.height(6.dp))
            Text(historia.titulo.comNome(nome), fontSize = 20.sp, color = Color.White, fontWeight = FontWeight.Bold)
            Spacer(Modifier.weight(1f))

            Column(
                Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(28.dp))
                    .background(Color.White)
                    .padding(28.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(slide.emoji, fontSize = 70.sp)
                Spacer(Modifier.height(16.dp))
                Text(slide.texto.comNome(nome), fontSize = 19.sp, color = Ink, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
            }

            Spacer(Modifier.weight(1f))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    "⬅️ Antes",
                    color = Color.White,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(if (indice > 0) Color(0x33FFFFFF) else Color.Transparent)
                        .clickable(enabled = indice > 0) { indice-- }
                        .padding(horizontal = 18.dp, vertical = 12.dp)
                )
                Text(
                    if (ultimo) "Terminar 🎉" else "Depois ➡️",
                    color = Color.White,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0x55FFFFFF))
                        .clickable {
                            if (ultimo) {
                                repo.somarEstrela("historias")
                                mostrarConfete = true
                                tts.falar("Que história linda, $nome! Deus te ama muito!")
                            } else indice++
                        }
                        .padding(horizontal = 18.dp, vertical = 12.dp)
                )
            }
        }
        ConfettiOverlay(visivel = mostrarConfete)
        LaunchedEffect(mostrarConfete) {
            if (mostrarConfete) {
                delay(1800)
                mostrarConfete = false
                aoVoltar()
            }
        }
    }
}

// ================= JOGO DE SOMBRAS =================
private val FORMAS_JOGO = listOf(TipoIcone.ESTRELA, TipoIcone.BOLA, TipoIcone.CASA, TipoIcone.PRESENTE, TipoIcone.BALAO_FALA)

@Composable
fun SombraScreen(nome: String, tts: TtsManager, repo: ProgressoRepository, aoVoltar: () -> Unit) {
    var formaAlvo by remember { mutableStateOf(FORMAS_JOGO.random()) }
    var opcoes by remember { mutableStateOf(gerarOpcoesSombra(formaAlvo)) }
    var mostrarConfete by remember { mutableStateOf(false) }
    var dica by remember { mutableStateOf<TipoIcone?>(null) }

    fun novaRodada() {
        formaAlvo = FORMAS_JOGO.random()
        opcoes = gerarOpcoesSombra(formaAlvo)
        dica = null
    }

    LaunchedEffect(Unit) {
        tts.falar("Vamos encontrar a sombra que combina, $nome? Olha essa forma aqui!")
    }

    FundoComEstrelas(gradiente = GradienteGrape) {
        Column(Modifier.fillMaxSize().padding(20.dp)) {
            Text("⬅️", color = Color.White, fontSize = 20.sp, modifier = Modifier.clickable { aoVoltar() })
            Spacer(Modifier.height(10.dp))
            Text("Qual sombra combina?", fontSize = 22.sp, color = Color.White)
            Spacer(Modifier.height(20.dp))

            Box(
                Modifier
                    .size(120.dp)
                    .align(Alignment.CenterHorizontally)
                    .clip(CircleShape)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                IconeCartoon(formaAlvo, tamanho = 80.dp, corPrincipal = Sun)
            }

            Spacer(Modifier.height(36.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                opcoes.forEach { forma ->
                    Box(
                        Modifier
                            .size(90.dp)
                            .clip(CircleShape)
                            .background(if (dica == forma) Color(0xFFFFE29A) else Color(0x33FFFFFF))
                            .clickable {
                                if (forma == formaAlvo) {
                                    repo.somarEstrela("sombra")
                                    mostrarConfete = true
                                    tts.falar("${ELOGIOS[Random.nextInt(ELOGIOS.size)]} Encontrou a sombra certinha, $nome!")
                                    novaRodada()
                                } else {
                                    tts.falar(FRASES_APOIO_ERRO[Random.nextInt(FRASES_APOIO_ERRO.size)])
                                    dica = formaAlvo
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        IconeCartoon(forma, tamanho = 60.dp, corPrincipal = Color(0xFF2B2740))
                    }
                }
            }
        }
        ConfettiOverlay(visivel = mostrarConfete)
        LaunchedEffect(mostrarConfete) {
            if (mostrarConfete) {
                delay(1200)
                mostrarConfete = false
            }
        }
    }
}

private fun gerarOpcoesSombra(certa: TipoIcone): List<TipoIcone> {
    val restantes = FORMAS_JOGO.filter { it != certa }.shuffled().take(2)
    return (restantes + certa).shuffled()
}

// ================= FUTEBOL =================
@Composable
fun FutebolScreen(nome: String, tts: TtsManager, repo: ProgressoRepository, aoVoltar: () -> Unit) {
    val escopo = rememberCoroutineScope()
    val posicaoBolaY = remember { Animatable(0f) }
    var gols by remember { mutableStateOf(repo.getEstrelas("futebol")) }
    var mostrarConfete by remember { mutableStateOf(false) }
    var chutando by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        tts.falar("Vamos jogar futebol, $nome? Toca na bolinha pra chutar pro gol!")
    }

    fun chutar() {
        if (chutando) return
        chutando = true
        escopo.launch {
            posicaoBolaY.animateTo(-520f, animationSpec = tween(550))
            gols++
            repo.somarEstrela("futebol")
            mostrarConfete = true
            val elogio = ELOGIOS[Random.nextInt(ELOGIOS.size)]
            tts.falar("Golaaaço! $elogio Você é o craque, $nome!")
            posicaoBolaY.snapTo(0f)
            delay(1400)
            mostrarConfete = false
            chutando = false
        }
    }

    Box(Modifier.fillMaxSize().background(GradienteLeaf).clickable { chutar() }) {
        Canvas(modifier = Modifier.fillMaxWidth().height(140.dp).align(Alignment.TopCenter)) {
            val margem = size.width * 0.22f
            drawRect(
                color = Color.White,
                topLeft = Offset(margem, 0f),
                size = androidx.compose.ui.geometry.Size(size.width - margem * 2, size.height),
                style = androidx.compose.ui.graphics.drawscope.Stroke(width = 10f)
            )
        }

        Text("⬅️", color = Color.White, fontSize = 20.sp, modifier = Modifier.padding(20.dp).clickable { aoVoltar() })

        Text(
            "⚽ Gols: $gols",
            fontSize = 20.sp,
            col
