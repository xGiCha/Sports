package com.sport.kaisbet.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sport.kaisbet.databinding.FragmentSportsLayoutBinding
import com.sport.kaisbet.ui.adapters.SportsAdapter
import com.sport.kaisbet.ui.viewModels.SportsViewModel
import okhttp3.internal.toImmutableList

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

    @SuppressLint("NotifyDataSetChanged")
    private fun setUpObservers() {
        sportsViewModel.sportsList.observe(viewLifecycleOwner) { sportsList ->
            adapter.submitList(sportsList)
        }

        sportsViewModel.sportsListExtra.observe(viewLifecycleOwner) { sportsList ->
//            adapter.submitList(sportsList.map { it.copy() })
            adapter.submitList(sportsList)
            adapter.notifyDataSetChanged()
        }
    }

}