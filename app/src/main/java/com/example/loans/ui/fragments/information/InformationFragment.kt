package com.example.loans.ui.fragments.information

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.example.loans.R
import com.example.loans.databinding.FirstStartInstructionLayoutBinding
import com.example.loans.presentation.fragments.information.InformationViewModel
import com.example.loans.ui.activity.MainActivity
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class InformationFragment: Fragment(R.layout.first_start_instruction_layout) {

    private var _firstStartBinding: FirstStartInstructionLayoutBinding? = null
    private val firstStartBinding  get() = _firstStartBinding!!

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private val viewModel: InformationViewModel by viewModels { factory }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        _firstStartBinding = FirstStartInstructionLayoutBinding.bind(view)
        initViews()
        observeViewModel()
    }

    private fun initViews() {
        firstStartBinding.buttonStart.setOnClickListener {
            viewModel.navigateToHome()
        }
    }

    private fun observeViewModel() {
        viewModel.navigateToHomeEvent.observe(viewLifecycleOwner) { showHomeFragment() }
    }

    private fun showHomeFragment() {
        findNavController().navigate(R.id.action_firstStartFragment_to_home_graph, null, navOptions {
            popUpTo(R.id.firstrun_graph) {
                inclusive
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _firstStartBinding = null
    }

}