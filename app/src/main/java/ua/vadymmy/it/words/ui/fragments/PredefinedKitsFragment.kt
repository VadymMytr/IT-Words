package ua.vadymmy.it.words.ui.fragments

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.LifecycleOwner
import javax.inject.Inject
import ua.vadymmy.it.words.R
import ua.vadymmy.it.words.databinding.FragmentPredefinedKitsBinding
import ua.vadymmy.it.words.di.AppComponent
import ua.vadymmy.it.words.ui.activities.KitDetailsActivity
import ua.vadymmy.it.words.ui.activities.KitDetailsActivity.Companion.KEY_BUNDLE_WORD_KIT
import ua.vadymmy.it.words.ui.adapters.recyclers.PredefinedKitsPreviewsAdapter
import ua.vadymmy.it.words.ui.common.BaseFragment
import ua.vadymmy.it.words.ui.viewmodels.PredefinedKitsViewModel
import ua.vadymmy.it.words.utils.startActivity

class PredefinedKitsFragment : BaseFragment() {

    override val titleId: Int get() = R.string.main_tab_predefined_kits

    @Inject
    lateinit var viewModel: PredefinedKitsViewModel
    private lateinit var binding: FragmentPredefinedKitsBinding
    private val recyclerAdapter by lazy {
        PredefinedKitsPreviewsAdapter(viewModel::onPredefinedKitClick)
    }

    override fun configureViews() {
        with(binding) {
            predefinedKitsRecycler.adapter = recyclerAdapter
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }

    override fun inflateBinding(layoutInflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentPredefinedKitsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun injectFragment(injector: AppComponent) {
        injector.injectFragment(this)
    }

    override fun observe(lifecycleOwner: LifecycleOwner) {
        with(viewModel) {
            predefinedKitsLiveData.observe(lifecycleOwner) { kits ->
                if (kits.isNotEmpty()) recyclerAdapter.elements = kits.toMutableList()
            }

            navigatePredefinedKitDetailsLiveData.observe(lifecycleOwner) { kit ->
                Log.i("TAG", "navigatePredefinedKitDetailsLiveData: $kit")
                kit?.let {
                    startActivity(
                        KitDetailsActivity::class.java,
                        intentData = bundleOf(KEY_BUNDLE_WORD_KIT to it)
                    )
                }
            }
        }
    }
}