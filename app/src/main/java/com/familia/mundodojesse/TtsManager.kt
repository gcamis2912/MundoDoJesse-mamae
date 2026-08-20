package com.familia.mundodojesse

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import java.util.Locale

class TtsManager(context: Context) {
    private var tts: TextToSpeech? = null
    private var pronto = false
    private val filaPendente = mutableListOf<String>()

    var aoComecarFala: (() -> Unit)? = null
    var aoTerminarFala: (() -> Unit)? = null

    init {
        tts = TextToSpeech(context.applicationContext) { status ->
            if (status == TextToSpeech.SUCCESS) {
                tts?.language = Locale("pt", "BR")
                tts?.setPitch(1.28f)
                tts?.setSpeechRate(0.86f)
                escolherVozMaisSuave()
                tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                    override fun onStart(utteranceId: String?) { aoComecarFala?.invoke() }
                    override fun onDone(utteranceId: String?) { aoTerminarFala?.invoke() }
                    @Deprecated("compat") override fun onError(utteranceId: String?) {}
                })
                pronto = true
                filaPendente.forEach { falarAgora(it) }
                filaPendente.clear()
            }
        }
    }

    private fun escolherVozMaisSuave() {
        try {
            val vozes = tts?.voices ?: return
            val candidata = vozes.firstOrNull {
                it.locale.language == "pt" && (it.name.contains("female", true) || it.name.contains("#female", true))
            }
            candidata?.let { tts?.voice = it }
        } catch (_: Exception) { }
    }

    fun falar(texto: String) {
        if (!pronto) { filaPendente.add(texto); return }
        falarAgora(texto)
    }

    private fun falarAgora(texto: String) {
        tts?.speak(texto, TextToSpeech.QUEUE_FLUSH, null, texto.hashCode().toString())
    }

    fun liberar() {
        tts?.stop()
        tts?.shutdown()
    }
}
