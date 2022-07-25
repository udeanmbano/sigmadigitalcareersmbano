package com.mbanoUI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.DialogFragment
import com.mbano.eventsapp.uiActionModule.databinding.InfoDialogBinding

class InfoDialog : DialogFragment() {
    private lateinit var binding: InfoDialogBinding

    @StringRes
    var infoMessageText: Int? = null
    @StringRes
    var loadingMessageText: Int? = null

    var showLoading = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = InfoDialogBinding.inflate(inflater)
        infoMessageText?.let {
            binding.txtvInfoMessage.setText(it)
        }
        loadingMessageText?.let {
            binding.txtvLoadingMessage.setText(it)
        }
        if (showLoading) {
            binding.imgSuccess.visibility = GONE
            binding.loadingContainer.visibility = VISIBLE
        } else {
            binding.imgSuccess.visibility = VISIBLE
            binding.loadingContainer.visibility = GONE
        }
        return binding.root
    }
}