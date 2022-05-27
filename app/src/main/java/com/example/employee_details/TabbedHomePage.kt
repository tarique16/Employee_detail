package com.example.employee_details

import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.employee_details.main.SectionsPagerAdapter
import com.example.employee_details.databinding.ActivityTabbedHomePageBinding
import com.example.employee_details.fragments.ui.AccountSettingFragment
import com.example.employee_details.fragments.ui.HomePageFragment
import com.example.employee_details.fragments.ui.MyProfileFragment

class TabbedHomePage : AppCompatActivity() {

    private lateinit var binding: ActivityTabbedHomePageBinding
    private var fragmentsList = arrayListOf<Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTabbedHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        tabs.setupWithViewPager(viewPager)
        setPager()
    }

    private fun setPager(){
        fragmentsList.add(HomePageFragment())
        fragmentsList.add(MyProfileFragment())
        fragmentsList.add(AccountSettingFragment())
        val sectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager,lifecycle, fragmentsList)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        viewPager.isUserInputEnabled = false
        val tabs: TabLayout = binding.tabs
        tabs.addTab(tabs.newTab().setText(R.string.tab_text_1))
        tabs.addTab(tabs.newTab().setText(R.string.tab_text_2))
        tabs.addTab(tabs.newTab().setText(R.string.tab_text_3))
        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager.currentItem = tab!!.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                supportFragmentManager.findFragmentByTag("f" + viewPager.adapter?.getItemId(3))
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                TODO("Not yet implemented")
            }

        })

    }
}