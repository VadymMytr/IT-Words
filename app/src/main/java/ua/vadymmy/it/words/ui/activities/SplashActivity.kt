package ua.vadymmy.it.words.ui.activities

import android.view.LayoutInflater
import androidx.lifecycle.LifecycleOwner
import javax.inject.Inject
import ua.vadymmy.it.words.databinding.ActivitySplashBinding
import ua.vadymmy.it.words.di.AppComponent
import ua.vadymmy.it.words.ui.activities.main.MainActivity
import ua.vadymmy.it.words.ui.common.BaseActivity
import ua.vadymmy.it.words.ui.viewmodels.SplashViewModel
import ua.vadymmy.it.words.utils.startActivity

class SplashActivity : BaseActivity() {

    @Inject
    lateinit var viewModel: SplashViewModel
    private lateinit var binding: ActivitySplashBinding

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }

    override fun inflateBinding(layoutInflater: LayoutInflater) {
        binding = ActivitySplashBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
    }

    override fun injectActivity(injector: AppComponent) {
        injector.injectActivity(this)
    }

    override fun configureViews() {}

    override fun observe(lifecycleOwner: LifecycleOwner) {
        with(viewModel) {
            navigateAuthLiveData.observe(lifecycleOwner) {
                if (it) {
                    startActivity(AuthActivity::class.java, isFinishRequired = true)
                }
            }

            navigateMainLiveData.observe(lifecycleOwner) {
                if (it) {
                    startActivity(MainActivity::class.java, isFinishRequired = true)
                }
            }
        }
    }
}