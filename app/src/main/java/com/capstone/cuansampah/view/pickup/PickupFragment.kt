package com.capstone.cuansampah.view.pickup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.capstone.cuansampah.R
import com.capstone.cuansampah.databinding.FragmentPickupBinding
import com.capstone.cuansampah.view.adapter.PickupPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class PickupFragment : Fragment() {

    private var _binding: FragmentPickupBinding? = null
    private val binding get() = _binding!!

    private lateinit var pickupPagerAdapter: PickupPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPickupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupTabs()
    }

    private fun setupTabs() {
        pickupPagerAdapter = PickupPagerAdapter(requireActivity())
        val viewPager: ViewPager2 = binding.pickupView
        viewPager.adapter = pickupPagerAdapter

        val tabs: TabLayout = binding.pickupTabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_all,
            R.string.tab_in_progress,
            R.string.tab_completed,
            R.string.tab_cancelled
        )
    }
}
