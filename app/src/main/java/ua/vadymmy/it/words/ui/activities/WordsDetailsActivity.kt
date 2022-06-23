package ua.vadymmy.it.words.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ua.vadymmy.it.words.MyApp
import ua.vadymmy.it.words.databinding.ActivityWordsDetailsBinding
import ua.vadymmy.it.words.domain.models.word.kit.WordKit
import ua.vadymmy.it.words.domain.words.utils.PronounceWordUseCase
import ua.vadymmy.it.words.utils.loadFrom

class WordsDetailsActivity @Inject constructor() : AppCompatActivity() {

    private lateinit var kit: WordKit
    private lateinit var binding: ActivityWordsDetailsBinding

    @Inject
    lateinit var pronounceWordUseCase: PronounceWordUseCase

    private var currentPosition = 0
    private val max get() = kit.size
    private val canGoNext get() = currentPosition != max - 1
    private val canGoBack get() = currentPosition > 0
    private val currentWord get() = kit.words[currentPosition]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWordsDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        MyApp.injector.injectActivity(this)

        kit = intent.getSerializableExtra(
            KitDetailsActivity.KEY_BUNDLE_WORD_KIT
        ) as WordKit

        fillPosition()
        fillWord()

        binding.wordDetailsSpeakButton.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                pronounceWordUseCase(currentWord)
            }
        }

        binding.wordsDetailsCloseBtn.setOnClickListener {
            finish()
        }

        binding.wordsDetailsLeftBtn.setOnClickListener {
            if (canGoBack) {
                currentPosition--
                fillPosition()
                fillWord()
            }
        }

        binding.wordsDetailsRightBtn.setOnClickListener {
            if (canGoNext) {
                currentPosition++
                fillPosition()
                fillWord()
            }
        }
    }

    private fun fillWord() {
        with(binding) {
            wordDetailsImage.loadFrom(currentWord.image)
            wordDetailsTranslateText.text = currentWord.translate
            wordDetailsOriginalText.text = currentWord.original
            wordDetailsTranscriptionText.text = currentWord.transcription
            CoroutineScope(Dispatchers.IO).launch {
                pronounceWordUseCase(currentWord)
            }
        }
    }

    private fun fillPosition() {
        binding.wordsPos.text = "${currentPosition.inc()} / $max"
    }
}