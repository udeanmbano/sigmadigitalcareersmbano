package com.mbano.eventsapp.ui.schedule

import android.os.Bundle
import android.os.Parcelable
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
    private var loadedFirstTime:Boolean = false
    // Save state
    var recyclerViewState: Parcelable? = null
    // This property is only valid between onCreateView and
    // onDestroyView.

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showLoading()
        binding.scheduleRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                recyclerViewState =
                    binding.scheduleRecycler.layoutManager?.onSaveInstanceState() // save recycleView state
            }
        })
        recyclerViewState = binding.scheduleRecycler.layoutManager?.onSaveInstanceState()
        binding.scheduleRecycler.adapter?.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        binding.scheduleRecycler.layoutManager?.onRestoreInstanceState(recyclerViewState)
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

            if (!loadedFirstTime) {
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false).apply {
                    scheduleRecycler.layoutManager = this

                }
                ScheduleAdapter().apply {
                    items = schedules
                    scheduleRecycler.adapter = this

                }
                scheduleRecycler.adapter?.notifyDataSetChanged()
                if( scheduleRecycler.adapter?.itemCount!!>1) {
                    loadedFirstTime = true
                } else {

                }
            } else {
               ScheduleAdapter().apply {
                    items = schedules

                }
                scheduleRecycler.adapter?.notifyDataSetChanged()
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