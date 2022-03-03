package com.examen.carlosgs.ui.locations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.examen.carlosgs.R
import com.examen.carlosgs.adapter.LocationsAdapter
import com.examen.carlosgs.databinding.FragmentLocationsBinding
import com.examen.carlosgs.data.model.LocationModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class LocationsFragment : Fragment(), LocationsAdapter.LocationsAdapterListener,
    OnMapReadyCallback {

    private lateinit var locationsViewModel: LocationsViewModel
    private lateinit var mAdapter: LocationsAdapter
    private lateinit var map : GoogleMap

    private var _binding: FragmentLocationsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        locationsViewModel = ViewModelProvider(this)[LocationsViewModel::class.java]

        _binding = FragmentLocationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initComponents()
        initObservers()
        createMap()

        return root
    }

    private fun initObservers() {
        locationsViewModel.locationsListData.observe(requireActivity()) {
            mAdapter.setList(it)

            if(it.isNotEmpty()){

                binding.rvLocations.visibility = View.VISIBLE
                binding.tvEmptyData.visibility = View.GONE

                //si ya se cargo el mapa, setear los lugares
                it.forEach { l ->
                    map.addMarker(MarkerOptions().position(LatLng(l.latitud!!,l.longitud!!)).title(l.fecha!!.toDate().toString()))
                }
                map.animateCamera(
                        CameraUpdateFactory.newLatLngZoom(LatLng(it.first().latitud!!,it.first().longitud!!),16f),
                3000,
                null
                )
            } else {
                binding.rvLocations.visibility = View.GONE
                binding.tvEmptyData.visibility = View.VISIBLE
            }
        }


        locationsViewModel.progress.observe(requireActivity()){
            binding.pbLoading.isVisible = it
        }
    }

    private fun initComponents() {
        mAdapter = LocationsAdapter(this)
        binding.rvLocations.setHasFixedSize(true)
        binding.rvLocations.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvLocations.adapter = mAdapter
    }

    override fun onClickLocation(locationModel: LocationModel) {
        val center = CameraUpdateFactory.newLatLng(LatLng(locationModel.latitud!!, locationModel.longitud!!))
        val zoom = CameraUpdateFactory.zoomTo(18f)

        map.moveCamera(center)
        map.animateCamera(zoom)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun createMap() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.fMap) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(p0: GoogleMap) {
        map = p0
    }
}