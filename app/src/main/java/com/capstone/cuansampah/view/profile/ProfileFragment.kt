package com.capstone.cuansampah.view.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.capstone.cuansampah.R
import com.capstone.cuansampah.databinding.FragmentProfileBinding
import com.capstone.cuansampah.view.login.LoginViewModel
import com.capstone.cuansampah.view.onboarding.OnboardingActivity
import com.capstone.cuansampah.view.viewModel.ViewModelFactory

class ProfileFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

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
            viewModel.logout()
            val intent = Intent(activity, OnboardingActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
