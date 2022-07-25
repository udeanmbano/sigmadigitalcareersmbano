package com.mbano.eventsapp.ui.schedule

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
import com.mbano.eventsapp.databinding.FragmentScheduleBinding
import com.mbano.eventsapp.ui.schedule.ui.ScheduleUI
import com.mbanoUI.InfoDialog
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ScheduleFragment : Fragment(R.layout.fragment_schedule) {

    private val binding by viewBinding(FragmentScheduleBinding::bind)
    private val scheduleViewModel: ScheduleViewModel by sharedViewModel()
    private val loadingDialog = InfoDialog()

    // This property is only valid between onCreateView and
    // onDestroyView.

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showLoading()
        setUpObservers()

        scheduleViewModel.getScheduleData()
    }

    private fun setUpObservers() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                observeScheduleDataState()
            }
        }
    }

    private suspend fun observeScheduleDataState() {
        scheduleViewModel.scheduleDataResult.collectLatest {
            hideLoading()
            showSchedules(it)
        }
    }

    private fun showSchedules(schedules: List<ScheduleUI>) {
        with(binding) {
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false).apply {
                 scheduleRecycler.layoutManager = this
            }
             ScheduleAdapter().apply {
                  items = schedules
                 scheduleRecycler.adapter = this
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