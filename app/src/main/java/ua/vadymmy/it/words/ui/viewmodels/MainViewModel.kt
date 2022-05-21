package ua.vadymmy.it.words.ui.viewmodels

import androidx.lifecycle.viewModelScope
import javax.inject.Inject
import kotlinx.coroutines.launch
import ua.vadymmy.it.words.ui.common.BaseViewModel

class MainViewModel @Inject constructor(
) : BaseViewModel() {

    init {
        load()
    }

    private fun load() {
        viewModelScope.launch {
        }
    }

}