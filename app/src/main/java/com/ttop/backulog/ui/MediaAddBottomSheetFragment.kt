package com.ttop.backulog.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ttop.backulog.databinding.MediaAddBottomsheetFragmentBinding

class MediaAddBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: MediaAddBottomsheetFragmentBinding
    lateinit var viewModel: MediaAddViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = MediaAddModule().getViewModel(this)
        binding = MediaAddBottomsheetFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.closeButton.setOnClickListener { dismiss() }
        binding.addJeuButton.setOnClickListener {
            viewModel.storeMedia(getMediaName())
            dismiss()
        }
    }

    private fun getMediaName() = binding.jeuNameEditText.text?.toString() ?: "Le jeu"

}