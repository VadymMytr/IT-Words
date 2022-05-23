package ua.vadymmy.it.words.ui.activities.main

import android.view.LayoutInflater
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.tabs.TabLayoutMediator
import javax.inject.Inject
import ua.vadymmy.it.words.databinding.ActivityMainBinding
import ua.vadymmy.it.words.di.AppComponent
import ua.vadymmy.it.words.ui.adapters.pagers.MainPagerAdapter
import ua.vadymmy.it.words.ui.common.BaseActivity
import ua.vadymmy.it.words.ui.viewmodels.MainViewModel

class MainActivity : BaseActivity() {

    @Inject
    lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var navigationAdapter: MainPagerAdapter

    override val titleTextView: TextView? by lazy {
        binding.mainToolbarTitle
    }

    override fun inflateBinding(layoutInflater: LayoutInflater) {
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
    }

    override fun injectActivity(injector: AppComponent) {
        injector.injectActivity(this)
    }

    override fun configureViews() {
        navigationAdapter = MainPagerAdapter(this).also {
            binding.mainPager.adapter = it
        }

        TabLayoutMediator(binding.mainTabs, binding.mainPager) { tab, position ->
            val navigationTab = NavigationTab.from(position)
            tab.setText(navigationTab.titleRes)
            tab.setIcon(navigationTab.iconRes)
        }.attach()
    }

    override fun observe(lifecycleOwner: LifecycleOwner) {
    }

    fun showLoading() {
        with(binding) {
            mainLoader.isVisible = true
        }
    }

    fun hideLoading() {
        with(binding) {
            mainLoader.isVisible = false
        }
    }
}