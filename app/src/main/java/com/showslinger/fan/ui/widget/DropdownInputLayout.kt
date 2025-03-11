package com.showslinger.fan.ui.widget

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.fragment.app.FragmentManager
import com.google.android.material.textfield.TextInputLayout
import com.kevin.wheel.WheelView
import com.showslinger.fan.R
import com.showslinger.fan.base.BaseBottomSheetDialog
import com.showslinger.fan.databinding.DialogSelectDropdownBinding
import com.showslinger.fan.utils.viewBinding

class DropdownInputLayout : TextInputLayout {
    private var selectedItemIndex = -1
    private var items: List<Any>? = null
    private var previewItems: List<String> = emptyList()
    private var onSelectedChanged: ((Int) -> Unit)? = null
    private var fragmentManager: FragmentManager? = null

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        inflate(context, R.layout.dropdown_input_layout, this)
        endIconMode = END_ICON_CUSTOM
        setEndIconDrawable(R.drawable.ic_drop_down)
        setEndIconOnClickListener {
            doShowSelect()
        }

        editText?.setOnClickListener {
            doShowSelect()
        }
    }

    fun onSelectedChanged(onSelected: (Int) -> Unit) {
        this.onSelectedChanged = onSelected
    }

    fun <T : Any> setData(fragmentManager: FragmentManager, items: List<T>, selected: T?) {
        this.fragmentManager = fragmentManager
        this.items = items
        this.selectedItemIndex = if (selected != null) items.indexOfFirst { it == selected } else -1
        this.previewItems = items.map { it.toString() }
        this.editText?.setText(selected?.toString().orEmpty())
    }

    private fun doShowSelect() {
        Sheet.show(
            fragmentManager ?: return,
            previewItems,
            selectedItemIndex
        ) { newIndex ->
            if (newIndex != selectedItemIndex) {
                selectedItemIndex = newIndex
                this.editText?.setText(previewItems.getOrNull(newIndex).orEmpty())
                onSelectedChanged?.invoke(selectedItemIndex)
            }
        }
    }


    class Sheet : BaseBottomSheetDialog(R.layout.dialog_select_dropdown),
        WheelView.OnItemSelectedListener {
        private val binding by viewBinding(DialogSelectDropdownBinding::bind)
        private var items: List<String> = emptyList()
        private var selectedItemIndex: Int = -1
        private var onSelected: ((Int) -> Unit)? = null

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            binding.btnCancel.setOnClickListener { dismissAllowingStateLoss() }
            binding.wheelView.setDataItems(items.toMutableList())
            binding.wheelView.setOnItemSelectedListener(this)

            if (selectedItemIndex != -1) {
                binding.wheelView.setSelectedItemPosition(selectedItemIndex)
            }
        }

        override fun onItemSelected(wheelView: WheelView, data: Any, position: Int) {
            selectedItemIndex = position
        }

        override fun onDismiss(dialog: DialogInterface) {
            onSelected?.invoke(selectedItemIndex)
            super.onDismiss(dialog)
        }

        companion object {
            fun show(
                fragmentManager: FragmentManager,
                items: List<String>,
                selectedItemIndex: Int = -1,
                onSelected: (Int) -> Unit
            ) {
                Sheet()
                    .apply {
                        this.items = items
                        this.selectedItemIndex = selectedItemIndex
                        this.onSelected = onSelected
                    }
                    .show(fragmentManager, null)
            }
        }
    }
}