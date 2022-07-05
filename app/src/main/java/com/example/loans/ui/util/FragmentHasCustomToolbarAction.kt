package com.example.loans.ui.util

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

interface FragmentHasCustomToolbarAction {
    fun getCustomAction() : CustomAction
}

class CustomAction(
    @DrawableRes val iconRes: Int,
    @StringRes val textRes: Int,
    val onCustomAction: Runnable
)