package com.spacex.rockets.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.spacex.core.base.lifecycle.BaseDaggerFragment
import com.spacex.core.extensions.observe
import com.spacex.rockets.R
import com.spacex.rockets.databinding.ListBinding
import com.spacex.rockets.ui.adapters.RocketsAdapter
import com.spacex.rockets.ui.vm.RocketsViewModel
import com.spacex.repository.entities.rockets.RocketEntity
import com.spacex.repository.interactors.base.PersistenceEmpty
import com.spacex.repository.interactors.base.State

class RocketsFragment : BaseDaggerFragment<ListBinding>(), SwipeRefreshLayout.OnRefreshListener {

    //region Properties

    private val viewModel by viewModels<RocketsViewModel> { viewModelFactory }

    private lateinit var rocketsAdapter: RocketsAdapter
    //endregion

    //region Lifecyle

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //setHasOptionsMenu(true)

        rocketsAdapter = RocketsAdapter(context!!, ::onItemSelected)
        binding.rocketList.adapter = rocketsAdapter
        binding.swipeRefreshLayout.setOnRefreshListener(this)

        observeDataChanges()
    }

    override fun onResume() {
        super.onResume()

        binding.swipeRefreshLayout.isEnabled = true
    }

    override fun onPause() {
        super.onPause()

        binding.swipeRefreshLayout.isEnabled = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rocketList.adapter = null
    }
    //endregion

    //region Functions

    private fun observeDataChanges() {

        // Observing state to show loading
        observe(viewModel.stateData) {
            binding.swipeRefreshLayout.isRefreshing = it is State.Loading
        }

        // Observing error to show toast with retry action
        observe(viewModel.errorData) {
            if (it !is PersistenceEmpty) {
                showSnackbarWithAction(it) {
                    viewModel.retry()
                }
            }
        }

        observe(viewModel.successData) {
            rocketsAdapter.updateData(it, viewModel.isActiveOnly)
        }


        binding.isActiveOnly.setOnCheckedChangeListener { _, isChecked ->

            viewModel.isActiveOnly = isChecked
            if (!viewModel.successData.value.isNullOrEmpty()) {
                rocketsAdapter.updateData(
                    viewModel.successData.value!!,
                    isChecked
                )
            }
        }

    }

    private fun onItemSelected(item: RocketEntity) {
        Toast.makeText(context, "${item.rocket_name} has been clicked!", Toast.LENGTH_SHORT).show()
    }

    override fun onRefresh() {
        viewModel.refresh()
    }

    override fun getLayoutId(): Int = R.layout.fragment_rockets
    //endregion
}
