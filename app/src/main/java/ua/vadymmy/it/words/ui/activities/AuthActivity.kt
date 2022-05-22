package ua.vadymmy.it.words.ui.activities

import android.view.LayoutInflater
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.LifecycleOwner
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import javax.inject.Inject
import ua.vadymmy.it.words.R
import ua.vadymmy.it.words.databinding.ActivityAuthBinding
import ua.vadymmy.it.words.di.AppComponent
import ua.vadymmy.it.words.ui.common.BaseActivity
import ua.vadymmy.it.words.ui.viewmodels.AuthViewModel
import ua.vadymmy.it.words.utils.showToast
import ua.vadymmy.it.words.utils.startActivity

class AuthActivity : BaseActivity() {

    @Inject
    lateinit var viewModel: AuthViewModel
    private lateinit var binding: ActivityAuthBinding

    @Inject
    lateinit var signInOptions: GoogleSignInOptions
    private val signInClient by lazy { GoogleSignIn.getClient(this, signInOptions) }
    private val signInRequest =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            viewModel.onSignInRequestResult(activityResult.data)
        }

    override fun configureViews() {
        binding.authLoginButton.setOnClickListener {
            viewModel.onSignInClick()
        }
    }

    override fun inflateBinding(layoutInflater: LayoutInflater) {
        binding = ActivityAuthBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
    }

    override fun injectActivity(injector: AppComponent) {
        injector.injectActivity(this)
    }

    override fun observe(lifecycleOwner: LifecycleOwner) {
        with(viewModel) {
            onSuccessLiveData.observe(lifecycleOwner) {
                if (it) {
                    startActivity(SyncActivity::class.java, isFinishRequired = true)
                }
            }

            onFailureLiveData.observe(lifecycleOwner) {
                if (it) {
                    showToast(R.string.failure_auth)
                }
            }

            requestGoogleSignInLiveData.observe(lifecycleOwner) {
                if (it) signInRequest.launch(signInClient.signInIntent)
            }

            requestFirebaseSignInLiveData.observe(lifecycleOwner) {
                if (it.isNotEmpty()) viewModel.requestFirebaseAuth(it, this@AuthActivity)
            }
        }
    }
}