package com.mbano.eventsapp.ui.events

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.mbano.eventsapp.R
import com.mbano.eventsapp.databinding.FragmentEventsBinding
import com.mbano.eventsapp.ui.events.uI.EventUI
import com.mbanoUI.InfoDialog
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class EventsFragment : Fragment(R.layout.fragment_events) {

    private val binding by viewBinding(FragmentEventsBinding::bind)
    private val eventsViewModel: EventsViewModel by sharedViewModel()
    private val loadingDialog = InfoDialog()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpObservers()

        showLoading()
        eventsViewModel.getEventsData()
    }

    private fun setUpObservers() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                observeEventDataState()
            }
        }
    }

    private suspend fun observeEventDataState() {
        eventsViewModel.eventDataResult.collectLatest {
            hideLoading()
            showEvents(it)
        }
    }
    private fun showEvents(events
                           : List<EventUI>) {
        with(binding) {
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false).apply {
                eventsRecycler.layoutManager = this
            }
           EventsAdapter().apply {
                items = events
               eventsRecycler.adapter = this
            }
        }
    }
    private fun showLoading() {
        val fragmentManager = requireActivity().supportFragmentManager
        loadingDialog.apply {
            infoMessageText = R.string.txtvPleaseBePatient
            loadingMessageText = R.string.txtvLoading
            showLoading = true
            isCancelable = false
            show(fragmentManager, "loading")
        }
    }

    private fun hideLoading() {
        if (loadingDialog.isVisible) {
            loadingDialog.dismiss()
        }
    }


}