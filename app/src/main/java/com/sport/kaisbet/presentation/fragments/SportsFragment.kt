package com.sport.kaisbet.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.sport.kaisbet.databinding.FragmentSportsLayoutBinding
import com.sport.kaisbet.presentation.adapters.SportsAdapter
import com.sport.kaisbet.presentation.viewModels.SportsViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SportsFragment : Fragment() {

    private lateinit var binding: FragmentSportsLayoutBinding
    private lateinit var sportsViewModel: SportsViewModel
    private lateinit var adapter: SportsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sportsViewModel = ViewModelProvider(requireActivity()).get(SportsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSportsLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        setUpObservers()

    }

    private fun initViews() {
        adapter = SportsAdapter({ isCollapsed, position ->
            sportsViewModel.checkCollapsedProperty(isCollapsed, position)
        }, { hasFavorite, event ->
            sportsViewModel.handleFavoriteSingleStar(hasFavorite, event)
        },{ switchState, sportUi ->
            sportsViewModel.handleFavoriteAllStars(switchState, sportUi)
        })
        binding.sportsRV.layoutManager = LinearLayoutManager(requireContext())
        binding.sportsRV.adapter = adapter
    }

    private fun setUpObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                sportsViewModel.sportsList.collectLatest { sportsList ->
                    adapter.submitList(sportsList)
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                sportsViewModel.loader.collectLatest { state ->
                    binding.progressView.visibility = View.VISIBLE.takeIf { state } ?: View.GONE
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                sportsViewModel.showError.collectLatest { state ->
                    binding.errorTxtV.isVisible = state
                    binding.sportsRV.isVisible = !state
                }
            }
        }
    }

}