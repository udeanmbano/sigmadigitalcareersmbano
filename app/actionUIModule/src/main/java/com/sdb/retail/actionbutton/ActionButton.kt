package com.sdb.retail.actionbutton

import android.content.Context
import android.util.AttributeSet
import android.view.View

import androidx.annotation.ColorRes
import com.sdb.retail.actionbutton.databinding.ActionButtonBinding



class ActionButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ToggleButtonBase(context, attrs) {

    private var binding: ActionButtonBinding

    init {
        val view = View.inflate(context, R.layout.action_button, this)
        binding = ActionButtonBinding.bind(view)

        btnActive = binding.btnActive
        btnInActive = binding.btnInActive

        initialize(attrs)

        val attrsTypedArray = context.obtainStyledAttributes(attrs, R.styleable.ActionButton)

        @ColorRes
        val loadingIndicatorTint =
            attrsTypedArray.getResourceId(R.styleable.ActionButton_loadingIdicatorTint, 0)
        if (loadingIndicatorTint != 0) {
            binding.progressIndicator.indeterminateTintList =
                context.getColorStateList(loadingIndicatorTint)
        }

        attrsTypedArray.recycle()
    }

    fun showLoading() {
        btnActive.apply {
            setOnClickListener { }
            text = ""
        }

        binding.progressIndicator.visibility = VISIBLE
    }

    fun hideLoading() {
        btnActive.apply {
            setOnClickListener(activeButtonListener)
            text = activeButtonText
        }
        binding.progressIndicator.visibility = INVISIBLE
    }
}
