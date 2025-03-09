package com.xxx.checkout.ui.dialog

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.xxx.checkout.R
import com.xxx.checkout.base.BaseBottomSheetDialog
import com.xxx.checkout.databinding.DialogQuestionsBinding
import com.xxx.checkout.ui.MainViewModel
import com.xxx.checkout.utils.viewBinding

class QuestionsDialog : BaseBottomSheetDialog(R.layout.dialog_questions) {
    private val binding by viewBinding(DialogQuestionsBinding::bind)
    private val viewModel by activityViewModels<MainViewModel>()

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
                viewModel.startPay()
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