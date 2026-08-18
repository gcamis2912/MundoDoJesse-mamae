package com.familia.mundodojesse

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random

// ================= SPLASH (boas-vindas) =================
@Composable
fun SplashScreen(nome: String, tts: TtsManager, aoComecar: () -> Unit) {
    val pulso = rememberInfiniteTransition(label = "pulso")
    val escala by pulso.animateFloat(
        initialValue = 1f, targetValue = 1.08f,
        animationSpec = infiniteRepeatable(tween(700), RepeatMode.Reverse), label = "escalaPulso"
    )

    LaunchedEffect(Unit) {
        val saudacao = SAUDACOES[Random.nextInt(SAUDACOES.size)](nome)
        if (Random.nextInt(3) == 0) {
            val afirmacao = AFIRMACOES[Random.nextInt(AFIRMACOES.size)](nome)
            tts.falar("$saudacao $afirmacao")
        } else {
            tts.falar(saudacao)
        }
    }

    Column(
        Modifier.fillMaxSize().background(GradienteFundoPrincipal),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("🌟", fontSize = 90.sp)
        Spacer(Modifier.height(12.dp))
        Text("Mundo do $nome", fontSize = 32.sp, color = Color.White)
        Spacer(Modifier.height(30.dp))
        Text(
            "Vamos brincar! ✨",
            fontSize = 24.sp,
            color = Color.White,
            modifier = Modifier
                .graphicsLayer { scaleX = escala; scaleY = escala }
                .background(Color(0x33FFFFFF), RoundedCornerShape(30.dp))
                .clickable(onClick = aoComecar)
                .padding(horizontal = 32.dp, vertical = 16.dp)
        )
    }
}

// ================= MENU PRINCIPAL =================
data class ItemMenu(val titulo: String, val icone: TipoIcone, val gradiente: Brush, val corIcone: Color, val rota: String)

val ITENS_MENU = listOf(
    ItemMenu("Histórias", TipoIcone.LIVRO, GradienteCoral, Coral, "historias"),
    ItemMenu("Sombras", TipoIcone.SOMBRA, GradienteGrape, Grape, "sombra"),
    ItemMenu("Futebol", TipoIcone.BOLA, GradienteLeaf, Leaf, "futebol"),
    ItemMenu("Blocos de Cor", TipoIcone.BLOCOS, GradienteSky, Sky, "blocos"),
    ItemMenu("Fala e Sons", TipoIcone.BALAO_FALA, GradientePink, Pink, "fonoterapia"),
    ItemMenu("Minhas Letrinhas", TipoIcone.LETRA, GradienteSun, SunDeep, "alfabetizacao"),
    ItemMenu("Frases Gentis", TipoIcone.CASA, GradientePink, Pink, "frases"),
    ItemMenu("Casa de Deus", TipoIcone.CASA, GradienteGrape, Sun, "igreja"),
    ItemMenu("Presente da Família", TipoIcone.PRESENTE, GradienteSun, SunDeep, "presente")
)

@Composable
fun HomeScreen(nome: String, aoNavegar: (String) -> Unit, aoAbrirAreaPais: () -> Unit) {
    FundoComEstrelas(gradiente = GradienteFundoPrincipal) {
        Column(Modifier.fillMaxSize().padding(20.dp)) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconeCartoon(TipoIcone.ESTRELA, tamanho = 34.dp, corPrincipal = Sun)
                    Spacer(Modifier.width(8.dp))
                    Text("Mundo do $nome", fontSize = 26.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(Color(0x33FFFFFF))
                        .clickable(onClick = aoAbrirAreaPais),
                    contentAlignment = Alignment.Center
                ) {
                    Text("⚙", fontSize = 16.sp, color = Color.White)
                }
            }
            Spacer(Modifier.height(22.dp))
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                items(ITENS_MENU) { item ->
                    CartaoAtividade(
                        titulo = item.titulo,
                        icone = item.icone,
                        gradiente = item.gradiente,
                        corIcone = item.corIcone,
                        modifier = Modifier.fillMaxWidth()
                    ) { aoNavegar(item.rota) }
                }
            }
        }
    }
}

