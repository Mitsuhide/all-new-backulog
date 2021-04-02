package com.ttop.backulog.ui.films

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ttop.backulog.databinding.FilmsFragmentBinding
import com.ttop.backulog.domain.Media
import com.ttop.backulog.ui.MediaAdapter

class FilmsFragment : Fragment() {

    private lateinit var viewModel: FilmsViewModel
    private lateinit var binding: FilmsFragmentBinding
    private lateinit var adapter: MediaAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = FilmsModule().getViewModel(this)
        binding = FilmsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getMediaFilmList()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.medias.observe(requireActivity(), { medias ->
            displayList(medias)
        })
    }

    private fun displayList(medias: List<Media>) {
        adapter = MediaAdapter(medias)
        binding.filmsRecyclerView.adapter = adapter
    }

}