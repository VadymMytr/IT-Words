package ua.vadymmy.it.words.ui.activities

import android.content.Intent
import android.view.LayoutInflater
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleOwner
import javax.inject.Inject
import ua.vadymmy.it.words.R
import ua.vadymmy.it.words.databinding.ActivityKitDetailsBinding
import ua.vadymmy.it.words.di.AppComponent
import ua.vadymmy.it.words.domain.models.word.kit.LearningWordKit
import ua.vadymmy.it.words.domain.models.word.kit.WordKit
import ua.vadymmy.it.words.ui.adapters.recyclers.WordsAdapter
import ua.vadymmy.it.words.ui.common.BaseActivity
import ua.vadymmy.it.words.ui.viewmodels.KitDetailsViewModel
import ua.vadymmy.it.words.utils.REMOVE_AT_DEFAULT
import ua.vadymmy.it.words.utils.loadFrom

class KitDetailsActivity : BaseActivity() {

    @Inject
    lateinit var viewModel: KitDetailsViewModel
    private lateinit var binding: ActivityKitDetailsBinding
    private val recyclerAdapter by lazy {
        WordsAdapter(
            viewModel::onSpeakWordClick,
            viewModel::onRemoveWordClick
        )
    }

    override fun onBackPressed() {
        finish()
    }

    override fun onResume() {
        super.onResume()
        viewModel.parseIntent(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.let { viewModel.parseIntent(it) }
    }

    override fun configureViews() {
        with(binding) {
            kitDetailsWordsRecycler.adapter = recyclerAdapter

            kitDetailsBackButton.setOnClickListener {
                onBackPressed()
            }

            kitDetailsLearnButton.setOnClickListener {
                viewModel.onLearnWordsClick()
            }

            kitDetailsCardsButton.setOnClickListener {
                viewModel.onCardsClick()
            }

            kitDetailsSearchButton.setOnClickListener {
                viewModel.onAddWordsClick()
            }
        }
    }

    override fun inflateBinding(layoutInflater: LayoutInflater) {
        binding = ActivityKitDetailsBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
    }

    override fun injectActivity(injector: AppComponent) {
        injector.injectActivity(this)
    }

    override fun observe(lifecycleOwner: LifecycleOwner) {
        with(viewModel) {
            predefinedWordKitLiveData.observe(lifecycleOwner) { wordKit ->
                wordKit?.let { fillWordKit(it) }
            }

            learningWordKitLiveData.observe(lifecycleOwner) { learningWordKit ->
                learningWordKit?.let {
                    fillWordKit(it)
                    fillLearningWordKit(it)
                }
            }

            wordKitSizeLiveData.observe(lifecycleOwner) { size ->
                binding.kitDetailsSizeText.text = getString(
                    R.string.kit_details_size_placeholder,
                    size
                )
            }

            removeWordAtLiveData.observe(lifecycleOwner) {
                if (it != REMOVE_AT_DEFAULT) {
                    recyclerAdapter.removeAt(it)
                }
            }

            navigateToSearchLiveData.observe(lifecycleOwner) { learningWordKit ->
                learningWordKit?.let {
                    //TODO nav search
                }
            }

            navigateToCardsLiveData.observe(lifecycleOwner) { wordKit ->
                wordKit?.let {
                    //TODO nav kit cards
                }
            }

            navigateToTestLiveData.observe(lifecycleOwner) { learningWordKit ->
                learningWordKit?.let {
                    //TODO nav test
                }
            }
        }
    }

    private fun fillWordKit(wordKit: WordKit) {
        with(binding) {
            kitDetailsNameText.text = wordKit.name
            kitDetailsImage.loadFrom(wordKit.image)

            val areNoWords = wordKit.words.isEmpty()
            kitDetailsNoWordsLayout.isVisible = areNoWords
            kitDetailsContentLayout.isVisible = !areNoWords

            if (wordKit !is LearningWordKit) recyclerAdapter.setWords(wordKit)
        }
    }

    private fun fillLearningWordKit(wordKit: LearningWordKit) {
        recyclerAdapter.setWords(wordKit)
    }

    companion object {
        const val KEY_BUNDLE_WORD_KIT = "word_kit"
    }
}