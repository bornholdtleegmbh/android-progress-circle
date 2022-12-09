package de.bornholdtlee.progress_circle.utils

import android.content.res.Resources

fun Int.toPx() = (this * Resources.getSystem().displayMetrics.density).toInt()
fun Int.toDp() = (this / Resources.getSystem().displayMetrics.density).toInt()
