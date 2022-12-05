package com.example.xmlapi

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Location
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.xmlapi.Common.Common
import com.example.xmlapi.Model.MyPlaces
import com.example.xmlapi.Remote.IGoogleAPIService
import com.example.xmlapi.databinding.FragmentMapBinding
import com.example.xmlapi.viewmodel.DataViewModel
import com.google.android.gms.location.*

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Suppress("DEPRECATION")
class MapFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: FragmentMapBinding
    private var latitude: Double = 0.toDouble()
    private var longitude: Double = 0.toDouble()
    private lateinit var mLastLocation: Location
    private var mMarker: Marker?= null
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private lateinit var mService: IGoogleAPIService
    internal lateinit var currentPlace: MyPlaces
    private val model : DataViewModel by activityViewModels()

    companion object {
        private const val MY_PERMISSION_CODE: Int = 1000
    }

    @SuppressLint("MissingPermission")
    private val callback = OnMapReadyCallback { googleMap ->
        mMap = googleMap
        mService = Common.googleAPIService
        if(checkLocationPermission()) {
            buildLocationRequest()
            buildLocationCallBack()
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity as RealActivity)
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
        }
        binding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.action_cafe -> nearByPlace("cafe")
                R.id.action_restaurant -> nearByPlace("restaurant")
                R.id.action_subway -> nearByPlace("subway_station")
            }
            true
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    private fun nearByPlace(typePlace: String) {
        mMap.clear()
        val url = getUrl(latitude, longitude, typePlace)
        mService.getNearbyPlaces(url)
            .enqueue(object: Callback<MyPlaces> {
                override fun onResponse(call: Call<MyPlaces>, response: Response<MyPlaces>) {
                    currentPlace = response.body()!!
                    if (response.isSuccessful) {
                        for (i in 0 until response.body()!!.results!!.size) {
                            val markerOptions = MarkerOptions()
                            val googlePlace = response.body()!!.results!![i]
                            val lat = googlePlace.geometry!!.location!!.lat
                            val lng = googlePlace.geometry!!.location!!.lng
                            val placeName = googlePlace.name
                            val latLng = LatLng(lat, lng)
                            markerOptions.position(latLng)
                            markerOptions.title(placeName)
                            when (typePlace) {
                                "cafe" -> markerOptions.icon(bitmapDescriptorFromVector(activity as RealActivity, R.drawable.ic_cafe))
                                "restaurant" -> markerOptions.icon(bitmapDescriptorFromVector(activity as RealActivity, R.drawable.ic_restaurant))
                                "subway_station" -> markerOptions.icon(bitmapDescriptorFromVector(activity as RealActivity, R.drawable.ic_subway))
                                else -> markerOptions.icon(
                                    BitmapDescriptorFactory.defaultMarker(
                                        BitmapDescriptorFactory.HUE_BLUE))
                            }
                            markerOptions.snippet(typePlace)
                            mMap.addMarker(markerOptions)
                        }
                    }
                }
                override fun onFailure(call: Call<MyPlaces>, t: Throwable) {
                    Toast.makeText(activity as RealActivity,""+ t.message, Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun getUrl(latitude: Double, longitude: Double, typePlace: String): String {
        val googlePlaceUrl = StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json")
        googlePlaceUrl.append("?location=$latitude,$longitude")
        googlePlaceUrl.append("&radius=5000")
        googlePlaceUrl.append("&type=$typePlace")
        googlePlaceUrl.append("&key=AIzaSyAM-KgUMlHOGrOiJf6YI08tA5tokRDGLY4")
        Log.d("URL_DEBUG", googlePlaceUrl.toString())
        return googlePlaceUrl.toString()
    }

    private fun buildLocationCallBack() {
        locationCallback = object: LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                mLastLocation = p0.locations[p0.locations.size-1]
                if (mMarker != null) {
                    mMarker!!.remove()
                }
                latitude = mLastLocation.latitude
                longitude = mLastLocation.longitude
                val latLng = LatLng(latitude, longitude)
                val markerOptions = MarkerOptions()
                    .position(latLng)
                    .title("Current Position")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                mMarker = mMap.addMarker(markerOptions)
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14f))
                mMap.setOnInfoWindowClickListener(this@MapFragment)
            }
        }
    }

    private fun buildLocationRequest() {
        locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 5000
        locationRequest.fastestInterval = 3000
        locationRequest.smallestDisplacement = 10f
    }

    private fun checkLocationPermission(): Boolean {
        return if (ContextCompat.checkSelfPermission(activity as RealActivity, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity as RealActivity, android.Manifest.permission.ACCESS_FINE_LOCATION))
                ActivityCompat.requestPermissions(activity as RealActivity, arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ), MY_PERMISSION_CODE
                )
            else
                ActivityCompat.requestPermissions(activity as RealActivity, arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ), MY_PERMISSION_CODE
                )
            false
        } else
            true
    }


    @Deprecated("Deprecated in Java")
    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            MY_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(activity as RealActivity, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                        if (checkLocationPermission()) {
                            buildLocationRequest()
                            buildLocationCallBack()

                            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity as RealActivity)
                            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())

                            mMap.isMyLocationEnabled = true
                        }
                }
                else {
                    Toast.makeText(activity as RealActivity, "권한이 거부되었습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onStop() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
        super.onStop()
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        if (ContextCompat.checkSelfPermission(activity as RealActivity, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.isMyLocationEnabled = true
        }
        mMap.uiSettings.isZoomControlsEnabled = true
    }

    override fun onInfoWindowClick(marker: Marker) {
        model.setStoreName(marker.title!!)
        findNavController().navigate(R.id.action_mapFragment_to_reviewFragment2)
    }

    private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        return ContextCompat.getDrawable(context, vectorResId)?.run {
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
            val bitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
            draw(Canvas(bitmap))
            BitmapDescriptorFactory.fromBitmap(bitmap)
        }
    }
}