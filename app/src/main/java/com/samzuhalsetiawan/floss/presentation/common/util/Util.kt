package com.samzuhalsetiawan.floss.presentation.common.util

import android.content.Context
import android.content.ContextWrapper
import androidx.activity.ComponentActivity

tailrec fun Context.getActivity(): ComponentActivity? = when (this) {
   is ComponentActivity -> this
   is ContextWrapper -> baseContext.getActivity()
   else -> null
}
