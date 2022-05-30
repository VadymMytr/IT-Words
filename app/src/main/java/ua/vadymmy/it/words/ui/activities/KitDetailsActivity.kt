package ua.vadymmy.it.words.ui.activities

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleOwner
import javax.inject.Inject
import ua.vadymmy.it.words.R
import ua.vadymmy.it.words.databinding.ActivityKitDetailsBinding
import ua.vadymmy.it.words.di.AppComponent
import ua.vadymmy.it.words.domain.models.word.kit.LearningWordKit
import ua.vadymmy.it.words.domain.models.word.kit.WordKit
import ua.vadymmy.it.words.ui.activities.SearchActivity.Companion.KEY_SEARCH_BUNDLE_KIT
import ua.vadymmy.it.words.ui.adapters.recyclers.WordsAdapter
import ua.vadymmy.it.words.ui.common.BaseActivity
import ua.vadymmy.it.words.ui.viewmodels.KitDetailsViewModel
import ua.vadymmy.it.words.ui.viewmodels.KitDetailsViewModel.Companion.DEFAULT_SIZE
import ua.vadymmy.it.words.ui.viewmodels.KitDetailsViewModel.KitProgress
import ua.vadymmy.it.words.utils.REMOVE_AT_DEFAULT
import ua.vadymmy.it.words.utils.loadFrom
import ua.vadymmy.it.words.utils.startActivity

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
        Log.i("TAG", "onBackPressed() details")
        finish()
    }

    override fun onResume() {
        super.onResume()
        Log.i("TAG", "onResume() details")
        viewModel.parseIntent(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.i("TAG", "onNewIntent() details")
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

            kitDetailsProgressAddWordButton.setOnClickListener {
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

            wordKitProgressLiveData.observe(lifecycleOwner) { kitProgress ->
                fillLearningProgress(kitProgress)
            }

            removeWordAtLiveData.observe(lifecycleOwner) {
                if (it != REMOVE_AT_DEFAULT) {
                    recyclerAdapter.removeAt(it)
                }
            }

            navigateToSearchLiveData.observe(lifecycleOwner) { learningWordKit ->
                learningWordKit?.let {
                    startActivity(
                        SearchActivity::class.java,
                        isFinishRequired = false,
                        intentData = bundleOf(KEY_SEARCH_BUNDLE_KIT to learningWordKit)
                    )
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

            navigateToLearningKitDetailsLiveData.observe(lifecycleOwner) { learningWordKit ->
                learningWordKit?.let {
                    Log.i("TAG", "navigateToLearningKitDetailsLiveData")
                    startActivity(
                        KitDetailsActivity::class.java,
                        isFinishRequired = true,
                        intentData = bundleOf(KEY_BUNDLE_WORD_KIT to learningWordKit)
                    )
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
        fillLearningProgress(KitProgress.from(wordKit))
    }

    private fun fillLearningProgress(kitProgress: KitProgress) {
        with(binding) {
            kitDetailsProgressLayout.isVisible = kitProgress.size != DEFAULT_SIZE
            kitDetailsProgressBar.setProgress(kitProgress.progressPercent, true)
            kitDetailsProgressText.text = getString(
                R.string.kit_progress_placeholder,
                kitProgress.progress,
                kitProgress.size
            )
        }
    }

    companion object {
        const val KEY_BUNDLE_WORD_KIT = "word_kit"
    }
}