package com.showslinger.fan.ui.dialog

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.showslinger.fan.R
import com.showslinger.fan.base.BaseBottomSheetDialog
import com.showslinger.fan.databinding.DialogQuestionsBinding
import com.showslinger.fan.ui.MainViewModel
import com.showslinger.fan.utils.viewBinding

class QuestionsDialog : BaseBottomSheetDialog(R.layout.dialog_questions) {
    private val binding by viewBinding(DialogQuestionsBinding::bind)
    private val viewModel by activityViewModels<MainViewModel>()

    override fun isEnableDraggable(): Boolean {
        return false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initData()
    }

    private fun initViews() {
        with(binding) {
            btnBack.setOnClickListener {
                dismissAllowingStateLoss()
            }

            btnContinue.setOnClickListener {
                viewModel.setAnswers(binding.questionView.getAnswers())
                viewModel.addPayment()
                dismissAllowingStateLoss()
            }
        }
    }

    private fun initData() {
        binding.questionView.setQuestions(viewModel.getQuestions())
        binding.questionView.setAnswers(viewModel.getAnswers())
    }

    companion object {
        fun newInstance(): QuestionsDialog {
            return QuestionsDialog()
        }
    }
}