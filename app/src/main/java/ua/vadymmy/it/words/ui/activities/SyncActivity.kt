package ua.vadymmy.it.words.ui.activities

import android.view.LayoutInflater
import androidx.core.view.isInvisible
import androidx.lifecycle.LifecycleOwner
import javax.inject.Inject
import ua.vadymmy.it.words.R
import ua.vadymmy.it.words.databinding.ActivitySyncBinding
import ua.vadymmy.it.words.di.AppComponent
import ua.vadymmy.it.words.ui.common.BaseActivity
import ua.vadymmy.it.words.ui.viewmodels.sync.SyncStatus.Synced
import ua.vadymmy.it.words.ui.viewmodels.sync.SyncViewModel
import ua.vadymmy.it.words.utils.startActivity

class SyncActivity : BaseActivity() {

    @Inject
    lateinit var syncViewModel: SyncViewModel
    private lateinit var syncBinding: ActivitySyncBinding

    override fun onResume() {
        super.onResume()
        syncViewModel.onResume()
    }

    override fun configureViews() {
        syncBinding.syncNextButton.setOnClickListener {
            syncViewModel.onNextClick()
        }
    }

    override fun inflateBinding(layoutInflater: LayoutInflater) {
        syncBinding = ActivitySyncBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
    }

    override fun injectActivity(injector: AppComponent) {
        injector.injectActivity(this)
    }

    override fun observe(lifecycleOwner: LifecycleOwner) {
        with(syncViewModel) {
            syncStatusLiveData.observe(lifecycleOwner) { status ->
                if (status is Synced) {
                    syncBinding.syncNextButton.isInvisible = false
                    syncBinding.syncLottieView.let {
                        it.setAnimation(R.raw.ok_done)
                        it.loop(true)
                        it.playAnimation()
                    }
                }

                syncBinding.syncProgressText.text = getString(status.messageRes)
            }

            navigateMainLiveData.observe(lifecycleOwner) {
                if (it) startActivity(MainActivity::class.java, isFinishRequired = true)
            }
        }
    }
}