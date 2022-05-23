package ua.vadymmy.it.words.ui.activities.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import ua.vadymmy.it.words.R

enum class NavigationTab(@StringRes val titleRes: Int, @DrawableRes val iconRes: Int) {
    HOME(R.string.main_tab_home, R.drawable.ic_home),
    MY_KITS(R.string.main_tab_my_kits, R.drawable.ic_learning),
    ALL_KITS(R.string.main_tab_all_kits, R.drawable.ic_kits);

    companion object{
        val amount get() = values().size
        fun from(position: Int) = values()[position]
    }
}