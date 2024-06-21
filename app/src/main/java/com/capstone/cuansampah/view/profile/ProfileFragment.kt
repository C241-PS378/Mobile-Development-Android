package com.capstone.cuansampah.view.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import com.bumptech.glide.Glide
import com.capstone.cuansampah.R
import com.capstone.cuansampah.databinding.FragmentProfileBinding
import com.capstone.cuansampah.view.onboarding.OnboardingActivity
import com.capstone.cuansampah.view.pickup.PickupFragment

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupHistoryAction()
        setupSettingAction()
    }

    private fun setupView() {
        Glide.with(this)
            .load(R.drawable.logo_app)
            .into(binding.profilePicture)
        binding.tvUsername.text = "John Doe"
        binding.tvLocation.text = "Jakarta"
    }

    private fun setupHistoryAction() {
        binding.pickedupButton.setOnClickListener {

        }
        binding.soldButton.setOnClickListener {}
        binding.boughtButton.setOnClickListener {}
    }

    private fun setupSettingAction() {
        binding.btnProfileSettings.setOnClickListener {}
        binding.btnYourItems.setOnClickListener {}
        binding.btnYourLocation.setOnClickListener {}
        binding.btnNearbyCollectors.setOnClickListener {}
        binding.btnLogout.setOnClickListener {
            startActivity(Intent(context, OnboardingActivity::class.java))
            activity?.finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
