package com.familia.mundodojesse

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun IgrejaScreen(nome: String, tts: TtsManager, repo: ProgressoRepository, aoVoltar: () -> Unit) {
    var etapa by remember { mutableStateOf(1) }

    Box(Modifier.fillMaxSize().background(GradienteGrape)) {
        when (etapa) {
            1 -> EtapaSilencioOuFesta(nome, tts, repo) { etapa = 2 }
            2 -> EtapaAdoracao(nome, tts, repo) { etapa = 3 }
            else -> EtapaAbraco(nome, tts, repo) { aoVoltar() }
        }
        Text("⬅️", color = Color.White, fontSize = 20.sp, modifier = Modifier.padding(20.dp).clickable { aoVoltar() })
    }
}

@Composable
private fun EtapaSilencioOuFesta(nome: String, tts: TtsManager, repo: ProgressoRepository, aoTerminar: () -> Unit) {
    var mostrarConfete by remember { mutableStateOf(false) }
    var dica by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        tts.falar("Opa, $nome! Na casa do Papai do Céu, a gente faz silêncio pra ouvir Ele, ou faz bagunça correndo?")
    }

    Column(Modifier.fillMaxSize().padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text("⛪", fontSize = 70.sp)
        Spacer(Modifier.height(10.dp))
        Text("Na Casa de Deus, a gente faz o quê?", fontSize = 20.sp, color = Color.White, fontWeight = FontWeight.Bold, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
        Spacer(Modifier.height(30.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
            OpcaoIgreja("Silêncio 🤫", certa = true, destaque = dica) {
                repo.somarEstrela("igreja")
                mostrarConfete = true
                tts.falar("Muito bem, meu amor! O $nome é muito educado e ama a casa de Deus!")
            }
            OpcaoIgreja("Correria 😝", certa = false, destaque = false) {
                dica = true
                tts.falar("Quase lá, meu amor! Na casa de Deus, a gente fala baixinho, com carinho.")
            }
        }
    }
    ConfettiOverlay(visivel = mostrarConfete)
    LaunchedEffect(mostrarConfete) {
        if (mostrarConfete) { delay(1800); mostrarConfete = false; aoTerminar() }
    }
}

@Composable
private fun OpcaoIgreja(texto: String, certa: Boolean, destaque: Boolean, aoClicar: () -> Unit) {
    Text(
        texto, fontSize = 20.sp, color = Ink, fontWeight = FontWeight.Bold,
        modifier = Modifier
            .clip(RoundedCornerShape(22.dp))
            .background(if (destaque && certa) Color(0xFFFFE29A) else Color.White)
            .clickable(onClick = aoClicar)
            .padding(horizontal = 26.dp, vertical = 22.dp)
    )
}

@Composable
private fun EtapaAdoracao(nome: String, tts: TtsManager, repo: ProgressoRepository, aoTerminar: () -> Unit) {
    var passo by remember { mutableStateOf(0) }
    var pulando by remember { mutableStateOf(false) }

    LaunchedEffect(passo) {
        when (passo) {
            0 -> tts.falar("Hora de falar baixinho com Jesus, $nome. Toca aqui e faz: shhh...")
            1 -> tts.falar("Agora, hora de dar glória a Deus com alegria! Toca e bate palminha!")
        }
    }

    Column(Modifier.fillMaxSize().padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text(if (passo == 0) "🤫" else "🙌", fontSize = 80.sp)
        Spacer(Modifier.height(20.dp))
        Text(
            if (passo == 0) "Toca pra fazer silêncio" else "Toca pra comemorar com alegria!",
            fontSize = 20.sp, color = Color.White, fontWeight = FontWeight.Bold, textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
        Spacer(Modifier.height(26.dp))
        Box(
            Modifier
                .size(110.dp)
                .clip(CircleShape)
                .background(Color.White)
                .clickable {
                    if (passo == 0) {
                        tts.falar("Shhh... muito bem, $nome!")
                        passo = 1
                    } else {
                        pulando = true
                        repo.somarEstrela("igreja")
                        tts.falar("Aleluia! Isso, $nome, que alegria dar glória a Deus!")
                        passo = 2
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Text(if (passo == 0) "🤫" else "👏", fontSize = 46.sp)
        }
    }
    LaunchedEffect(passo) {
        if (passo == 2) { delay(1600); aoTerminar() }
    }
}

@Composable
private fun EtapaAbraco(nome: String, tts: TtsManager, repo: ProgressoRepository, aoTerminar: () -> Unit) {
    var offsetX by remember { mutableStateOf(0f) }
    var concluido by remember { mutableStateOf(false) }
    var mostrarConfete by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        tts.falar("$nome, arrasta o amiguinho até o coleguinha pra dar um abraço bem gostoso!")
    }

    Column(Modifier.fillMaxSize().padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text("Arrasta pra dar um abraço 🤗", fontSize = 20.sp, color = Color.White, fontWeight = FontWeight.Bold, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
        Spacer(Modifier.height(30.dp))
        Box(Modifier.fillMaxWidth().height(140.dp)) {
            Text("🧒", fontSize = 60.sp, modifier = Modifier.align(Alignment.CenterEnd).padding(end = 10.dp))
            Box(
                Modifier
                    .align(Alignment.CenterStart)
                    .offset { androidx.compose.ui.unit.IntOffset(offsetX.toInt(), 0) }
                    .pointerInput(concluido) {
                        if (concluido) return@pointerInput
                        detectDragGestures(
                            onDrag = { change, arraste ->
                                change.consume()
                                offsetX = (offsetX + arraste.x).coerceIn(0f, 480f)
                            },
                            onDragEnd = {
                                if (offsetX > 320f && !concluido) {
                                    concluido = true
                                    repo.somarEstrela("igreja")
                                    mostrarConfete = true
                                    tts.falar("Que lindo, $nome! Assim a gente ama e respeita os amiguinhos na casa de Deus!")
                                } else if (!concluido) {
                                    offsetX = 0f
                                }
                            }
                        )
                    }
            ) {
                Text("🧑‍🦱", fontSize = 60.sp)
            }
        }
    }
    ConfettiOverlay(visivel = mostrarConfete)
    LaunchedEffect(mostrarConfete) {
        if (mostrarConfete) { delay(1800); aoTerminar() }
    }
}
