package com.sdb.retail.actionbutton

import android.content.Context
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.ViewAnimationUtils
import android.widget.Button
import android.widget.FrameLayout
import androidx.core.animation.doOnEnd
import androidx.core.content.res.getResourceIdOrThrow
import com.airbnb.paris.extensions.style
import com.sdb.retail.commons.extensions.getString
import kotlin.math.max

sealed class Toggle(val animate: Boolean) {
    class Activate(animate: Boolean) : Toggle(animate)
    class Deactivate(animate: Boolean) : Toggle(animate)
}

abstract class ToggleButtonBase  : FrameLayout {

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null) : super(context, attrs)
    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    companion object {
        const val ANIM_DURATION_IN_MILLIS = 300L
    }

    protected lateinit var btnActive: Button
    protected lateinit var btnInActive: Button
    protected lateinit var activeButtonText: String
    protected lateinit var activeButtonListener: OnClickListener

    protected fun initialize(attributeSet: AttributeSet?) {
        val attrsTypedArray = context.obtainStyledAttributes(attributeSet, R.styleable.ToggleButtonBase)
        btnActive.style(attrsTypedArray.getResourceIdOrThrow(R.styleable.ToggleButtonBase_activeStyle))
        activeButtonText = attrsTypedArray.getString(
            R.styleable.ToggleButtonBase_text,
            context.getString(R.string.defaultText)
        )
        btnActive.text = activeButtonText

        btnInActive.style(attrsTypedArray.getResourceIdOrThrow(R.styleable.ToggleButtonBase_inactiveStyle))
        btnInActive.text = attrsTypedArray.getString(
            R.styleable.ToggleButtonBase_text,
            context.getString(R.string.defaultText)
        )

        btnInActive.isEnabled =
            attrsTypedArray.getBoolean(R.styleable.ToggleButtonBase_isInactiveClickable, false)

        if (attrsTypedArray.getBoolean(R.styleable.ToggleButtonBase_isActive, false))
            toggle(Toggle.Activate(false))

        val underlineText =
            attrsTypedArray.getBoolean(R.styleable.ToggleButtonBase_textUnderline, false)
        if (underlineText) {
            btnActive.paintFlags = btnActive.paintFlags or Paint.UNDERLINE_TEXT_FLAG
            btnInActive.paintFlags = btnInActive.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        }

        attrsTypedArray.recycle()
    }

    protected fun makeInactiveButtonClickable() {
        btnInActive.isEnabled = true
    }

    fun toggle(toggleType: Toggle) {

        when (toggleType) {
            is Toggle.Activate -> {

                if (btnActive.visibility == VISIBLE)
                    return

                btnActive.visibility = VISIBLE

                if (!toggleType.animate) {
                    btnInActive.visibility = View.INVISIBLE
                    return
                }
            }

            is Toggle.Deactivate -> {

                if (btnActive.visibility == INVISIBLE)
                    return

                btnInActive.visibility = VISIBLE

                if (!toggleType.animate) {
                    btnActive.visibility = INVISIBLE
                    return
                }
            }
        }

        animate(toggleType)
    }

    fun setActiveClickListener(onClickListener: OnClickListener) {
        activeButtonListener = onClickListener
        btnActive.setOnClickListener(onClickListener)
    }

    fun setInActiveClickListener(onClickListener: OnClickListener) {
        btnInActive.setOnClickListener(onClickListener)
    }

    private fun animate(toggleType: Toggle) {

        if (!btnActive.isAttachedToWindow)
            return

        val centerWidth = findCenter(btnActive.width)
        val centerHeight = findCenter(btnActive.height)
        val maxRadius = max(btnActive.width, btnActive.height).toFloat()

        when (toggleType) {
            is Toggle.Activate -> {
                val anim = ViewAnimationUtils.createCircularReveal(
                    btnActive,
                    centerWidth,
                    centerHeight,
                    0f,
                    maxRadius
                )
                anim.duration = ANIM_DURATION_IN_MILLIS
                anim.doOnEnd {
                    btnInActive.visibility = View.INVISIBLE
                }
                anim.start()
            }

            is Toggle.Deactivate -> {
                val anim = ViewAnimationUtils.createCircularReveal(
                    btnActive,
                    centerWidth,
                    centerHeight,
                    maxRadius,
                    0f
                )
                anim.duration = ANIM_DURATION_IN_MILLIS
                anim.doOnEnd {
                    btnActive.visibility = View.INVISIBLE
                }
                anim.start()
            }
        }
    }

    private fun findCenter(measurement: Int): Int {
        return measurement.div(2)
    }
}