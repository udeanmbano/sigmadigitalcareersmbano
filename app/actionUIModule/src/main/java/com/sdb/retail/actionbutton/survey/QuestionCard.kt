package com.sdb.retail.actionbutton.survey

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RadioGroup
import androidx.core.widget.addTextChangedListener
import com.furkanakdemir.surroundcardview.ReleaseListener
import com.furkanakdemir.surroundcardview.SurroundCardView
import com.furkanakdemir.surroundcardview.SurroundListener
import com.google.android.material.textfield.TextInputLayout
import com.sdb.retail.actionbutton.R
import com.sdb.retail.actionbutton.databinding.QuestionCardBinding
import com.sdb.retail.commons.survey.*
import java.lang.IllegalStateException


class QuestionCard @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    FrameLayout(context, attrs) {

    private var binding: QuestionCardBinding
    val questionCard: SurroundCardView
    val responseControlContainer: LinearLayout
    private var responseControls: MutableList<View> = mutableListOf()

    private var responseListener: QuestionResponseListener? = null
    private var manualCompleteAvailable = false


    init {
        val view = inflate(context, R.layout.question_card, this)
        binding = QuestionCardBinding.bind(view)

        questionCard = binding.questionCard
        responseControlContainer = binding.responseControlContainer

        questionCard.setSurrounded(false)

        questionCard.setOnClickListener {
            if (questionCard.isCardSurrounded)
                editQuestion()
            else
                if (manualCompleteAvailable)
                    completeQuestion()
        }

        questionCard.releaseListener = object : ReleaseListener {
            override fun onRelease() {
                responseControlContainer.visibility = View.VISIBLE
                questionCard.setSurrounded(false)
            }
        }
    }

    fun setQuestion(
        questionNumber: Int,
        question: Question,
        wasUserTriggered: Boolean = true
    ) {
        binding.questionNumberValue.text = questionNumber.toString()
        binding.questionTitle.text =
            context.getString(SurveyEngine.getStringResId(question.questionId))

        if (question.response == null && questionCard.isCardSurrounded)
            editQuestion()

        removeResponseControls()

        //Generate initial question response controls
        val responseControl = generateResponseControl(question, false)
        injectResponseControl(responseControl)
    }

    private fun removeResponseControls() {
        responseControlContainer.removeAllViews()
    }

    private fun removeSubQuestionResponseControls() {
        responseControlContainer.removeViews(1, responseControlContainer.childCount - 1)
    }

    fun setSubQuestions(subQuestions: List<Question>?) {
        if (subQuestions == null)
            return

        removeSubQuestionResponseControls()

        subQuestions.forEach { subQuestion ->
            val subQuestionResponseControl = generateResponseControl(subQuestion, true)
            injectResponseControl(subQuestionResponseControl)
        }
    }

    fun setResponseListener(responseListener: QuestionResponseListener) {
        this.responseListener = responseListener
    }

    fun completeQuestion() {
        questionCard.surround()
        questionCard.surroundListener = object : SurroundListener {
            override fun onSurround() {
                hideResponseControls()
            }

        }
        manualCompleteAvailable = true
    }

    private fun editQuestion() {
        questionCard.release()
    }

    private fun injectResponseControl(control: View) {
        responseControls.add(control)
        responseControlContainer.addView(control)
    }

    private fun hideResponseControls() {
        responseControlContainer.visibility = View.GONE
    }

    private fun generateResponse(
        isSubQuestion: Boolean,
        questionId: String,
        responseValue: Any
    ): SurveyViewModel.Response {
        return if (isSubQuestion)
            SurveyViewModel.Response.SubQuestionResponse(
                questionId,
                responseValue
            )
        else
            SurveyViewModel.Response.QuestionResponse(
                questionId,
                responseValue
            )
    }

