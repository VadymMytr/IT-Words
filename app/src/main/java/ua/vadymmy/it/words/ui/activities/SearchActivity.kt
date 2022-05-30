package ua.vadymmy.it.words.ui.activities

import android.content.Intent
import android.view.LayoutInflater
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.LifecycleOwner
import javax.inject.Inject
import ua.vadymmy.it.words.databinding.ActivitySearchBinding
import ua.vadymmy.it.words.di.AppComponent
import ua.vadymmy.it.words.ui.adapters.recyclers.SearchAdapter
import ua.vadymmy.it.words.ui.common.BaseActivity
import ua.vadymmy.it.words.ui.common.BaseViewModel.Companion.DEFAULT_MESSAGE
import ua.vadymmy.it.words.ui.viewmodels.SearchViewModel
import ua.vadymmy.it.words.utils.showToast

class SearchActivity : BaseActivity() {

    @Inject
    lateinit var viewModel: SearchViewModel
    private lateinit var binding: ActivitySearchBinding
    private val searchAdapter by lazy {
        SearchAdapter(
            viewModel::onSpeakWordClick,
            viewModel::onAddWordClick,
            viewModel::onRemoveWordClick,
            viewModel::isWordInKit
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
            searchResultsRecycler.adapter = searchAdapter

            searchBackButton.setOnClickListener {
                onBackPressed()
            }

            searchEditText.addTextChangedListener {
                viewModel.search(it.toString())
            }
        }
    }

    override fun inflateBinding(layoutInflater: LayoutInflater) {
        binding = ActivitySearchBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
    }

    override fun injectActivity(injector: AppComponent) {
        injector.injectActivity(this)
    }

    override fun observe(lifecycleOwner: LifecycleOwner) {
        with(viewModel) {
            searchShowDefaultLiveData.observe(lifecycleOwner) {
                binding.searchDefaultText.isVisible = it
            }

            showLoaderLiveData.observe(lifecycleOwner) {
                binding.searchLoader.root.isVisible = it
            }

            searchNotFoundLiveData.observe(lifecycleOwner) {
                binding.searchNotFoundText.isVisible = it
            }

            searchResultLiveData.observe(lifecycleOwner) {
                binding.searchResultsRecycler.isVisible = it.isNotEmpty()
                searchAdapter.setWords(it)
            }

            showMessageLiveData.observe(lifecycleOwner) {
                if (it != DEFAULT_MESSAGE) showToast(it)
            }
        }
    }

    companion object {
        const val KEY_SEARCH_BUNDLE_KIT = "key_search_bundle_kit"
    }
}