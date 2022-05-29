package ua.vadymmy.it.words.ui.adapters.pagers

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import ua.vadymmy.it.words.ui.activities.main.MainActivity
import ua.vadymmy.it.words.ui.activities.main.NavigationTab
import ua.vadymmy.it.words.ui.activities.main.NavigationTab.ALL_KITS
import ua.vadymmy.it.words.ui.activities.main.NavigationTab.HOME
import ua.vadymmy.it.words.ui.activities.main.NavigationTab.MY_KITS
import ua.vadymmy.it.words.ui.fragments.LearningKitsFragment
import ua.vadymmy.it.words.ui.fragments.MainFragment
import ua.vadymmy.it.words.ui.fragments.PredefinedKitsFragment

class MainPagerAdapter(val mainActivity: MainActivity) : FragmentStateAdapter(mainActivity) {

    override fun createFragment(position: Int): Fragment =
        when (NavigationTab.from(position)) {
            HOME -> MainFragment()
            MY_KITS -> LearningKitsFragment()
            ALL_KITS -> PredefinedKitsFragment()
        }

    override fun getItemCount(): Int = NavigationTab.amount
}