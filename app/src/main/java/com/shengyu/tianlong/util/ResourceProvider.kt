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
 * @param context MainApplication context
 */
open class ResourceProvider private constructor(context: Context) {

    init {
        mContext = LocaleHelper.setLocale(context, SharePreference.language)
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private lateinit var mContext: Context

        @SuppressLint("StaticFieldLeak")
        protected var sInst: ResourceProvider? = null


        fun createInstance(context: Context) {
            if (sInst == null) {
                sInst = ResourceProvider(context)
            }
        }

        fun refreshLanguage() {
            mContext = LocaleHelper.setLocale(mContext, SharePreference.language)
        }

        fun getContext(): Context {
            return mContext
        }

        fun getString(resId: Int): String {
            return mContext.getString(resId)
        }

        fun getString(resId: Int, value: String): String {
            return mContext.getString(resId, value)
        }

        fun getColor(resId: Int): Int {
            return ContextCompat.getColor(mContext, resId)
        }

        fun getDrawable(resID: Int): Drawable {
            return ContextCompat.getDrawable(mContext, resID)!!
        }

        fun getInt(resId: Int): Int {
            return mContext.resources.getInteger(resId)
        }

        fun getStringArray(resId: Int): Array<String> {
            return mContext.resources.getStringArray(resId)
        }

        fun getColorStateList(@ColorRes color: Int): ColorStateList? {
            return ContextCompat.getColorStateList(mContext, color)
        }

        fun getTypedArray(resId: Int): TypedArray {
            return mContext.resources.obtainTypedArray(resId)
        }

        fun getIntArray(resId: Int): IntArray {
            return mContext.resources.getIntArray(resId)
        }

        fun getFont(resId: Int): Typeface {
            return ResourcesCompat.getFont(mContext, resId)!!
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
            return mContext.contentResolver
        }
    }
}
