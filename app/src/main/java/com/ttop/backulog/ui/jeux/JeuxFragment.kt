package com.ttop.backulog.ui.jeux

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ttop.backulog.databinding.JeuxFragmentBinding
import com.ttop.backulog.domain.Media
import com.ttop.backulog.ui.MediaAdapter
import com.ttop.backulog.ui.jeux.JeuxViewModel.State.Failure
import com.ttop.backulog.ui.jeux.JeuxViewModel.State.Loading
import com.ttop.backulog.ui.jeux.JeuxViewModel.State.Success

class JeuxFragment : Fragment() {

    private lateinit var viewModel: JeuxViewModel
    private lateinit var binding: JeuxFragmentBinding
    private lateinit var adapter: MediaAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = JeuxModule().getViewModel(this)
        viewModel.getMediaJeuList()


        binding = JeuxFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()

    }

    private fun observeViewModel() {
        viewModel.state.observe(requireActivity(), {
            when (it) {
                Failure -> TODO()
                Loading -> {
                    binding.jeuxViewFlipper.displayedChild = VF_LOADING_CHILD
                }
                is Success -> {
                    displayList(it.medias)
                    binding.jeuxViewFlipper.displayedChild = VF_CONTENT_CHILD
                }
            }
        })
    }

    private fun displayList(medias: List<Media>) {
        adapter = MediaAdapter(medias)
        binding.jeuxRecyclerView.adapter = adapter
    }

    companion object {
        const val VF_CONTENT_CHILD = 0
        const val VF_LOADING_CHILD = 1
    }
}

