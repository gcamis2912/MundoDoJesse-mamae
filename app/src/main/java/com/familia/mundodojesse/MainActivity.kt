package com.familia.mundodojesse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    private lateinit var tts: TtsManager
    private lateinit var repo: ProgressoRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tts = TtsManager(this)
        repo = ProgressoRepository(this)

        setContent {
            MundoDoJesseTheme {
                AppMundoDoJesse(tts = tts, repo = repo)
            }
        }
    }

    override fun onDestroy() {
        tts.liberar()
        super.onDestroy()
    }
}

@Composable
fun AppMundoDoJesse(tts: TtsManager, repo: ProgressoRepository) {
    val navController = rememberNavController()
    var nome by remember { mutableStateOf(repo.getNome()) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(60_000)
            repo.registrarMinutoDeUso()
        }
    }

    NavHost(navController = navController, startDestination = "splash", modifier = Modifier.fillMaxSize()) {
        composable("splash") {
            SplashScreen(nome = nome, tts = tts) {
                navController.navigate("home") { popUpTo("splash") { inclusive = true } }
            }
        }
        composable("home") {
            HomeScreen(
                nome = nome,
                aoNavegar = { rota -> navController.navigate(rota) },
                aoAbrirAreaPais = { navController.navigate("gate") }
            )
        }
        composable("gate") {
            ParentGateScreen(
                aoLiberar = { navController.navigate("pais") { popUpTo("gate") { inclusive = true } } },
                aoVoltar = { navController.popBackStack() }
            )
        }
        composable("pais") {
            ParentDashboardScreen(repo = repo) {
                nome = repo.getNome()
                navController.popBackStack("home", inclusive = false)
            }
        }
        composable("historias") {
            HistoriasScreen(
                nome = nome,
                aoAbrirHistoria = { id -> navController.navigate("historia/$id") },
                aoVoltar = { navController.popBackStack() }
            )
        }
        composable("historia/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: "amor_jesus"
            HistoriaDetalheScreen(historiaId = id, nome = nome, tts = tts, repo = repo) {
                navController.popBackStack()
            }
        }
        composable("presente") {
            HistoriaDetalheScreen(historiaId = "presente", nome = nome, tts = tts, repo = repo) {
                navController.popBackStack()
            }
        }
        composable("sombra") {
            SombraScreen(nome = nome, tts = tts, repo = repo) { navController.popBackStack() }
        }
        composable("futebol") {
            FutebolScreen(nome = nome, tts = tts, repo = repo) { navController.popBackStack() }
        }
        composable("blocos") {
            BlocosScreen(nome = nome, tts = tts, repo = repo) { navController.popBackStack() }
        }
        composable("fonoterapia") {
            FonoterapiaScreen(nome = nome, tts = tts, repo = repo) { navController.popBackStack() }
        }
        composable("frases") {
            BoasManeirasScreen(nome = nome, tts = tts, repo = repo) { navController.popBackStack() }
        }
        composable("igreja") {
            IgrejaScreen(nome = nome, tts = tts, repo = repo) { navController.popBackStack() }
        }
        composable("alfabetizacao") {
            AlfabetizacaoScreen(nome = nome, tts = tts, repo = repo) { navController.popBackStack() }
        }
    }
}
