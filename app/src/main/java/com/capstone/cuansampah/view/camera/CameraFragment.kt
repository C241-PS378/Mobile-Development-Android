package com.capstone.cuansampah.view.camera

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.capstone.cuansampah.R
import com.capstone.cuansampah.utils.getImageUri

class CameraFragment : Fragment() {

    private lateinit var btnCapture: Button
    private lateinit var btnTakeGallery: Button
    private lateinit var imgCaptured: ImageView
    private var currentImageUri: Uri? = null

    companion object {
        private const val REQUEST_CAMERA_PERMISSION = 101
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_camera, container, false)
        btnCapture = view.findViewById(R.id.cameraBtn)
        btnTakeGallery = view.findViewById(R.id.galleryBtn)
        imgCaptured = view.findViewById(R.id.imagePicker)
        btnCapture.setOnClickListener {
            startCamera()
        }
        btnTakeGallery.setOnClickListener {
            startGallery()
        }
        return view
    }

    private fun startCamera() {
        val photoURI: Uri = getImageUri(requireContext())
        photoURI.let {
            currentImageUri = it
            launcherIntentCamera.launch(it)
        }
    }

    private fun showImage() {
        currentImageUri?.let { uri ->
            Log.d("Image URI", "showImage: $uri")
            view?.findViewById<ImageView>(R.id.imagePicker)?.setImageURI(uri)
        }
    }

    private fun startGallery() {
        launcherGallery.launch(
            PickVisualMediaRequest(
                ActivityResultContracts.PickVisualMedia.ImageOnly
            )
        )
    }

    private val launcherGallery =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri: Uri? ->
            if (uri != null) {
                currentImageUri = uri
                showImage()
            } else {
                Log.d("Photo Picker", getString(R.string.empty_image))
            }
        }
}