package com.capstone.cuansampah.view.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.capstone.cuansampah.R
import com.capstone.cuansampah.data.remote.response.ProfileResponse
import com.capstone.cuansampah.data.remote.response.ResultResponse
import com.capstone.cuansampah.databinding.FragmentProfileBinding
import com.capstone.cuansampah.utils.getImageUri
import com.capstone.cuansampah.view.onboarding.OnboardingActivity
import com.capstone.cuansampah.view.viewModel.AuthViewModel
import com.capstone.cuansampah.view.viewModel.ViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.yalantis.ucrop.UCrop
import java.io.File

class ProfileFragment : Fragment() {

    private val viewModel: AuthViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private var currentImageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        setupAction()
        setupSettingAction()
    }

    private fun setupObservers() {
        viewModel.getSession().observe(viewLifecycleOwner) { session ->
            session?.let { user ->
                val cookie = "jwt=${user.token}"
                viewModel.profile(user.token, cookie)
                    .observe(viewLifecycleOwner) { profileResult ->
                        when (profileResult) {
                            is ResultResponse.Success -> {
                                updateProfileUI(profileResult.data)
                            }

                            is ResultResponse.Error -> {
                                Snackbar.make(requireView(), "Failed to load profile: ${profileResult.error}", Snackbar.LENGTH_LONG)
                                    .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.on_error))
                                    .show()
                            }

                            ResultResponse.Loading -> {

                            }
                        }
                    }
            }
        }
    }

    private fun updateProfileUI(profileResponse: ProfileResponse) {
        profileResponse.data?.let { data ->
            val imageSource = data.image ?: R.drawable.ic_profpic

            Glide.with(this)
                .load(imageSource)
                .into(binding.profilePicture)

            binding.tvUsername.text = data.username ?: "{username}"
            binding.tvLocation.text = data.email ?: "{address}"
        }
    }

    private fun setupAction() {
        binding.fabEdit.setOnClickListener {
            showBottomSheet()
        }
    }

    private fun setupSettingAction() {
        binding.btnProfileSettings.setOnClickListener {

        }
        binding.btnYourItems.setOnClickListener {

        }
        binding.btnYourLocation.setOnClickListener {

        }
        binding.btnNearbyCollectors.setOnClickListener {

        }
        binding.btnLogout.setOnClickListener {
            viewModel.logout()
            val intent = Intent(activity, OnboardingActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }

    private fun showBottomSheet() {
        val view = layoutInflater.inflate(R.layout.action_bottom_sheet, null)
        val dialog = BottomSheetDialog(requireContext())
        dialog.setContentView(view)

        val btnCamera = view.findViewById<Button>(R.id.btnCamera)
        val btnGallery = view.findViewById<Button>(R.id.btnGallery)

        btnCamera.setOnClickListener {
            dialog.dismiss()
            startCamera()
        }

        btnGallery.setOnClickListener {
            dialog.dismiss()
            startGallery()
        }
        dialog.show()
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            currentImageUri?.let { startCrop(it) }
        }
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            startCrop(uri)
        } else {
            Log.d("Photo Picker", getString(R.string.empty_image))
        }
    }

    private fun startCamera() {
        val photoURI: Uri = getImageUri(requireContext())
        photoURI.let {
            currentImageUri = it
            launcherIntentCamera.launch(it)
        }
    }

    private fun startGallery() {
        launcherGallery.launch(
            PickVisualMediaRequest(
                ActivityResultContracts.PickVisualMedia.ImageOnly
            )
        )
    }

    private fun startCrop(sourceUri: Uri) {
        val destinationUri = Uri.fromFile(File(requireContext().cacheDir, "cropped_${System.currentTimeMillis()}.jpg"))
        UCrop.of(sourceUri, destinationUri)
            .withAspectRatio(1f, 1f)
            .withMaxResultSize(1080, 1080)
            .start(requireActivity(), this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == AppCompatActivity.RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            val resultUri = UCrop.getOutput(data!!)
            if (resultUri != null) {
                currentImageUri = resultUri
//                showImage()
            }
        } else if (resultCode == UCrop.RESULT_ERROR) {
            val cropError = UCrop.getError(data!!)
            cropError?.let { showToast(it.message ?: "Crop error") }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
