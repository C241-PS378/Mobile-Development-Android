package com.capstone.cuansampah.view.camera
//
//import android.content.Intent
//import android.net.Uri
//import android.os.Build
//import android.os.Bundle
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.annotation.RequiresExtension
//import androidx.fragment.app.Fragment
//import androidx.lifecycle.ViewModelProvider
//import androidx.navigation.fragment.findNavController
//import com.bumptech.glide.Glide
//import com.bumptech.glide.load.resource.bitmap.RoundedCorners
//import com.bumptech.glide.request.RequestOptions
//import com.capstone.cuansampah.R
//import com.capstone.cuansampah.data.remote.response.ResultResponse
//import com.capstone.cuansampah.databinding.FragmentWasteInformationBinding
//import com.capstone.cuansampah.utils.uriToFile
//
//
//class WasteInformationFragment : Fragment() {
//    private var _binding: FragmentWasteInformationBinding? = null
//    private var imageUri: Uri? = null
//    private var collector: Boolean? = null
//    private var openMap: Boolean? = null
//    private var address: String? = null
//    private var collector_address: String? = null
//    private var lat: Double? = null
//    private var lon: Double? = null
//    private val binding get() = _binding!!
//    private lateinit var viewModel: ImageClassificationViewModel
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        _binding = FragmentWasteInformationBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        val factory = ImageClassificationViewModelFactory.getInstance(this)
//        viewModel = ViewModelProvider(this, factory)[ImageClassificationViewModel::class.java]
//        imageUri = arguments?.getParcelable("imageUri")
//        imageUri?.let { uri ->
//            Glide.with(this)
//                .load(uri)
//                .apply(RequestOptions().transform(RoundedCorners(16))) // 16 is the radius in pixels
//                .into(binding.imageWaste)
//        }
//        address = arguments?.getString("address")
//        lat = arguments?.getDouble("lat")
//        collector = arguments?.getBoolean("collector")
//        lon = arguments?.getDouble("lon")
//        collector_address = arguments?.getString("collector_address")
//        openMap = arguments?.getBoolean("openMap")
//        binding.sellerAddress.text = address
//        binding.collectorAddress.text = collector_address
//        if(address!=""){
//            hideMap(true)
//        }
//        val imageFile = imageUri?.let { uriToFile(it, requireContext()) }
//        if (imageFile != null) {
//            viewModel.uploadImage(imageFile).observe(viewLifecycleOwner) { response ->
//                if (response != null) {
//                    when (response) {
//                        is ResultResponse.Loading -> {
//                            showLoading(true)
//                            Log.d("Info", "loading")
//                        }
//                        is ResultResponse.Success -> {
//                            showLoading(false)
//                            Log.d("Info", "success: ${response.data}")
//                            binding.productName.text = response.data.nama.toString()
//                            binding.pbContent.text = response.data.bahaya.toString()
//                            binding.ppContent.text = response.data.pengolahan.toString()
//                            binding.pkContent.text = response.data.jenis_sampah.toString()
//                        }
//                        is ResultResponse.Error -> {
//                            Log.d("InfoError", response.toString())
//                        }
//                    }
//                }else{
//                    Log.d("Info", "response is null")
//                }
//            }
//        }
//        if (collector == false) {
//            binding.btnSellWaste.text = getString(R.string.sell_to_market)
//            binding.btnSellWaste.setOnClickListener {
//                findNavController().navigate(R.id.navigation_market)
//            }
//        } else {
//            showCollectorAddress(true)
//            binding.collectorAddress.text= collector_address
//        }
//        binding.openMap.setOnClickListener {
//            val intent = Intent(context, MapsActivity::class.java).apply {
//                putExtra("imageUri", imageUri)
//                collector?.let { putExtra("collector", it) }
//            }
//            startActivity(intent)
//        }
//
//    }
//
//    private fun showCollectorAddress(isShow: Boolean) {
//        binding.titleAddress.visibility = if (isShow) View.VISIBLE else View.GONE
//        binding.titleCollectorAddress.visibility = if (isShow) View.VISIBLE else View.GONE
//        binding.collectorAddress.visibility  = if (isShow) View.VISIBLE else View.GONE
//    }
//
//    private fun showLoading(isLoading: Boolean) {
//        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
//    }
//
//    private fun hideMap(isShow: Boolean) {
//        binding.openMap.visibility = if (isShow) View.VISIBLE else View.GONE
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//}