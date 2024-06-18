package com.capstone.cuansampah.view.camera

import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.annotation.RequiresExtension
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.capstone.cuansampah.R
import com.capstone.cuansampah.databinding.FragmentWasteInformationBinding
import java.io.File

class WasteInformationFragment : Fragment() {
    private var _binding: FragmentWasteInformationBinding? = null
    private var imageUri: Uri? = null
    private var collector: Boolean? = null
    private var address: String? = null
    private var imageFile: File? = null
    private var collector_address: String? = null
    private var lat: Double? = null
    private var lon: Double? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWasteInformationBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageUri = arguments?.getParcelable("imageUri")
        imageUri?.let { uri ->
            Glide.with(this)
                .load(uri)
                .apply(RequestOptions().transform(RoundedCorners(16))) // 16 is the radius in pixels
                .into(binding.imageWaste)
        }
        address = arguments?.getString("address")
        lat = arguments?.getDouble("lat")
        collector = arguments?.getBoolean("collector")
        lon = arguments?.getDouble("lon")
        collector_address = arguments?.getString("collector_address")
        imageFile = arguments?.getSerializable("image_file") as File?
        binding.sellerAddress.text = address
        binding.collectorAddress.text = collector_address
        if (collector == false) {
            binding.btnSellWaste.text = "Sell to Market"
        } else {
            showCollectorAddress(true)
        }
        binding.openMap.setOnClickListener {
            val bundle = Bundle().apply {
                putParcelable("imageUri", imageUri)
                collector?.let { it1 -> putBoolean("collector", it1) }
            }
            findNavController().navigate(R.id.navigation_maps, bundle)
        }
    }

    private fun showCollectorAddress(isShow: Boolean) {
        binding.titleAddress.visibility = if (isShow) View.VISIBLE else View.GONE
        binding.titleCollectorAddress.visibility = if (isShow) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}