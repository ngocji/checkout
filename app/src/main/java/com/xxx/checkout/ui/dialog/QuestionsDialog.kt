package com.xxx.checkout.ui.dialog

import com.xxx.checkout.R
import com.xxx.checkout.base.BaseBottomSheetDialog

class QuestionsDialog : BaseBottomSheetDialog(R.layout.dialog_questions) {

    companion object {
        fun newInstance(): QuestionsDialog {
            return QuestionsDialog()
        }
    }
}