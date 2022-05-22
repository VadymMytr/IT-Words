package ua.vadymmy.it.words.ui.activities

import android.util.Log
import android.view.LayoutInflater
import androidx.lifecycle.LifecycleOwner
import javax.inject.Inject
import ua.vadymmy.it.words.databinding.ActivityMainBinding
import ua.vadymmy.it.words.di.AppComponent
import ua.vadymmy.it.words.ui.common.BaseActivity
import ua.vadymmy.it.words.ui.viewmodels.MainViewModel
import ua.vadymmy.it.words.utils.startActivity

class MainActivity : BaseActivity() {

    @Inject
    lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onResume() {
        super.onResume()
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
        with(viewModel) {
            navigateAuthLiveData.observe(lifecycleOwner) {
                Log.i("TAG", "navigateAuthLiveData: ${it}")
                if (it) {
                    startActivity(AuthActivity::class.java)
                }
            }
        }
    }

}