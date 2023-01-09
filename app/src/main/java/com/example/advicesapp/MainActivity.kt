package com.example.advicesapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.advicesapp.ui.main.SectionsPagerAdapter
import com.example.advicesapp.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private val tabTitles = arrayOf(
        R.string.tab_text_1,
        R.string.tab_text_2,
        R.string.tab_text_3,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(
            binding.tabs, viewPager
        ) { tab, position -> tab.setText(tabTitles[position]) }.attach()
        if (savedInstanceState == null)
            binding.tabs.selectTab(binding.tabs.getTabAt(1))
    }
}