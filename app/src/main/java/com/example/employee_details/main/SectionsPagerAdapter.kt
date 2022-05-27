package com.example.employee_details.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.employee_details.fragments.ui.AccountSettingFragment
import com.example.employee_details.fragments.ui.HomePageFragment
import com.example.employee_details.fragments.ui.MyProfileFragment
import com.example.employee_details.R
import java.util.ArrayList

//import com.example.base_task1.fragments.R

private val TAB_TITLES = arrayOf(
    R.string.tab_text_1,
    R.string.tab_text_2,
    R.string.tab_text_3
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */

class SectionsPagerAdapter(
    fa: FragmentManager,
    lc: Lifecycle,
    private var fragmentsList: ArrayList<Fragment>
) : FragmentStateAdapter(fa, lc) {
    fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return when(position){
            0 -> {
                HomePageFragment()
            }
            1 -> {
                MyProfileFragment()
            }
            2 -> {
                AccountSettingFragment()
            }
            else -> HomePageFragment()

        }
    }

    fun getPageTitle(position: Int) {
//        return context.resources.getString(TAB_TITLES[position])

    }

    override fun getItemCount(): Int = fragmentsList.size

    override fun createFragment(position: Int): Fragment {
        return fragmentsList[position]
    }
}
/*
(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return when(position){
            0 -> {
                HomePageFragment()
            }
            1 -> {
                MyProfileFragment()
            }
            2 -> {
                AccountSettingFragment()
            }
            else -> HomePageFragment()

        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        // Show 3 total pages.
        return 3
    }
}*/
