package parkhomov.andrew.base.utils

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat

fun Context.getDrawableFromId(resId: Int): Drawable = ContextCompat.getDrawable(this, resId)!!