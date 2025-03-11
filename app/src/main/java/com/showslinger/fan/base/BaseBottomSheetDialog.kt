package com.showslinger.fan.base

import android.app.Dialog
import android.os.Bundle
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheetDialog(layoutRes: Int) :
    BottomSheetDialogFragment(layoutRes) {
    open fun isEnableDraggable() = true

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        if (!isEnableDraggable()) {
            bottomSheetDialog.setOnShowListener {
                val bottomSheet = bottomSheetDialog
                    .findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)

                if (bottomSheet != null) {
                    val behavior: BottomSheetBehavior<*> = BottomSheetBehavior.from(bottomSheet)
                    behavior.isDraggable = false
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED)
                }
            }
        }
        return bottomSheetDialog
    }
}
