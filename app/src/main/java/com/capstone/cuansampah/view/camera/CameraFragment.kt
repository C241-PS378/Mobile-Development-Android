package com.capstone.cuansampah.view.camera

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.capstone.cuansampah.R
import com.capstone.cuansampah.databinding.FragmentCameraBinding
import com.capstone.cuansampah.utils.getImageUri

class CameraFragment : Fragment() {
    private var currentImageUri: Uri? = null
    private var _binding: FragmentCameraBinding? = null

    private val binding get() = _binding!!
    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
        }
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", getString(R.string.empty_image))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCameraBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showBtnImage(true)
        binding.cameraBtn.setOnClickListener {
            startCamera()
        }
        binding.galleryBtn.setOnClickListener {
            startGallery()
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

    private fun showImage() {
        currentImageUri?.let { uri ->
            Log.d("Image URI", "showImage: $uri")
            currentImageUri?.let { uri ->
                Glide.with(this)
                    .load(uri)
                    .apply(RequestOptions().transform(RoundedCorners(16))) // 16 is the radius in pixels
                    .into(binding.imagePicker)
            }
            showBtnImage(false)
            showBtnChoose(true)
            binding.sellCollector.setOnClickListener {
                val bundle = Bundle().apply {
                    putParcelable("imageUri", uri)
                    putBoolean("collector", true)
                }
                findNavController().navigate(R.id.waste_information, bundle)
            }

            binding.sellMarket.setOnClickListener {
                val bundle = Bundle().apply {
                    putParcelable("imageUri", uri)
                    putBoolean("collector", false)
                }
                findNavController().navigate(R.id.waste_information, bundle)
            }
        }
    }

    private fun showBtnImage(isShow: Boolean) {
        binding.cameraBtn.visibility = if (isShow) View.VISIBLE else View.GONE
        binding.galleryBtn.visibility = if (isShow) View.VISIBLE else View.GONE
    }

    private fun showBtnChoose(isShow: Boolean) {
        binding.sellCollector.visibility = if (isShow) View.VISIBLE else View.GONE
        binding.sellMarket.visibility = if (isShow) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
