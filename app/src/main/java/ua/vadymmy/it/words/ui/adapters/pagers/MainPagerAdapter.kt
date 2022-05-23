package ua.vadymmy.it.words.ui.adapters.pagers

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import ua.vadymmy.it.words.ui.activities.main.MainActivity
import ua.vadymmy.it.words.ui.activities.main.NavigationTab
import ua.vadymmy.it.words.ui.activities.main.NavigationTab.ALL_KITS
import ua.vadymmy.it.words.ui.activities.main.NavigationTab.HOME
import ua.vadymmy.it.words.ui.activities.main.NavigationTab.MY_KITS
import ua.vadymmy.it.words.ui.fragments.MainFragment

class MainPagerAdapter(val mainActivity: MainActivity) : FragmentStateAdapter(mainActivity) {

    override fun createFragment(position: Int): Fragment =
        when (NavigationTab.from(position)) {
            HOME -> MainFragment()
            MY_KITS -> MainFragment()
            ALL_KITS -> MainFragment()
        }

    override fun getItemCount(): Int = NavigationTab.amount
}