package com.showslinger.fan.base

import androidx.fragment.app.Fragment
import com.showslinger.fan.R
import com.showslinger.fan.utils.FragmentUtils

open class BaseFragment(layoutRes: Int) : Fragment(layoutRes) {

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