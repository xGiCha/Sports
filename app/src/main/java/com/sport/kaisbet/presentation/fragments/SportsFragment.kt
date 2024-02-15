package com.sport.kaisbet.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sport.kaisbet.databinding.FragmentSportsLayoutBinding
import com.sport.kaisbet.presentation.adapters.SportsAdapter
import com.sport.kaisbet.presentation.viewModels.SportsViewModel

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
        sportsViewModel.fetchSportsList()

        // setup adapter
        adapter = SportsAdapter(requireContext(), { isCollapsed, position ->
            sportsViewModel.checkCollapsedProperty(isCollapsed, position)
        }, { hasFavorite, event ->
            sportsViewModel.checkFavoriteProperty(hasFavorite, event)
        })
        binding.sportsRV.layoutManager = LinearLayoutManager(requireContext())
        binding.sportsRV.adapter = adapter
    }

    private fun setUpObservers() {
        sportsViewModel.sportsList.observe(viewLifecycleOwner) { sportsList ->
            adapter.submitList(sportsList.map { it.copy() })
        }
    }

}