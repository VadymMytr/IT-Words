package ua.vadymmy.it.words.ui.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleOwner
import javax.inject.Inject
import ua.vadymmy.it.words.R
import ua.vadymmy.it.words.databinding.FragmentLearningKitsBinding
import ua.vadymmy.it.words.di.AppComponent
import ua.vadymmy.it.words.ui.activities.KitDetailsActivity
import ua.vadymmy.it.words.ui.adapters.recyclers.LearningKitsPreviewsAdapter
import ua.vadymmy.it.words.ui.common.BaseFragment
import ua.vadymmy.it.words.ui.viewmodels.LearningKitsViewModel
import ua.vadymmy.it.words.utils.REMOVE_AT_DEFAULT
import ua.vadymmy.it.words.utils.startActivity

class LearningKitsFragment : BaseFragment() {

    override val titleId: Int get() = R.string.main_tab_my_kits

    @Inject
    lateinit var viewModel: LearningKitsViewModel
    private lateinit var binding: FragmentLearningKitsBinding
    private val recyclerAdapter by lazy {
        LearningKitsPreviewsAdapter(
            viewModel::onLearningKitClick,
            viewModel::onRemoveLearningKitClick
        )
    }

    override fun configureViews() {
        with(binding) {
            learningKitsRecycler.adapter = recyclerAdapter
            learningKitsCreate.setOnClickListener {
                viewModel.onCreateKitClick()
            }

            learningKitsCreate.isVisible = false
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }

    override fun inflateBinding(layoutInflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentLearningKitsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun injectFragment(injector: AppComponent) {
        injector.injectFragment(this)
    }

    override fun observe(lifecycleOwner: LifecycleOwner) {
        with(viewModel) {
            showLoaderLiveData.observe(lifecycleOwner) {
                if (it) showLoading()
            }

            hideLoaderLiveData.observe(lifecycleOwner) {
                if (it) hideLoading()
            }

            learningKitsLiveData.observe(lifecycleOwner) { kits ->
                val isEmpty = kits.isEmpty()
                binding.learningKitsEmptyText.isVisible = isEmpty
                binding.learningKitsRecycler.isVisible = !isEmpty

                if (!isEmpty) {
                    recyclerAdapter.elements = kits.toMutableList()
                }
            }

            openKitDetailsLiveData.observe(lifecycleOwner) { kit ->
                kit?.let {
                    startActivity(
                        KitDetailsActivity::class.java,
                        intentData = bundleOf(KitDetailsActivity.KEY_BUNDLE_WORD_KIT to it)
                    )
                }
            }

            showNewKitDialogLiveData.observe(lifecycleOwner) {
                if (it) {

                }
            }

            removeKitAtLiveData.observe(lifecycleOwner) {
                if (it != REMOVE_AT_DEFAULT) {
                    recyclerAdapter.removeAt(it)
                }
            }
        }
    }
}