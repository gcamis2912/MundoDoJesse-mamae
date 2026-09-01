package com.familia.mundodojesse

import android.content.Context
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun String.comNome(nome: String) = this.replace("{nome}", nome)

const val NOME_PADRAO = "Jessé"

val SAUDACOES: List<(String) -> String> = listOf(
    { nome -> "Oi, $nome! Que alegria te ver! O que vamos fazer hoje?" },
    { nome -> "Olá, meu amor, $nome! Vamos brincar juntinhos?" },
    { nome -> "$nome, que bom te ver de novo! Estava com saudade!" },
    { nome -> "Oiii, $nome! Pronto pra um dia cheio de descobertas?" },
    { nome -> "Seja bem-vindo, $nome! Vamos aprender brincando?" }
)

val ELOGIOS = listOf(
    "Isso, meu amor!", "Muito bem!", "Você é muito inteligente!", "Parabéns!",
    "Que lindo!", "Ebaaa, conseguiu!", "Show de bola!", "Uau, que demais!",
    "Você consegue tudo!", "Isso mesmo, viu só!"
)

val FRASES_APOIO_ERRO = listOf(
    "Quase lá, meu amor! Tenta assim...",
    "Você está indo bem! Vamos tentar de novo, juntinhos?",
    "Isso, continua tentando, você consegue!",
    "Calma, sem pressa nenhuma — vamos de novo?"
)

data class LetraNome(val letra: Char, val corHex: Long)
val LETRAS_JESSE = listOf(
    LetraNome('J', 0xFFFF6F61),
    LetraNome('E', 0xFFFFC93C),
    LetraNome('S', 0xFF4CAF7D),
    LetraNome('S', 0xFF6EC6FF),
    LetraNome('É', 0xFF8B5FBF)
)

data class FraseGentil(val frase: String, val emoji: String, val quando: String)
val FRASES_GENTIS = listOf(
    FraseGentil("Bom dia", "☀️", "Fala \"bom dia\" quando acorda. E vê alguém de manhã."),
    FraseGentil("Boa tarde", "🌤️", "Fala \"boa tarde\" quando vê alguém de tardinha."),
    FraseGentil("Boa noite", "🌙", "Fala \"boa noite\" na hora de dormir."),
    FraseGentil("Oi", "👋", "Fala \"oi\" quando encontra alguém."),
    FraseGentil("Tchau", "👋", "Fala \"tchau\" quando vai embora, dando tchauzinho com a mão."),
    FraseGentil("Por favor", "🙏", "Fala \"por favor\" quando quer pedir uma coisinha."),
    FraseGentil("Obrigado", "🙏", "Fala \"obrigado\" quando ganha algo, ou alguém te ajuda."),
    FraseGentil("Não, obrigado", "🙅", "Fala \"não, obrigado\" quando não quer uma coisa. Sempre com educação."),
    FraseGentil("De nada", "😊", "Fala \"de nada\" quando alguém te agradece."),
    FraseGentil("Com licença", "🚪", "Fala \"com licença\" quando quer passar."),
    FraseGentil("Desculpa", "😊", "Fala \"desculpa\" quando machuca alguém sem querer."),
    FraseGentil("Desculpa (arrotinho ou pum)", "😊", "Se você arrotar ou soltar um puzinho, fala \"desculpa\" pras pessoas."),
    FraseGentil("Tudo bem, acontece", "😌", "Fala isso quando alguém te pede desculpa. Pra deixar a pessoa tranquila."),
    FraseGentil("Não tem problema", "🤗", "Fala isso pra dizer que está tudo bem, sem ficar bravo.")
)

val AFIRMACOES: List<(String) -> String> = listOf(
    { nome -> "$nome, você é muito especial." },
    { nome -> "$nome, você é valioso, viu?" },
    { nome -> "$nome, você é uma criança educada e gentil." },
    { nome -> "Deus ama você demais, $nome." },
    { nome -> "A mamãe ama você mais que tudo nesse mundo, $nome." },
    { nome -> "$nome, você é amado, sempre." }
)

data class SlideHistoria(val emoji: String, val texto: String)
data class Historia(val id: String, val titulo: String, val emoji: String, val slides: List<SlideHistoria>)

