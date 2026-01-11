package com.emman.android.loadapp.ui

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.emman.android.loadapp.ButtonState
import com.emman.android.loadapp.MainViewModel
import com.emman.android.loadapp.R
import com.emman.android.loadapp.Urls
import com.emman.android.loadapp.databinding.FragmentMenuBinding

class MenuFragment : Fragment() {

    private lateinit var binding: FragmentMenuBinding
    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var selectedUrl: String
    private lateinit var selectedFileName: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.glide_radio -> {
                    selectedUrl = GLIDE.url
                    selectedFileName = GLIDE.name
                }

                R.id.loadapp_radio -> {
                    selectedUrl = URL.url
                    selectedFileName = URL.name
                }

                R.id.retrofit_radio -> {
                    selectedUrl = RETROFIT.url
                    selectedFileName = RETROFIT.name
                }
            }
        }

        binding.customButton.setOnClickListener {
            if (binding.radioGroup.checkedRadioButtonId == -1) {
                Toast.makeText(context, "Please select an option", Toast.LENGTH_SHORT).show()
            } else {
                buttonState()
            }
        }

        viewModel.downloadStatus.observe(viewLifecycleOwner) { status ->
            status?.let {
                binding.customButton.buttonState = ButtonState.Completed
                findNavController().navigate(
                    MenuFragmentDirections.menuFragmentToDetailFragment(
                        viewModel.fileName.value ?: "",
                        it
                    )
                )
                viewModel.setDownloadStatus(null)
            }
        }
    }


    private fun buttonState() {
        binding.customButton.buttonState = ButtonState.Loading
        download()
    }


    private fun download() {
        val request = DownloadManager.Request(selectedUrl.toUri())
            .setTitle(selectedFileName)
            .setDescription(getString(R.string.app_description))
            .setRequiresCharging(false)
            .setAllowedOverMetered(true)
            .setAllowedOverRoaming(true)

        val downloadManager = requireContext().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

        val downloadID = downloadManager.enqueue(request) // enqueue puts the download request in the queue.

        viewModel.setDownloadID(downloadID)
        viewModel.setFileName(selectedFileName)
    }

    companion object {
        private val GLIDE = Urls(
            "Glide - Image Loading Library by BumpTech",
            "https://github.com/bumptech/glide"
        )
        private val URL = Urls(
            "LoadApp - Current repository by Udacity",
            "https://github.com/udacity/nd940-c3-advanced-android-programming-project-starter/archive/master.zip"
        )
        private val RETROFIT = Urls(
            "Retrofit - Type-safe HTTP client for Android and Java by Square, Inc",
            "https://github.com/square/retrofit"
        )
    }

}

