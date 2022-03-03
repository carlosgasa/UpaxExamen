package com.examen.carlosgs.ui.upload

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.examen.carlosgs.R
import com.examen.carlosgs.adapter.FileAdapter
import com.examen.carlosgs.adapter.PopularMovieAdapter
import com.examen.carlosgs.databinding.FragmentLocationsBinding
import com.examen.carlosgs.databinding.FragmentUploadFilesBinding
import com.examen.carlosgs.ui.locations.LocationsViewModel


class UploadFilesFragment : Fragment() {

    private lateinit var uploadFilesViewModel: UploadFilesViewModel
    private lateinit var mAdapter: FileAdapter


    private var _binding: FragmentUploadFilesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        uploadFilesViewModel = ViewModelProvider(this)[UploadFilesViewModel::class.java]

        _binding = FragmentUploadFilesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initComponents()
        initObservers()

        return root
    }

    private fun initObservers() {
        uploadFilesViewModel.filesListData.observe(requireActivity()){
            mAdapter.setList(it)
        }

        uploadFilesViewModel.loading.observe(requireActivity()){
            binding.pbLoading.visibility = if(it) View.VISIBLE else View.INVISIBLE
        }
    }

    private fun initComponents() {
        binding.fabSelect.setOnClickListener{
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            resultLauncherGallery.launch(intent)
        }

        //Configurar recycler
        mAdapter = FileAdapter()
        binding.rvFiles.setHasFixedSize(true)
        binding.rvFiles.layoutManager = GridLayoutManager(requireActivity(), 3)
        binding.rvFiles.adapter = mAdapter
    }


    private val resultLauncherGallery = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                it.data?.data?.let {
                    uploadFilesViewModel.uploadFile(it)
                }
            }
        }
}