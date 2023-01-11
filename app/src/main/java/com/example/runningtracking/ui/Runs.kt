package com.example.runningtracking.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.runningtracking.R
import com.example.runningtracking.adapters.RunAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint


/**
 * A simple [Fragment] subclass.
 * Use the [Runs.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class Runs : Fragment() {

    private val viewModel : MainViewModel by viewModels()
    lateinit var navController : NavController
    lateinit var bottomMenu : BottomNavigationView
    private lateinit var runAdapter: RunAdapter
    private lateinit var rvRuns : RecyclerView



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_runs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvRuns = view.findViewById(R.id.rvRuns)
        navController = findNavController()
        bottomMenu = view.findViewById(R.id.bottomNavView2)
        bottomMenu.setupWithNavController(navController)
        bottomMenu.menu.getItem(0).isCheckable = false
        setupRecyclerView()
        viewModel.runsSortedByDate.observe(viewLifecycleOwner, Observer {
            runAdapter.sumbitList(it)
        })

    }

    private fun setupRecyclerView() = rvRuns.apply {
        runAdapter = RunAdapter()
        adapter = runAdapter
        layoutManager = LinearLayoutManager(requireContext())
    }

}