    private fun generateResponseControl(question: Question, isSubQuestion: Boolean): View {
        return when (question) {
            is RadioMultipleChoice -> {
                val radioResponseControl = inflateRadioResponseControl(question.options)

                //Check if any state needs to be reinstated
                val questionResponse = question.response
                if (questionResponse != null)
                    reInstateControl(questionResponse, radioResponseControl)

                setInputListener(isSubQuestion, question, radioResponseControl)

                radioResponseControl
            }

            is SingleAutofillText -> {
                View(context)
            }

            is MultipleAutofillText -> {
                val textInputLayout =
                    inflateTextInputResponseControl(question.hintTextId, question.inputType)

                //Restore state
                val questionResponse = question.response
                if (questionResponse != null)
                    reInstateControl(questionResponse, textInputLayout)

                setInputListener(isSubQuestion, question, textInputLayout)

                textInputLayout
            }

            is SingleLineText -> {
                val textInputLayout =
                    inflateTextInputResponseControl(question.hintTextId, question.inputType)

                //Restore state
                val questionResponse = question.response
                if (questionResponse != null)
                    reInstateControl(questionResponse, textInputLayout)

                setInputListener(isSubQuestion, question, textInputLayout)

                textInputLayout
            }

            is MultipleSingleLineText -> {
                val textInputLayout =
                    inflateTextInputResponseControl(question.hintTextId, question.inputType)

                //Restore state
                val questionResponse = question.response
                if (questionResponse != null)
                    reInstateControl(questionResponse, textInputLayout)

                setInputListener(isSubQuestion, question, textInputLayout)

                textInputLayout
            }

            is MultilineText -> {
                val textInputLayout =
                    inflateTextInputResponseControl(question.hintTextId, question.inputType)

                //Restore state
                val questionResponse = question.response
                if (questionResponse != null)
                    reInstateControl(questionResponse, textInputLayout)

                setInputListener(isSubQuestion, question, textInputLayout)

                textInputLayout
            }

            is AddressText -> {
                //TODO: Update to retrieve hints from strings res
                val inputCountry = inflateTextInputResponseControl("Country", question.inputType)
                val inputCity = inflateTextInputResponseControl("City", question.inputType)
                val inputStreet = inflateTextInputResponseControl("Street", question.inputType)

                val questionResponse = question.response

                if (questionResponse != null) {
                    //Restore state
                }

                setChangeFocusListener(inputCountry, inputCity)
                setChangeFocusListener(inputCity, inputStreet)

                //TODO: Concat all responses
                setInputListener(isSubQuestion, question, inputStreet)

                val addressContainer = createLinearLayout()
                addressContainer.addView(inputCountry)
                addressContainer.addView(inputCity)
                addressContainer.addView(inputStreet)

                addressContainer
            }

            is CountryAndTin -> {
                View(context)
            }

            else -> {
                throw IllegalStateException("Could not identify Question type to generate response controls")
            }
        }
    }

    private fun inflateRadioResponseControl(options: List<Option>): RadioResponseControl {
        val radioResponseControl = RadioResponseControl(context, options)
        radioResponseControl.orientation = RadioGroup.HORIZONTAL
        return radioResponseControl
    }

    //TODO: Update questionnaire to actually provide real input types and not only text
    private fun inflateTextInputResponseControl(hintId: String?, inputType: Int): TextInputLayout {
        val textInputLayout = createTextInputLayout()
        textInputLayout.hint =
            context.getString(SurveyEngine.getStringResId(hintId))
        textInputLayout.editText?.inputType = inputType
        textInputLayout.editText?.isSingleLine = true
        return textInputLayout
    }

    private fun setInputListener(
        isSubQuestion: Boolean,
        question: Question,
        radioResponseControl: RadioResponseControl
    ) {
        radioResponseControl.setOnCheckedChangeListener { _, _ ->
            val selectedOption = radioResponseControl.selectedOption!!
            val response =
                generateResponse(isSubQuestion, question.questionId, selectedOption)

            responseListener?.onResponse(response)
        }
    }

    private fun setInputListener(
        isSubQuestion: Boolean,
        question: Question,
        textInputLayout: TextInputLayout
    ) {
        textInputLayout.editText?.addTextChangedListener { newText ->
            //Expose response data for validation
        }
        textInputLayout.editText?.setOnKeyListener { _, keyCode, keyEvent ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && keyEvent.action == KeyEvent.ACTION_DOWN) {
                val responseValue =
                    textInputLayout.editText?.text.toString() //TODO: Consider .trim
                val response =
                    generateResponse(isSubQuestion, question.questionId, responseValue)

                responseListener?.onResponse(response)
            }

            false
        }
    }

    private fun setChangeFocusListener(
        textInputLayout: TextInputLayout,
        nextFocusView: View
    ) {
        textInputLayout.editText?.addTextChangedListener { newText ->
            //Expose response data for validation
        }
        textInputLayout.editText?.setOnKeyListener { _, keyCode, keyEvent ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && keyEvent.action == KeyEvent.ACTION_DOWN) {
                nextFocusView.requestFocus()
            }

            false
        }
    }

    private fun reInstateControl(response: Any, radioResponseControl: RadioResponseControl) {
        if (response !is Option)
            throw IllegalArgumentException("Incorrect response type to reInstateControl on RadioResponseControl")

        radioResponseControl.setSate(response)
    }

    private fun reInstateControl(response: Any, textInputLayout: TextInputLayout) {
        if (response !is String)
            throw IllegalArgumentException("Incorrect response type to reInstateControl on TextInputLayout control")

        textInputLayout.editText?.setText(response)
    }

    private fun createLinearLayout(): LinearLayout {
        val linearLayout = LinearLayout(context)
        linearLayout.layoutParams =
            LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        linearLayout.orientation = LinearLayout.VERTICAL
        return linearLayout
    }

    private fun createTextInputLayout(): TextInputLayout {
        return LayoutInflater.from(context)
            .inflate(
                R.layout.question_text_input_layout,
                responseControlContainer,
                false
            ) as TextInputLayout
    }
}