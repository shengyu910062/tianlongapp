/*******************************************************************************
 *  Copyright (C) 2023 by shengyu.
 ******************************************************************************/

package com.shengyu.tianlong.util

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Typeface
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat

/**
 * Common resource provider
 *
 * @param applicationContext MainApplication context
 */
open class ResourceProvider private constructor(applicationContext: Context) {

    init {
        context = LocaleHelper.setLocale(applicationContext, SharePreference.language)
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private lateinit var context: Context

        @SuppressLint("StaticFieldLeak")
        protected var inst: ResourceProvider? = null

        fun createInstance(context: Context) {
            if (inst == null) {
                inst = ResourceProvider(context)
            }
        }

        fun refreshLanguage() {
            context = LocaleHelper.setLocale(context, SharePreference.language)
        }

        fun getContext(): Context {
            return context
        }

        fun getString(resId: Int): String {
            return context.getString(resId)
        }

        fun getString(resId: Int, value: String): String {
            return context.getString(resId, value)
        }

        fun getColor(resId: Int): Int {
            return ContextCompat.getColor(context, resId)
        }

        fun getDrawable(resID: Int): Drawable {
            return ContextCompat.getDrawable(context, resID)!!
        }

        fun getInt(resId: Int): Int {
            return context.resources.getInteger(resId)
        }

        fun getStringArray(resId: Int): Array<String> {
            return context.resources.getStringArray(resId)
        }

        fun getColorStateList(@ColorRes color: Int): ColorStateList? {
            return ContextCompat.getColorStateList(context, color)
        }

        fun getTypedArray(resId: Int): TypedArray {
            return context.resources.obtainTypedArray(resId)
        }

        fun getIntArray(resId: Int): IntArray {
            return context.resources.getIntArray(resId)
        }

        fun getFont(resId: Int): Typeface {
            return ResourcesCompat.getFont(context, resId)!!
        }

        fun getBitmap(@DrawableRes resId: Int): Bitmap? {
            try {
                val drawable = getDrawable(resId)

                if (drawable is BitmapDrawable) {
                    return drawable.bitmap
                }

                drawable.run {
                    val bitmap = Bitmap.createBitmap(
                        drawable.intrinsicWidth,
                        drawable.intrinsicHeight,
                        Bitmap.Config.ARGB_8888
                    )
                    val canvas = Canvas(bitmap)
                    drawable.setBounds(0, 0, canvas.width, canvas.height)
                    drawable.draw(canvas)

                    return bitmap
                }
            } catch (e: Exception) {
                return null
            }
        }

        fun getBitmap(d: Drawable): Bitmap {
            val bitmap =
                Bitmap.createBitmap(d.intrinsicWidth, d.intrinsicHeight, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            d.setBounds(0, 0, canvas.width, canvas.height)
            d.draw(canvas)

            return bitmap
        }

        fun getContentResolver(): ContentResolver {
            return context.contentResolver
        }
    }
}