val HISTORIAS = listOf(
    Historia(
        id = "amor_jesus", titulo = "Jesus Te Ama, {nome}!", emoji = "💛",
        slides = listOf(
            SlideHistoria("✨", "Jesus olha pra {nome} todo dia, com um sorriso grande, cheio de amor."),
            SlideHistoria("🤗", "Jesus cuida de {nome} de pertinho, como um abraço quentinho o dia inteiro."),
            SlideHistoria("🌙", "De noite, enquanto {nome} dorme, Jesus fica de olho, protegendo bem baixinho."),
            SlideHistoria("💛", "Jesus ama {nome} pra sempre, não importa o que aconteça. Pra sempre mesmo!")
        )
    ),
    Historia(
        id = "noe", titulo = "Noé e o Barquinho", emoji = "🚢",
        slides = listOf(
            SlideHistoria("👴", "Deus pediu pro Noé fazer um barco bem grandão."),
            SlideHistoria("🐘", "Os bichinhos entraram dois a dois: au au, muu, miau!"),
            SlideHistoria("🌧️", "Choveu bastante, mas todo mundo ficou seguro e quentinho."),
            SlideHistoria("🌈", "Depois, apareceu um arco-íris lindo, colorido no céu!")
        )
    ),
    Historia(
        id = "presente", titulo = "O Maior Presente da Mamãe", emoji = "💝",
        slides = listOf(
            SlideHistoria("🤰", "Antes de {nome} nascer, a mamãe já amava ele demais."),
            SlideHistoria("👶", "Quando {nome} nasceu, a vida da mamãe ficou cheia de luz e alegria."),
            SlideHistoria("💝", "{nome} é o maior presente que a mamãe já ganhou na vida."),
            SlideHistoria("🥰", "Toda vez que {nome} sorri, a mamãe fica muito mais feliz. Ela te ama, {nome}!")
        )
    ),
    Historia(
        id = "carta_mamae", titulo = "Uma Cartinha da Mamãe pra {nome}", emoji = "💌",
        slides = listOf(
            SlideHistoria("💌", "{nome}, a mamãe fez esse aplicativo inteirinho com muito, muito amor, só pra você."),
            SlideHistoria("🌟", "Você é especial, {nome}. Você é valioso. Você é uma criança educada e cheia de luz."),
            SlideHistoria("🙏", "Deus ama você demais, {nome}. E a mamãe ama você mais que tudo nesse mundo."),
            SlideHistoria("💛", "Não importa o tamanho que você fique, {nome}, a mamãe vai estar sempre aqui, te amando. Sempre.")
        )
    ),
    Historia(
        id = "criacao", titulo = "A Criação do Mundo", emoji = "🌍",
        slides = listOf(
            SlideHistoria("🌞", "Deus fez o sol brilhando, a luinha do dia! E fez a lua bem quietinha, pra noite."),
            SlideHistoria("🌊", "Deus fez o mar grandão, e os peixinhos nadando dentro dele, felizes."),
            SlideHistoria("🦁", "Deus fez os bichinhos: leão, passarinho, borboleta... cada um mais lindo!"),
            SlideHistoria("👫", "E Deus fez Adão e Eva, o primeiro homem e a primeira mulher, com muito amor.")
        )
    ),
    Historia(
        id = "abraao", titulo = "Abraão e a Grande Família", emoji = "⭐",
        slides = listOf(
            SlideHistoria("👴", "Abraão amava muito a Deus e confiava Nele, sempre."),
            SlideHistoria("⭐", "Deus disse: \"Olha pro céu, Abraão! Sua família vai ser grande, que nem as estrelinhas!\""),
            SlideHistoria("👶", "E nasceu Isaque, o filho que Deus tinha prometido. Que alegria!"),
            SlideHistoria("🙏", "A família de Abraão foi crescendo, cheia da bênção de Deus.")
        )
    ),
    Historia(
        id = "jose_egito", titulo = "José e o Perdão", emoji = "🌈",
        slides = listOf(
            SlideHistoria("👦", "José era um menino muito amado, com uma túnica linda e colorida."),
            SlideHistoria("😢", "Ele ficou longe da família por um tempo, mas confiou em Deus o tempo todo."),
            SlideHistoria("💪", "Deus cuidou de José, e ele ficou forte e sábio, ajudando muita gente."),
            SlideHistoria("🤗", "No fim, José abraçou a família de novo, com o coração cheio de perdão.")
        )
    ),
    Historia(
        id = "moises", titulo = "Moisés e o Mar que se Abriu", emoji = "🌊",
        slides = listOf(
            SlideHistoria("👶", "Bebê Moisés foi guardado numa cestinha, bem protegido no rio."),
            SlideHistoria("🐑", "Ele cresceu e Deus disse: \"Vai, Moisés! Eu estou contigo!\""),
            SlideHistoria("🌊", "Na frente deles tinha um mar grandão... e a água se abriu, feito um caminho!"),
            SlideHistoria("🎉", "Todo mundo atravessou sequinho. Deus abre caminhos, sempre!")
        )
    ),
    Historia(
        id = "sansao", titulo = "Sansão, o Forte", emoji = "💪",
        slides = listOf(
            SlideHistoria("💪", "Sansão era um homem muito, muito forte, porque Deus dava força pra ele."),
            SlideHistoria("🦁", "Com a força de Deus, Sansão conseguia fazer coisas incríveis."),
            SlideHistoria("🙏", "A força de Sansão vinha do amor e da confiança em Deus."),
            SlideHistoria("⭐", "Deus dá força pra gente também, quando confiamos Nele.")
        )
    ),
    Historia(
        id = "davi_golias", titulo = "Davi e Golias", emoji = "🐑",
        slides = listOf(
            SlideHistoria("🐑", "Davi era pequenininho, mas tinha um coração corajoso."),
            SlideHistoria("😨", "Apareceu um gigante grandão chamado Golias. Todo mundo ficou com medo."),
            SlideHistoria("🎯", "Davi confiou em Deus e foi corajoso, bem cheio de fé."),
            SlideHistoria("🎉", "Todo mundo comemorou! Deus estava cuidando de Davi.")
        )
    ),
    Historia(
        id = "menino_jesse", titulo = "O Menino Jessé da Bíblia", emoji = "🐑",
        slides = listOf(
            SlideHistoria("👨‍🌾", "Há muito tempo, viveu um homem bom chamado Jessé, que amava Deus."),
            SlideHistoria("👨‍👩‍👧‍👦", "Jessé tinha vários filhos, e um deles era Davi, que ficou muito importante."),
            SlideHistoria("🐑", "Jessé cuidava da sua família com carinho, do jeitinho que Deus gosta."),
            SlideHistoria("💛", "{nome}, seu nome é especial — é o mesmo nome de um homem bom da Bíblia!")
        )
    ),
    Historia(
        id = "salomao", titulo = "Salomão, o Rei Sábio", emoji = "👑",
        slides = listOf(
            SlideHistoria("👑", "Salomão virou rei, e Deus perguntou: \"O que você quer que eu te dê?\""),
            SlideHistoria("🧠", "Salomão pediu sabedoria, pra saber cuidar bem das pessoas."),
            SlideHistoria("💡", "Deus ficou feliz com esse pedido e deu a Salomão muita sabedoria."),
            SlideHistoria("🙏", "Pedir sabedoria a Deus é uma escolha muito boa, sempre.")
        )
    ),
    Historia(
        id = "elias_enoque", titulo = "Elias e Enoque, Amigos de Deus", emoji = "☁️",
        slides = listOf(
            SlideHistoria("🙏", "Elias e Enoque amavam Deus e caminhavam bem pertinho Dele, todo dia."),
            SlideHistoria("☁️", "Eles confiavam tanto em Deus, que viviam em paz, sem medo de nada."),
            SlideHistoria("✨", "Deus levou os dois pra perto Dele de um jeito muito especial."),
            SlideHistoria("💛", "Quando a gente é amigo de Deus, Ele cuida da gente com muito carinho.")
        )
    ),
    Historia(
        id = "joao_batista", titulo = "João Batista, o Primo Especial", emoji = "💧",
        slides = listOf(
            SlideHistoria("👶", "João Batista nasceu numa família muito feliz, um presente de Deus."),
            SlideHistoria("🌿", "Ele cresceu forte, morando pertinho da natureza, amando Deus."),
            SlideHistoria("💧", "João ajudava as pessoas a se aproximarem de Deus, com água e amor."),
            SlideHistoria("🙌", "Ele anunciou pra todo mundo: \"Jesus está chegando!\" Que alegria!")
        )
    ),
    Historia(
        id = "nascimento_jesus", titulo = "O Nascimento de Jesus", emoji = "⭐",
        slides = listOf(
            SlideHistoria("🌟", "Numa noite estreladinha, na cidade de Belém, nasceu um bebê muito especial: Jesus!"),
            SlideHistoria("👨‍👩‍👦", "Maria e José cuidaram Dele com muito carinho, bem quietinho na palhinha."),
            SlideHistoria("✨", "Uma estrela brilhou no céu, e os pastorzinhos vieram visitar, cheios de alegria."),
            SlideHistoria("💛", "Todo mundo ficou feliz, feliz! Foi uma noite cheia de amor pra sempre lembrar.")
        )
    ),
    Historia(
        id = "milagres_jesus", titulo = "Jesus Faz Coisas Incríveis", emoji = "✨",
        slides = listOf(
            SlideHistoria("🍞", "Jesus pegou pouquinho de pão e peixe, e deu comidinha pra um montão de gente!"),
            SlideHistoria("🌊", "Jesus acalmou o mar bravo, só de falar bem baixinho: \"Fica quieto.\""),
            SlideHistoria("🤗", "Jesus cuidava de quem estava doente, com as mãozinhas cheias de amor."),
            SlideHistoria("💛", "Jesus faz coisas incríveis porque Ele ama a gente demais.")
        )
    ),
    Historia(
        id = "amor_da_cruz", titulo = "O Grande Amor de Jesus", emoji = "💛",
        slides = listOf(
            SlideHistoria("💛", "Jesus ama a gente tanto, tanto, que fez a coisa mais bonita por nós."),
            SlideHistoria("🙏", "Ele mostrou o maior amor do mundo, pensando em cada um de nós."),
            SlideHistoria("🌅", "E depois, Jesus ficou vivo de novo! Um dia de alegria enorme, pra sempre."),
            SlideHistoria("💫", "Esse é o amor de Jesus: gigante, pra sempre, e só pra fazer a gente feliz.")
        )
    ),
    Historia(
        id = "verdade", titulo = "Falar Sempre a Verdade", emoji = "🗣️",
        slides = listOf(
            SlideHistoria("😊", "Deus gosta muito quando a gente fala a verdade, sempre, com um sorriso."),
            SlideHistoria("💬", "Falar a verdade é ser um amiguinho de confiança, que todo mundo ama."),
            SlideHistoria("🤗", "Mesmo quando é difícil, falar a verdade deixa o coração levinho e feliz."),
            SlideHistoria("⭐", "{nome}, você é uma criança sincera e Deus fica muito feliz com isso!")
        )
    ),
    Historia(
        id = "jesus_volta", titulo = "Jesus Vai Voltar", emoji = "🌈",
        slides = listOf(
            SlideHistoria("🌟", "Um dia, Jesus vai voltar pra buscar todo mundo que O ama, num dia lindo e feliz."),
            SlideHistoria("🏰", "Ele vai levar a gente pra um lugar cheio de luz, brilho e muita alegria."),
            SlideHistoria("🌈", "Não tem nenhum medo nesse dia — só festa, cores lindas e amor gostoso."),
            SlideHistoria("💛", "{nome}, Jesus está preparando esse lugar especial, pensando em você.")
        )
    ),
    Historia(
        id = "ovelhinha_perdida", titulo = "A Ovelhinha Perdida", emoji = "🐑",
        slides = listOf(
            SlideHistoria("🐑", "O pastorzinho cuidava de cem ovelhinhas, com muito, muito carinho."),
            SlideHistoria("😟", "Uma ovelhinha se perdeu! O pastor ficou preocupadinho e saiu procurando."),
            SlideHistoria("🔍", "Ele procurou, procurou, até encontrar a ovelhinha, bem quietinha e assustada."),
            SlideHistoria("🥰", "Ele pegou ela no colo, feliz da vida! Jesus procura a gente assim, com muito amor.")
        )
    )
)

