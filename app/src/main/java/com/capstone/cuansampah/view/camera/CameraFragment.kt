package com.capstone.cuansampah.view.camera

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.capstone.cuansampah.R
import com.capstone.cuansampah.databinding.FragmentCameraBinding
import com.capstone.cuansampah.utils.getImageUri
import com.yalantis.ucrop.UCrop
import java.io.File

class CameraFragment : Fragment() {
    private var currentImageUri: Uri? = null
    private var _binding: FragmentCameraBinding? = null

    private val binding get() = _binding!!
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
                showImage()
            }
        } else if (resultCode == UCrop.RESULT_ERROR) {
            val cropError = UCrop.getError(data!!)
            cropError?.let { showToast(it.message ?: "Crop error") }
        }
    }

    private fun showImage() {
        currentImageUri?.let { uri ->
            Log.d("Image URI", "showImage: $uri")
            Glide.with(this)
                .load(uri)
                .apply(RequestOptions().transform(RoundedCorners(16)))
                .into(binding.imagePicker)

            showBtnImage(false)
            showBtnChoose(true)

            binding.sellCollector.setOnClickListener {
                val intent = Intent(requireContext(), WasteInformationActivity::class.java).apply {
                    putExtra("imageUri", uri)
                    putExtra("collector", true)
                }
                startActivity(intent)
            }

            binding.sellMarket.setOnClickListener {
                val intent = Intent(requireContext(), WasteInformationActivity::class.java).apply {
                    putExtra("imageUri", uri)
                    putExtra("collector", false)
                }
                startActivity(intent)
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

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
