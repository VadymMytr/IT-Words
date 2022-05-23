package ua.vadymmy.it.words.ui.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import ua.vadymmy.it.words.R
import ua.vadymmy.it.words.databinding.FragmentMainBinding
import ua.vadymmy.it.words.di.AppComponent
import ua.vadymmy.it.words.ui.common.BaseFragment

class MainFragment : BaseFragment() {

    override val titleId: Int
        get() = R.string.main_tab_home

    private lateinit var binding: FragmentMainBinding

    override fun configureViews() {

    }

    override fun inflateBinding(layoutInflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun injectFragment(injector: AppComponent) {
        injector.injectFragment(this)
    }

    override fun observe(lifecycleOwner: LifecycleOwner) {

    }
}