package com.xxx.checkout.base

import androidx.fragment.app.Fragment
import com.xxx.checkout.R
import com.xxx.checkout.utils.FragmentUtils

class BaseFragment(layoutRes: Int) : Fragment(layoutRes) {

    fun navigate(fragment: Fragment) {
        FragmentUtils.replace(
            FragmentUtils.ReplaceOption<Any>()
                .with(requireActivity())
                .setContainerId(R.id.fragment_container)
                .setFragment(fragment)
                .addToBackStack(true)
        )
    }
}