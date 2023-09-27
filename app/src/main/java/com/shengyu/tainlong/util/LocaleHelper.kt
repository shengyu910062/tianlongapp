/*******************************************************************************
 *  Copyright (C) 2023 by shengyu.
 ******************************************************************************/

package com.shengyu.tainlong.util

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import java.util.*

/**
 *  This contains locale changes configuration for language binding
 */
class LocaleHelper {

    companion object {
        const val LANGUAGE_CODE_DELIMITER = "_"

        fun setLocale(context: Context, language: String): Context {

            var localeLanguage = language.substringBefore(LANGUAGE_CODE_DELIMITER, language)
            var localeCountry = language.substringAfter(LANGUAGE_CODE_DELIMITER, language)
            var locale = Locale(localeLanguage, localeCountry)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                return updateResources(context, locale)
            }

            return updateResourcesLegacy(context, locale)
        }

        @TargetApi(Build.VERSION_CODES.N)
        private fun updateResources(context: Context, locale: Locale): Context {
            Locale.setDefault(locale)

            val configuration = context.resources.configuration
            configuration.setLocale(locale)
            configuration.setLayoutDirection(locale)

            return context.createConfigurationContext(configuration)
        }

        private fun updateResourcesLegacy(context: Context, locale: Locale): Context {
            Locale.setDefault(locale)

            val resources = context.resources
            val configuration = resources.configuration
            configuration.setLocale(locale)
            configuration.setLayoutDirection(locale)

            return context.createConfigurationContext(configuration)
        }

        fun onAttach(context: Context): Context {
            return setLocale(context, getCurrentLanguageCode())
        }

        fun getCurrentLanguageCode(): String {
            return SharePreference.language
        }
    }
}