// ================= PORTÃO DOS PAIS (continha matemática) =================
@Composable
fun ParentGateScreen(aoLiberar: () -> Unit, aoVoltar: () -> Unit) {
    var numeroA by remember { mutableStateOf(Random.nextInt(2, 8)) }
    var numeroB by remember { mutableStateOf(Random.nextInt(2, 8)) }
    var mensagemErro by remember { mutableStateOf(false) }

    val respostaCerta = numeroA + numeroB
    val opcoes = remember(numeroA, numeroB) {
        val erradas = mutableSetOf<Int>()
        while (erradas.size < 2) {
            val candidata = respostaCerta + Random.nextInt(-4, 5)
            if (candidata != respostaCerta && candidata > 0) erradas.add(candidata)
        }
        (erradas + respostaCerta).shuffled()
    }

    Column(
        Modifier.fillMaxSize().background(Grape).padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Área dos Pais 👨‍👩‍👧", fontSize = 26.sp, color = Color.White)
        Spacer(Modifier.height(10.dp))
        Text("Pra continuar, resolva a continha:", fontSize = 16.sp, color = Color.White)
        Spacer(Modifier.height(20.dp))
        Text("$numeroA + $numeroB = ?", fontSize = 40.sp, color = Color.White)
        Spacer(Modifier.height(28.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            opcoes.forEach { opcao ->
                Text(
                    "$opcao",
                    fontSize = 24.sp,
                    color = Ink,
                    modifier = Modifier
                        .background(Color.White, RoundedCornerShape(18.dp))
                        .clickable {
                            if (opcao == respostaCerta) {
                                aoLiberar()
                            } else {
                                mensagemErro = true
                                numeroA = Random.nextInt(2, 8)
                                numeroB = Random.nextInt(2, 8)
                            }
                        }
                        .padding(horizontal = 26.dp, vertical = 16.dp)
                )
            }
        }

        if (mensagemErro) {
            Spacer(Modifier.height(18.dp))
            Text("Ops, não foi essa. Tenta a nova continha!", color = Color.White, fontSize = 14.sp)
        }

        Spacer(Modifier.height(40.dp))
        Text("⬅️ Voltar", color = Color.White, fontSize = 14.sp, modifier = Modifier.clickable { aoVoltar() })
    }
}

// ================= PAINEL DOS PAIS (estatísticas) =================
@Composable
fun ParentDashboardScreen(repo: ProgressoRepository, aoVoltar: () -> Unit) {
    var nomeEditavel by remember { mutableStateOf(repo.getNome()) }
    val historico = remember { repo.getHistoricoUltimosDias(7) }
    val totalEstrelas = remember { repo.getTotalEstrelas() }
    val minutosHoje = remember { repo.getMinutosHoje() }

    Column(Modifier.fillMaxSize().background(Cream).padding(24.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("⬅️", fontSize = 22.sp, modifier = Modifier.clickable { aoVoltar() })
            Spacer(Modifier.width(12.dp))
            Text("Área dos Pais", fontSize = 24.sp, color = Ink)
        }
        Spacer(Modifier.height(20.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            item {
                CartaoPais(titulo = "Nome da criança") {
                    BasicTextField(
                        value = nomeEditavel,
                        onValueChange = {
                            nomeEditavel = it
                            repo.salvarNome(it)
                        },
                        textStyle = androidx.compose.ui.text.TextStyle(fontSize = 18.sp, color = Ink),
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White, RoundedCornerShape(12.dp))
                            .padding(14.dp)
                    )
                }
            }
            item {
                CartaoPais(titulo = "Tempo de uso hoje") {
                    Text("$minutosHoje minuto(s)", fontSize = 22.sp, color = Grape)
                }
            }
            item {
                CartaoPais(titulo = "Estrelinhas conquistadas no total") {
                    Text("⭐ $totalEstrelas estrelinhas", fontSize = 22.sp, color = SunDeep)
                }
            }
            item {
                CartaoPais(titulo = "Últimos 7 dias") {
                    Column {
                        historico.forEach { (dia, minutos) ->
                            Row(
                                Modifier.fillMaxWidth().padding(vertical = 4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(dia, fontSize = 14.sp, color = Ink)
                                Text("$minutos min", fontSize = 14.sp, color = Grape)
                            }
                        }
                    }
                }
            }
            item {
                CartaoPais(titulo = "Sobre o app") {
                    Text(
                        "Este app funciona 100% offline. Nenhum dado é enviado pra internet — tudo fica guardado só neste aparelho.",
                        fontSize = 13.sp, color = Ink
                    )
                }
            }
        }
    }
}

@Composable
private fun CartaoPais(titulo: String, conteudo: @Composable () -> Unit) {
    Column(
        Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(18.dp))
            .padding(18.dp)
    ) {
        Text(titulo, fontSize = 15.sp, color = Grape)
        Spacer(Modifier.height(8.dp))
        conteudo()
    }
}
