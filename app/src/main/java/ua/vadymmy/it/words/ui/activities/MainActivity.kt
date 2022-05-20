package ua.vadymmy.it.words.ui.activities

import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.LifecycleOwner
import javax.inject.Inject
import ua.vadymmy.it.words.databinding.ActivityMainBinding
import ua.vadymmy.it.words.di.AppComponent
import ua.vadymmy.it.words.ui.common.BaseActivity
import ua.vadymmy.it.words.ui.viewmodels.MainViewModel

class MainActivity : BaseActivity() {

    @Inject
    lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.onResume()
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

    }

    override fun observe(lifecycleOwner: LifecycleOwner) {

    }

}