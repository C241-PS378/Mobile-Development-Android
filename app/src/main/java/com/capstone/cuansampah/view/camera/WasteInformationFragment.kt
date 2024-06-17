package com.capstone.cuansampah.view.camera

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.capstone.cuansampah.R
import com.capstone.cuansampah.databinding.FragmentWasteInformationBinding

class WasteInformationFragment : Fragment() {
    private var _binding: FragmentWasteInformationBinding? = null
    private var imageUri: Uri? = null
    private var address: String? = null
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
        lon = arguments?.getDouble("lon")
        binding.userAddress.text = address
        binding.openMap.setOnClickListener{
            findNavController().navigate(R.id.navigation_maps)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}