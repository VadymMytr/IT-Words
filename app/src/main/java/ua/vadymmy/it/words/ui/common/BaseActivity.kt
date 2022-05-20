package ua.vadymmy.it.words.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import ua.vadymmy.it.words.MyApp
import ua.vadymmy.it.words.R
import ua.vadymmy.it.words.di.AppComponent

abstract class BaseActivity : AppCompatActivity() {
    private companion object {
        private const val DEFAULT_TITLE = R.string.app_name
    }

    @StringRes
    protected open val titleId: Int = DEFAULT_TITLE
    protected open val titleTextView: TextView? = null

    protected abstract fun inflateBinding(layoutInflater: LayoutInflater)
    protected abstract fun injectActivity(injector: AppComponent)
    protected abstract fun configureViews()
    protected abstract fun observe(lifecycleOwner: LifecycleOwner)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inflateBinding(layoutInflater)
        injectActivity(MyApp.injector)
        configureViews()
        observe(this as LifecycleOwner)
        titleId.takeIf { it != DEFAULT_TITLE }?.let { titleString ->
            titleTextView?.text = getString(titleString)
        }
    }
}