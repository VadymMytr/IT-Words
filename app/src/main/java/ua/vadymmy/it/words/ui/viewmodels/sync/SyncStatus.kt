package ua.vadymmy.it.words.ui.viewmodels.sync

import androidx.annotation.StringRes
import ua.vadymmy.it.words.R

sealed class SyncStatus(@StringRes val messageRes: Int) {
    object SyncingKits : SyncStatus(R.string.syncing_kits)
    object SyncingProgress : SyncStatus(R.string.syncing_progress)
    object Synced : SyncStatus(R.string.syncing_end)
}
