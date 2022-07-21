package com.sdb.retail.actionbutton.survey

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RadioButton
import android.widget.RadioGroup
import com.sdb.retail.actionbutton.R
import com.sdb.retail.commons.survey.Option

class RadioResponseControl @JvmOverloads constructor(
    context: Context,
    options: List<Option> =
        listOf(
            Option("Yes", "Yes"),
            Option("No", "No")
        ),
    attrs: AttributeSet? = null
) : RadioGroup(context, attrs) {

    var selectedOption: Option? = null

    init {
        options.forEach { option ->
            inflateRadioButton(option)
        }
    }

    fun setSate(option: Option) {
        val radioButton = findViewWithTag<RadioButton>(option)
        radioButton.isChecked = true
    }

    private fun inflateRadioButton(option: Option) {
        val radioButton = LayoutInflater.from(context)
            .inflate(R.layout.question_radio_button, this, false) as RadioButton

        radioButton.text = option.value
        radioButton.tag = option
        radioButton.setOnCheckedChangeListener { _, checked ->
            if (checked)
                selectedOption = option
        }

        addView(radioButton)
    }
}