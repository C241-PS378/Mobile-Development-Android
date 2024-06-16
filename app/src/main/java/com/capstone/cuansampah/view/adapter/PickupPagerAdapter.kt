package com.capstone.cuansampah.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.capstone.cuansampah.view.pickup.statusFragment.AllFragment
import com.capstone.cuansampah.view.pickup.statusFragment.CanceledFragment
import com.capstone.cuansampah.view.pickup.statusFragment.CompletedFragment
import com.capstone.cuansampah.view.pickup.statusFragment.InProgressFragment

class PickupPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = AllFragment()
            1 -> fragment = InProgressFragment()
            2 -> fragment = CompletedFragment()
            3 -> fragment = CanceledFragment()
        }
        return fragment as Fragment
    }

}