class ProgressoRepository(context: Context) {
    private val prefs = context.getSharedPreferences("mundo_do_jesse_prefs", Context.MODE_PRIVATE)
    private val formatoDia = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    fun getNome(): String = prefs.getString("nome_crianca", NOME_PADRAO) ?: NOME_PADRAO
    fun salvarNome(nome: String) { prefs.edit().putString("nome_crianca", nome.ifBlank { NOME_PADRAO }).apply() }

    fun getEstrelas(categoria: String): Int = prefs.getInt("estrelas_$categoria", 0)
    fun somarEstrela(categoria: String) {
        prefs.edit().putInt("estrelas_$categoria", getEstrelas(categoria) + 1).apply()
    }

    fun registrarMinutoDeUso() {
        val chaveHoje = "minutos_" + formatoDia.format(Date())
        val atual = prefs.getInt(chaveHoje, 0)
        prefs.edit().putInt(chaveHoje, atual + 1).apply()
        val dias = prefs.getStringSet("dias_com_uso", mutableSetOf())?.toMutableSet() ?: mutableSetOf()
        dias.add(formatoDia.format(Date()))
        prefs.edit().putStringSet("dias_com_uso", dias).apply()
    }

    fun getMinutosHoje(): Int = prefs.getInt("minutos_" + formatoDia.format(Date()), 0)

    fun getHistoricoUltimosDias(qtdDias: Int = 7): List<Pair<String, Int>> {
        val cal = java.util.Calendar.getInstance()
        val lista = mutableListOf<Pair<String, Int>>()
        repeat(qtdDias) {
            val chaveData = formatoDia.format(cal.time)
            val minutos = prefs.getInt("minutos_$chaveData", 0)
            lista.add(chaveData to minutos)
            cal.add(java.util.Calendar.DAY_OF_YEAR, -1)
        }
        return lista.reversed()
    }

    fun getTotalEstrelas(): Int {
        val categorias = listOf("historias", "letras", "sombra", "futebol", "frases", "contagem")
        return categorias.sumOf { getEstrelas(it) }
    }
}
