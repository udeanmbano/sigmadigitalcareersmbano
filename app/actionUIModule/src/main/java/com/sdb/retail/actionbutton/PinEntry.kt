package com.sdb.retail.actionbutton

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.get
import androidx.core.widget.addTextChangedListener
import com.furkanakdemir.surroundcardview.SurroundCardView
import com.sdb.retail.actionbutton.databinding.PinEntryBinding
import java.lang.StringBuilder

typealias PinValidator = (digit: String) -> Unit

class PinEntry @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {
    private var binding: PinEntryBinding

    val firstDigit: String
        get() = binding.etxtFirstDigit.text.toString()
    val secondDigit: String
        get() = binding.etxtSecondDigit.text.toString()
    val thirdDigit: String
        get() = binding.etxtThirdDigit.text.toString()
    val fourthDigit: String
        get() = binding.etxtLastDigit.text.toString()

    var showMask = false

    init {
        val view = View.inflate(context, R.layout.pin_entry, this)
        binding = PinEntryBinding.bind(view)

        with(binding) {
            otpFirstDigit.setSurrounded(false)
            otpSecondDigit.setSurrounded(false)
            otpThirdDigit.setSurrounded(false)
            otpLastDigit.setSurrounded(false)
        }
        setUpInputHandlers()
    }

    private fun setUpInputHandlers() {
        with(binding) {
            etxtFirstDigit.setOnKeyListener { _, keyCode, keyEvent ->
                if (keyCode == KeyEvent.KEYCODE_DEL && keyEvent.action == KeyEvent.ACTION_DOWN) {
                    clearDigit(otpFirstDigit)
                }

                false
            }

            etxtSecondDigit.setOnKeyListener { view, keyCode, keyEvent ->
                val etxt = view as EditText
                if (keyCode == KeyEvent.KEYCODE_DEL && keyEvent.action == KeyEvent.ACTION_DOWN) {
                    clearDigit(otpSecondDigit)
                    if (etxt.text.isNullOrEmpty())
                        clearDigit(otpFirstDigit)
                }

                false
            }

            etxtThirdDigit.setOnKeyListener { view, keyCode, keyEvent ->
                val etxt = view as EditText
                if (keyCode == KeyEvent.KEYCODE_DEL && keyEvent.action == KeyEvent.ACTION_DOWN) {
                    clearDigit(otpThirdDigit)
                    if (etxt.text.isNullOrEmpty())
                        clearDigit(otpSecondDigit)
                }

                false
            }

            etxtLastDigit.setOnKeyListener { view, keyCode, keyEvent ->
                val etxt = view as EditText
                if (keyCode == KeyEvent.KEYCODE_DEL && keyEvent.action == KeyEvent.ACTION_DOWN) {
                    clearDigit(otpLastDigit)
                    if (etxt.text.isNullOrEmpty())
                        clearDigit(otpThirdDigit)
                }

                false
            }
        }
    }

    private fun clearDigit(surroundCardView: SurroundCardView) {
        post {
            val input = surroundCardView[0]
            val mask = surroundCardView[1]

            mask.visibility = INVISIBLE
            (input as EditText).apply {
                visibility = VISIBLE
                setText("")
                requestFocus()
            }
        }
    }

    private fun showMask(surroundCardView: SurroundCardView) {
        postDelayed({
            if (showMask) {
                val mask = surroundCardView[1]
                mask.visibility = VISIBLE
            }
        }, 150)
    }

    fun clearOtpFields() {
        with(binding) {
            etxtFirstDigit.text = null
            etxtSecondDigit.text = null
            etxtThirdDigit.text = null
            etxtLastDigit.text = null
            etxtFirstDigit.requestFocus()
        }
    }

    fun setUpValidationHandlers(
        firstDigitValidation: PinValidator,
        secondDigitValidation: PinValidator,
        thirdDigitValidation: PinValidator,
        fourthDigitValidation: PinValidator
    ) {
        with(binding) {
            etxtFirstDigit.addTextChangedListener {
                firstDigitValidation(it.toString())
            }

            etxtSecondDigit.addTextChangedListener {
                secondDigitValidation(it.toString())
            }

            etxtThirdDigit.addTextChangedListener {
                thirdDigitValidation(it.toString())
            }

            etxtLastDigit.addTextChangedListener {
                fourthDigitValidation(it.toString())
            }
        }
    }

    fun acceptFirstDigit() {
        with(binding) {
            otpFirstDigit.surround()
            showMask(otpFirstDigit)
            etxtSecondDigit.requestFocus()
        }
    }

    fun releaseFirstDigit() {
        binding.otpFirstDigit.release()
    }

    fun acceptSecondDigit() {
        with(binding) {
            otpSecondDigit.surround()
            showMask(otpSecondDigit)
            etxtThirdDigit.requestFocus()
        }
    }

    fun releaseSecondDigit() {
        binding.otpSecondDigit.release()
    }

    fun acceptThirdDigit() {
        with(binding) {
            otpThirdDigit.surround()
            showMask(otpThirdDigit)
            etxtLastDigit.requestFocus()
        }
    }

    fun releaseThirdDigit() {
        binding.otpThirdDigit.release()
    }

    fun acceptLastDigit() {
        with(binding) {
            otpLastDigit.surround()
            showMask(otpLastDigit)
        }
    }

    fun releaseLastDigit() {
        binding.otpLastDigit.release()
    }

    fun getPin(): String {
        val otpStringBuilder = StringBuilder()
        otpStringBuilder
            .append(firstDigit)
            .append(secondDigit)
            .append(thirdDigit)
            .append(fourthDigit)
        return otpStringBuilder.toString()
    }

    fun requestFirstFocus() {
        binding.etxtFirstDigit.requestFocus()
    }
}