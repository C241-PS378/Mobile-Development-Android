package com.capstone.cuansampah.view.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.capstone.cuansampah.R
import com.capstone.cuansampah.databinding.ActivityHomeBinding
import com.capstone.cuansampah.view.adapter.SliderHomeAdapter
import com.capstone.cuansampah.data.local.ItemImageSlider


class HomeFragment : Fragment() {
    private lateinit var viewPager: ViewPager
    private var _binding: ActivityHomeBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ActivityHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val item = mutableListOf<ItemImageSlider>()
        item.add(0, ItemImageSlider("Carousel 1", R.drawable.carousel1))
        item.add(1, ItemImageSlider("Carousel 2", R.drawable.carousel2))
        item.add(2, ItemImageSlider("Carousel 3", R.drawable.carousel3))

        val viewPagerAdapter = SliderHomeAdapter(requireContext(), item)
        binding.viewPager.adapter = viewPagerAdapter
        viewPagerAdapter.autoslide(binding.viewPager)
        binding.tabLayout.setupWithViewPager(binding.viewPager)

    }

}