package com.xxx.checkout.ui.widget

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.LinearLayout
import android.widget.RadioButton
import androidx.core.view.children
import com.xxx.checkout.R
import com.xxx.checkout.databinding.ItemMultipleChoiceBinding
import com.xxx.checkout.databinding.ItemSingleChoiceBinding
import com.xxx.checkout.databinding.QuestionLargeTextViewBinding
import com.xxx.checkout.databinding.QuestionMultipleChoiceViewBinding
import com.xxx.checkout.databinding.QuestionSingleChoiceViewBinding
import com.xxx.checkout.databinding.QuestionTextViewBinding
import com.xxx.checkout.databinding.QuestionUploadDocumentViewBinding
import com.xxx.checkout.model.Answer
import com.xxx.checkout.model.Question

class QuestionsView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {
    private val inflate by lazy { LayoutInflater.from(context) }
    private val marginItem by lazy {
        context.resources.getDimensionPixelSize(R.dimen.margin_16)
    }

    private var fileUri: Uri? = null

    init {
        orientation = VERTICAL
    }

    fun setQuestions(items: List<Question>) {
        removeAllViews()
        items.forEach {
            val view = when (it.type) {
                Question.Type.TEXT -> createTextQuestion(it)
                Question.Type.LARGE_TEXT -> createLargeTextQuestion(it)
                Question.Type.SINGLE_CHOICE -> createSingleChoiceQuestion(it)
                Question.Type.MULTI_CHOICE -> createMultiChoiceQuestion(it)
                Question.Type.UPLOAD_DOCUMENT -> createUploadDocumentQuestion(it)
            }

            view.tag = it

            addView(view, LayoutParams(-1, -2).apply {
                setMargins(0, 0, 0, marginItem)
            })
        }
    }

    private fun createTextQuestion(question: Question): View {
        val binding = QuestionTextViewBinding.inflate(inflate)
        binding.inputLayout.hint = question.question
        return binding.root
    }

    private fun createLargeTextQuestion(question: Question): View {
        val binding = QuestionLargeTextViewBinding.inflate(inflate)
        binding.inputLayout.hint = question.question
        return binding.root
    }

    private fun createSingleChoiceQuestion(question: Question): View {
        val binding = QuestionSingleChoiceViewBinding.inflate(inflate)
        binding.tvTitle.text = question.question
        question.answers?.forEach { item ->
            val b = ItemSingleChoiceBinding.inflate(inflate)
            b.rd.text = item
            b.rd.setOnCheckedChangeListener { v, s ->
                if (v.isPressed) {
                    v.isChecked = true
                    for (child in binding.rdGroup.children) {
                        if (child is CheckBox && child != v) {
                            child.isChecked = false
                        }
                    }
                }
            }
            binding.rdGroup.addView(b.root)
        }
        return binding.root
    }

    private fun createMultiChoiceQuestion(question: Question): View {
        val binding = QuestionMultipleChoiceViewBinding.inflate(inflate)
        binding.tvTitle.text = question.question
        question.answers?.forEach { item ->
            val b = ItemMultipleChoiceBinding.inflate(inflate)
            b.chk.text = item
            binding.root.addView(b.root)
        }
        return binding.root
    }

    private fun createUploadDocumentQuestion(question: Question): View {
        val binding = QuestionUploadDocumentViewBinding.inflate(inflate)
        binding.tvTitle.text = question.question
        binding.btnUpload.setOnClickListener {
            // todo upload document
        }
        return binding.root
    }

    fun getAnswers(): List<Answer> {
        val answers = mutableListOf<Answer>()

        for (i in 0 until childCount) {
            val view = getChildAt(i)
            getAnswer(view)?.let {
                answers.add(it)
            }
        }

        return answers
    }

    private fun getAnswer(view: View): Answer? {
        val answers = mutableListOf<String>()
        val question = view.tag as? Question ?: return null
        var uri: Uri? = null
        when (question.type) {
            Question.Type.TEXT -> {
                val binding = QuestionTextViewBinding.bind(view)
                val answer = binding.inputLayout.editText?.text?.toString() ?: ""
                answers.add(answer)
            }

            Question.Type.LARGE_TEXT -> {
                val binding = QuestionLargeTextViewBinding.bind(view)
                val answer = binding.inputLayout.editText?.text?.toString() ?: ""
                answers.add(answer)
            }

            Question.Type.SINGLE_CHOICE -> {
                val binding = QuestionSingleChoiceViewBinding.bind(view)
                for (v in binding.rdGroup.children) {
                    if (v is CheckBox && v.isChecked) {
                        answers.add(v.text.toString())
                    }
                }
            }

            Question.Type.MULTI_CHOICE -> {
                val binding = QuestionMultipleChoiceViewBinding.bind(view)
                for (v in binding.root.children) {
                    if (v is CheckBox && v.isChecked) {
                        answers.add(v.text.toString())
                    }
                }
            }

            Question.Type.UPLOAD_DOCUMENT -> {
                uri = fileUri
            }
        }

        return Answer(question, answers, uri)
    }

    fun setAnswers(answers: List<Answer>?) {
        answers ?: return
        answers.forEach { answer ->
            val view = findViewWithTag<View>(answer.question)
            if (view != null) {
                when (answer.question.type) {
                    Question.Type.TEXT -> {
                        val binding = QuestionTextViewBinding.bind(view)
                        binding.inputLayout.editText?.setText(answer.answer.firstOrNull())
                    }

                    Question.Type.LARGE_TEXT -> {
                        val binding = QuestionLargeTextViewBinding.bind(view)
                        binding.inputLayout.editText?.setText(answer.answer.firstOrNull())
                    }

                    Question.Type.SINGLE_CHOICE -> {
                        val binding = QuestionSingleChoiceViewBinding.bind(view)
                        for (v in binding.rdGroup.children) {
                            if (v is RadioButton && answer.answer.contains(v.text.toString())) {
                                v.isChecked = true
                            }
                        }
                    }

                    Question.Type.MULTI_CHOICE -> {
                        val binding = QuestionMultipleChoiceViewBinding.bind(view)
                        for (v in binding.root.children) {
                            if (v is CheckBox && answer.answer.contains(v.text.toString())) {
                                v.isChecked = true
                            }
                        }
                    }

                    Question.Type.UPLOAD_DOCUMENT -> {
                        fileUri = answer.fileUri
                    }
                }
            }
        }
    }
}