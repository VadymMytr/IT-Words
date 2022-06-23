package ua.vadymmy.it.words.ui.activities.testing

import android.view.LayoutInflater
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleOwner
import javax.inject.Inject
import ua.vadymmy.it.words.R
import ua.vadymmy.it.words.databinding.ActivityLearningBinding
import ua.vadymmy.it.words.di.AppComponent
import ua.vadymmy.it.words.domain.models.word.common.WordProgress.TestType
import ua.vadymmy.it.words.domain.models.word.common.WordProgress.TestType.EN_VOICE_UA_SELECT
import ua.vadymmy.it.words.domain.models.word.common.WordProgress.TestType.UA_TRANSLATE_EN_INPUT
import ua.vadymmy.it.words.domain.models.word.common.WordProgress.TestType.UA_TRANSLATE_EN_SELECT
import ua.vadymmy.it.words.ui.activities.KitDetailsActivity
import ua.vadymmy.it.words.ui.activities.LearningResultActivity
import ua.vadymmy.it.words.ui.activities.LearningResultActivity.Companion.RESULT_ACT_BUNDLE_AMOUNT
import ua.vadymmy.it.words.ui.common.BaseActivity
import ua.vadymmy.it.words.ui.common.BaseViewModel
import ua.vadymmy.it.words.ui.fragments.BaseLearnFragment
import ua.vadymmy.it.words.ui.fragments.Learn1Fragment
import ua.vadymmy.it.words.ui.fragments.Learn2Fragment
import ua.vadymmy.it.words.ui.fragments.Learn3Fragment
import ua.vadymmy.it.words.ui.viewmodels.LearningViewModel
import ua.vadymmy.it.words.utils.loadFrom
import ua.vadymmy.it.words.utils.showToast
import ua.vadymmy.it.words.utils.startActivity

class LearningActivity : BaseActivity() {

    @Inject
    lateinit var viewModel: LearningViewModel
    private lateinit var binding: ActivityLearningBinding

    override val titleId: Int by lazy {
        R.string.learning_title
    }

    override val titleTextView: TextView? by lazy {
        binding.learningToolbarTitle
    }

    override fun onResume() {
        super.onResume()
        viewModel.parseIntent(intent)
    }

    override fun inflateBinding(layoutInflater: LayoutInflater) {
        binding = ActivityLearningBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
    }

    override fun injectActivity(injector: AppComponent) {
        injector.injectActivity(this)
    }

    override fun observe(lifecycleOwner: LifecycleOwner) {
        with(viewModel) {
            showProgressLiveData.observe(lifecycleOwner) {
                binding.learningWordsProgress.apply {
                    progress = it.first
                    max = it.second
                }

                binding.learningProgressText.text = getString(
                    R.string.learning_progress_placeholder,
                    it.first,
                    it.second
                )
            }

            updateIDKButtonText.observe(lifecycleOwner) {
                it?.let {
                    binding.learningIdkButton.setText(it)
                }
            }

            updateFragmentVisibilityLiveData.observe(lifecycleOwner) {
                binding.learningFragmentContainer.isVisible = it
            }

            updateCurrentWordVisibilityLiveData.observe(lifecycleOwner) {
                binding.learningWordDetailsLayout.isVisible = it
            }

            navigateNextWordLiveData.observe(lifecycleOwner) {
                it?.let {
                    navFrag(when (TestType.from(it.first.wordProgress)) {
                        EN_VOICE_UA_SELECT -> Learn1Fragment()
                        UA_TRANSLATE_EN_SELECT -> Learn2Fragment()
                        UA_TRANSLATE_EN_INPUT -> Learn3Fragment()
                    }.apply {
                        word = it.first
                        answers = it.second
                    })
                }
            }

            showCurrentWordLiveData.observe(lifecycleOwner) {
                it?.let {
                    with(binding) {
                        learningWordImage.loadFrom(it.image)
                        learningWordOriginalText.text = it.original
                        learningWordTranscriptionText.text = it.transcription
                        learningWordTranslateText.text = it.translate
                    }
                }
            }

            navigateResultLiveData.observe(lifecycleOwner) {
                it?.let {
                    startActivity(
                        LearningResultActivity::class.java,
                        isFinishRequired = true,
                        intentData = bundleOf(
                            KitDetailsActivity.KEY_BUNDLE_WORD_KIT to it.first,
                            RESULT_ACT_BUNDLE_AMOUNT to it.second
                        )
                    )
                }
            }

            showMessageLiveData.observe(lifecycleOwner) {
                if (it != BaseViewModel.DEFAULT_MESSAGE) showToast(it)
            }
        }
    }

    override fun configureViews() {
        binding.learningIdkButton.setOnClickListener {
            viewModel.onButtonClick()
        }

        binding.learningWordSpeakButton.setOnClickListener {
            viewModel.onPronounceClick()
        }
    }

    private fun navFrag(fragment: BaseLearnFragment) {
        supportFragmentManager.beginTransaction().replace(
            R.id.learning_fragment_container,
            fragment
        ).commit()
    }
}