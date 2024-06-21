package com.capstone.cuansampah.view.camera
//
//import android.Manifest
//import android.content.pm.PackageManager
//import android.location.Address
//import android.location.Geocoder
//import android.location.Location
//import android.net.Uri
//import android.os.Build
//import android.os.Bundle
//import android.os.Looper
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.AdapterView
//import android.widget.ArrayAdapter
//import android.widget.Spinner
//import android.widget.Toast
//import androidx.core.content.ContextCompat
//import androidx.fragment.app.Fragment
//import androidx.navigation.fragment.findNavController
//import com.capstone.cuansampah.R
//import com.capstone.cuansampah.data.local.User
//import com.capstone.cuansampah.databinding.FragmentMapsBinding
//import com.google.android.gms.common.ConnectionResult
//import com.google.android.gms.common.api.GoogleApiClient
//import com.google.android.gms.location.FusedLocationProviderClient
//import com.google.android.gms.location.LocationCallback
//import com.google.android.gms.location.LocationListener
//import com.google.android.gms.location.LocationRequest
//import com.google.android.gms.location.LocationResult
//import com.google.android.gms.location.LocationServices
//import com.google.android.gms.maps.CameraUpdateFactory
//import com.google.android.gms.maps.GoogleMap
//import com.google.android.gms.maps.OnMapReadyCallback
//import com.google.android.gms.maps.SupportMapFragment
//import com.google.android.gms.maps.model.BitmapDescriptorFactory
//import com.google.android.gms.maps.model.LatLng
//import com.google.android.gms.maps.model.LatLngBounds
//import com.google.android.gms.maps.model.Marker
//import com.google.android.gms.maps.model.MarkerOptions
//import java.io.IOException
//import java.util.Locale
//
//class MapsFragment : Fragment(), OnMapReadyCallback,
//    LocationListener, GoogleApiClient.ConnectionCallbacks,
//    GoogleApiClient.OnConnectionFailedListener {
//
//    private var mMap: GoogleMap? = null
//    private var imageUri: Uri? = null
//    private lateinit var mLastLocation: Location
//    private lateinit var mLocationRequest: LocationRequest
//    private var mCurrLocationMarker: Marker? = null
//    private var mGoogleApiClient: GoogleApiClient? = null
//    private var mFusedLocationClient: FusedLocationProviderClient? = null
//    private var _binding: FragmentMapsBinding? = null
//    private var collector: Boolean? = null
//    private val boundsBuilder = LatLngBounds.Builder()
//    private lateinit var spinner: Spinner
//    private val binding get() = _binding!!
//
//    private val locationCallback = object : LocationCallback() {
//        override fun onLocationResult(locationResult: LocationResult) {
//            for (location in locationResult.locations) {
//                onLocationChanged(location)
//            }
//        }
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        _binding = FragmentMapsBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
//        mapFragment.getMapAsync(this)
//        collector = arguments?.getBoolean("collector")
//        Log.d("Cocc", collector.toString())
//        if (collector == true) {
//            showCollectorAddress(true)
//            spinner = binding.spinnerLocations
//            val collectorPlace = listOf(
//                User(
//                    id = 1,
//                    username = "Collector 1",
//                    email = "collector1@gmail.com",
//                    phone_number = "083456789711",
//                    password = "cek123456",
//                    image = null,
//                    role = "collector",
//                    address = "Bank Sampah Mandiri",
//                    -7.736683221946488,
//                    110.43213791398486
//                ),
//                User(
//                    id = 2,
//                    username = "Collector 2",
//                    email = "collector2@gmail.com",
//                    phone_number = "083456789712",
//                    password = "cek1234578",
//                    image = null,
//                    role = "collector",
//                    address = "Bank Sampah Madusela",
//                    -6.217542648114002,
//                    106.84881677651752
//                ),
//                User(
//                    id = 3,
//                    username = "Collector 3",
//                    email = "collector3@gmail.com",
//                    phone_number = "083456789711",
//                    password = "cek1234569",
//                    image = null,
//                    role = "collector",
//                    address = "Bank Sampah Satya Wira Bakti",
//                    -7.449105210774321,
//                    110.73259891851151
//                ),
//            )
//            val addresses = collectorPlace.map { it.address }.toTypedArray()
//            val adapter =
//                ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, addresses)
//            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//            spinner.adapter = adapter
//            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//            spinner.adapter = adapter
//            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//                override fun onItemSelected(
//                    parent: AdapterView<*>?,
//                    view: View?,
//                    position: Int,
//                    id: Long
//                ) {
//                    val selectedItem = addresses[position]
//                    val selectCollector = collectorPlace[position]
//                    val selectedCollector = selectCollector.address
//                    Log.d("DIdalam", selectedCollector)
//                    showToast("Selected: $selectedItem")
//                    Log.d("collector_address", selectedCollector.toString())
//                    binding.btnSaveAddress.setOnClickListener {
//                        val address = binding.edAddress.text.toString()
//                        imageUri = arguments?.getParcelable("imageUri")
//                        val bundle = Bundle().apply {
//                            putParcelable("imageUri", imageUri)
//                            putString("collector_address", selectedCollector)
//                            putString("address", address)
//                            putDouble("latitude", mLastLocation.latitude)
//                            putDouble("longitude", mLastLocation.longitude)
//                            putBoolean("collector",true )
//                            putBoolean("openMap",false)
//                        }
//                        findNavController().navigate(R.id.waste_information, bundle)
//                    }
//                }
//
//                override fun onNothingSelected(parent: AdapterView<*>?) {
//                    // Aksi jika tidak ada yang dipilih
//                }
//            }
//
//        } else {
//            binding.btnSaveAddress.setOnClickListener {
//                val address = binding.edAddress.text.toString()
//                imageUri = arguments?.getParcelable("imageUri")
//                val bundle = Bundle().apply {
//                    putParcelable("imageUri", imageUri)
//                    putString("address", address)
//                    putDouble("latitude", mLastLocation.latitude)
//                    putDouble("longitude", mLastLocation.longitude)
//                    putBoolean("collector",false )
//                    putBoolean("openMap",false)
//                }
//                findNavController().navigate(R.id.waste_information, bundle)
//            }
//        }
//    }
//
//    private fun showCollectorAddress(isShow: Boolean) {
//        binding.titleAddress.visibility = if (isShow) View.VISIBLE else View.GONE
//        binding.spinnerLocations.visibility = if (isShow) View.VISIBLE else View.GONE
//    }
//
//    private fun showToast(message: String) {
//        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
//    }
//
//
//    override fun onMapReady(googleMap: GoogleMap) {
//        mMap = googleMap
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (ContextCompat.checkSelfPermission(
//                    requireContext(),
//                    Manifest.permission.ACCESS_FINE_LOCATION
//                ) == PackageManager.PERMISSION_GRANTED
//            ) {
//                buildGoogleApiClient()
//                mMap!!.isMyLocationEnabled = true
//            } else {
//                requestPermissions(
//                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
//                    LOCATION_REQUEST_CODE
//                )
//            }
//        } else {
//            buildGoogleApiClient()
//            mMap!!.isMyLocationEnabled = true
//        }
//
//        mMap?.setOnMapLongClickListener { latLng ->
//            mMap?.clear()
//            mMap?.addMarker(MarkerOptions().position(latLng).title("Selected Location"))
//            mMap?.moveCamera(CameraUpdateFactory.newLatLng(latLng))
//            displayAddress(latLng)
//        }
//    }
//
//    @Synchronized
//    private fun buildGoogleApiClient() {
//        mGoogleApiClient = GoogleApiClient.Builder(requireContext())
//            .addConnectionCallbacks(this)
//            .addOnConnectionFailedListener(this)
//            .addApi(LocationServices.API).build()
//        mGoogleApiClient!!.connect()
//    }
//
//    override fun onConnected(bundle: Bundle?) {
//        mLocationRequest = LocationRequest.create().apply {
//            interval = 1000
//            fastestInterval = 1000
//            priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
//        }
//
//        if (ContextCompat.checkSelfPermission(
//                requireContext(),
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) == PackageManager.PERMISSION_GRANTED
//        ) {
//            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
//            mFusedLocationClient?.requestLocationUpdates(
//                mLocationRequest,
//                locationCallback,
//                Looper.myLooper()
//            )
//        }
//    }
//
//    override fun onLocationChanged(location: Location) {
//        mLastLocation = location
//        if (mCurrLocationMarker != null) {
//            mCurrLocationMarker!!.remove()
//        }
//
//        // Place current location marker
//        val latLng = LatLng(location.latitude, location.longitude)
//        val markerOptions = MarkerOptions()
//        markerOptions.position(latLng)
//        markerOptions.title("Current Position")
//        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
//        mCurrLocationMarker = mMap!!.addMarker(markerOptions)
//
//        // Move map camera
//        mMap!!.moveCamera(CameraUpdateFactory.newLatLng(latLng))
//        mMap!!.animateCamera(CameraUpdateFactory.zoomTo(11f))
//        // Stop location updates
//        if (mGoogleApiClient != null) {
//            mFusedLocationClient?.removeLocationUpdates(locationCallback)
//        }
//        if (collector == true) {
//            addManyMarker()
//        }
//    }
//
//    private fun displayAddress(latLng: LatLng) {
//        val geocoder = Geocoder(requireContext(), Locale.getDefault())
//        try {
//            val addressList: List<Address>? =
//                geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
//            if (!addressList.isNullOrEmpty()) {
//                val address = addressList[0]
//                binding.edAddress.setText(address.getAddressLine(0))
//            } else {
//                binding.edAddress.setText("No address found")
//            }
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//    }
//
//    private fun addManyMarker() {
//        val collectorPlace = listOf(
//            User(
//                id = 1,
//                username = "Collector 1",
//                email = "collector1@gmail.com",
//                phone_number = "083456789711",
//                password = "cek123456",
//                image = null,
//                role = "collector",
//                address = "Bank Sampah Mandiri",
//                -7.736683221946488,
//                110.43213791398486
//            ),
//            User(
//                id = 2,
//                username = "Collector 2",
//                email = "collector2@gmail.com",
//                phone_number = "083456789712",
//                password = "cek1234578",
//                image = null,
//                role = "collector",
//                address = "Bank Sampah Madusela",
//                -6.217542648114002,
//                106.84881677651752
//            ),
//            User(
//                id = 3,
//                username = "Collector 3",
//                email = "collector3@gmail.com",
//                phone_number = "083456789711",
//                password = "cek1234569",
//                image = null,
//                role = "collector",
//                address = "Bank Sampah Satya Wira Bakti",
//                -7.449105210774321,
//                110.73259891851151
//            ),
//        )
//        collectorPlace.forEach { collector ->
//            val latLng = LatLng(collector.latitude, collector.longitude)
//            mMap?.addMarker(MarkerOptions().position(latLng).title(collector.address))
//            boundsBuilder.include(latLng)
//        }
//
//        val bounds: LatLngBounds = boundsBuilder.build()
//        mMap?.animateCamera(
//            CameraUpdateFactory.newLatLngBounds(
//                bounds,
//                resources.displayMetrics.widthPixels,
//                resources.displayMetrics.heightPixels,
//                300
//            )
//        )
//    }
//
//    override fun onConnectionSuspended(i: Int) {
//        mGoogleApiClient?.connect()
//    }
//
//    override fun onConnectionFailed(connectionResult: ConnectionResult) {
//        Log.e("MapsFragment", "Connection to GoogleApiClient failed")
//    }
//
//    fun searchLocation() {
//        val location = "Bank Sampah"
//        val geoCoder = Geocoder(requireContext())
//        try {
//            val addressList = geoCoder.getFromLocationName(location, 1)
//            if (addressList.isNullOrEmpty()) {
//                Toast.makeText(requireContext(), "No location found", Toast.LENGTH_SHORT).show()
//                return
//            }
//            val address = addressList[0]
//            val latLng = LatLng(address.latitude, address.longitude)
//            mMap!!.addMarker(MarkerOptions().position(latLng).title(location))
//            mMap!!.animateCamera(CameraUpdateFactory.newLatLng(latLng))
//            Toast.makeText(
//                requireContext(),
//                "${address.latitude} ${address.longitude}",
//                Toast.LENGTH_LONG
//            ).show()
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//    }
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<String>,
//        grantResults: IntArray
//    ) {
//        when (requestCode) {
//            LOCATION_REQUEST_CODE -> {
//                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    if (ContextCompat.checkSelfPermission(
//                            requireContext(),
//                            Manifest.permission.ACCESS_FINE_LOCATION
//                        ) == PackageManager.PERMISSION_GRANTED
//                    ) {
//                        buildGoogleApiClient()
//                        mMap!!.isMyLocationEnabled = true
//                    }
//                } else {
//                    Toast.makeText(requireContext(), "Permission Denied", Toast.LENGTH_SHORT).show()
//                }
//                return
//            }
//        }
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//
//    companion object {
//        private const val LOCATION_REQUEST_CODE = 101
//    }
//}
