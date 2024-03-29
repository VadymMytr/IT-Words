package ua.vadymmy.it.words.ui.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import javax.inject.Inject
import ua.vadymmy.it.words.R
import ua.vadymmy.it.words.databinding.FragmentMainBinding
import ua.vadymmy.it.words.di.AppComponent
import ua.vadymmy.it.words.domain.models.user.User
import ua.vadymmy.it.words.ui.activities.AuthActivity
import ua.vadymmy.it.words.ui.activities.SyncActivity
import ua.vadymmy.it.words.ui.activities.UserLevelsActivity
import ua.vadymmy.it.words.ui.common.BaseFragment
import ua.vadymmy.it.words.ui.viewmodels.MainFragmentViewModel
import ua.vadymmy.it.words.utils.loadFrom
import ua.vadymmy.it.words.utils.startActivity

class MainFragment : BaseFragment() {

    override val titleId: Int get() = R.string.main_tab_home

    @Inject
    lateinit var viewModel: MainFragmentViewModel
    private lateinit var binding: FragmentMainBinding

    override fun configureViews() {
        with(binding) {
            mainLogoutButton.setOnClickListener {
                viewModel.onLogoutClick()
            }

            mainUserLevelsDetailsButton.setOnClickListener {
                viewModel.onUserLevelsDetailsClick()
            }

            mainKitsButton.setOnClickListener {
                openMyKits()
            }

            mainSyncButton.setOnClickListener {
                startActivity(SyncActivity::class.java, isFinishRequired = true)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }

    override fun inflateBinding(layoutInflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)
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

            currentUserLiveData.observe(lifecycleOwner) { user ->
                fillUserData(user)
            }

            learningWordsAmountLiveData.observe(lifecycleOwner) { amount ->
                binding.mainWordsAmountText.text = amount.toString()
            }

            navigateAuthLiveData.observe(lifecycleOwner) {
                if (it) startActivity(
                    AuthActivity::class.java,
                    isFinishRequired = true
                )
            }

            navigateUserLevelsLiveData.observe(lifecycleOwner) {
                if (it) startActivity(UserLevelsActivity::class.java)
            }
        }
    }

    private fun fillUserData(user: User) {
        with(binding) {
            mainUserPhoto.loadFrom(user.photoUrl)
            mainUserEmail.text = user.email
            mainUserName.text = user.name

            val userLevel = user.level
            val currentProgress = user.learnProgress
            val maxProgress = userLevel.levelMax
            mainUserLevelNameText.setText(userLevel.titleRes)
            mainUserLevelProgress.max = maxProgress
            mainUserLevelProgress.setProgress(currentProgress, true)
            mainUserLevelProgressText.text = getString(
                R.string.main_user_level_placeholder,
                currentProgress,
                maxProgress
            )
            mainUserLevelProgressStartText.setText(userLevel.titleRes)
            mainUserLevelProgressEndText.setText(user.level.next.titleRes)
        }
    }
}