package ua.vadymmy.it.words.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import ua.vadymmy.it.words.MyApp
import ua.vadymmy.it.words.R
import ua.vadymmy.it.words.di.AppComponent
import ua.vadymmy.it.words.ui.activities.main.MainActivity

abstract class BaseFragment : Fragment() {
    private companion object {
        private const val DEFAULT_TITLE = R.string.app_name
    }

    @StringRes
    protected open val titleId: Int = DEFAULT_TITLE

    protected abstract fun inflateBinding(
        layoutInflater: LayoutInflater,
        container: ViewGroup?
    ): View

    protected abstract fun injectFragment(injector: AppComponent)
    protected abstract fun configureViews()
    protected abstract fun observe(lifecycleOwner: LifecycleOwner)
    protected fun showLoading() = (requireActivity() as? MainActivity)?.showLoading()
    protected fun hideLoading() = (requireActivity() as? MainActivity)?.hideLoading()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflateBinding(inflater, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        injectFragment(MyApp.injector)
        configureViews()
        observe(viewLifecycleOwner)
        titleId.takeIf { it != DEFAULT_TITLE }?.let { titleString ->
            (requireActivity() as? BaseActivity)?.titleTextView?.setText(titleString)
        }
    }
}