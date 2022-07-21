package com.sdb.retail.actionbutton.survey

import com.sdb.retail.commons.survey.SurveyViewModel

interface QuestionResponseListener {

    fun onResponse(response: SurveyViewModel.Response)

    fun onValidate()
}