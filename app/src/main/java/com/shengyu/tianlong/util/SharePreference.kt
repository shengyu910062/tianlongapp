/*******************************************************************************
 *  Copyright (C) 2023 by shengyu.
 ******************************************************************************/

package com.shengyu.tianlong.util

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.shengyu.tianlong.language.adapter.LanguageCode
import java.lang.reflect.Type
import java.util.Locale

/**
 * Common util for accessing and modifying preference data
 *
 * @param context MainApplication context
 */
open class SharePreference protected constructor(context: Context) {
    init {
        sharedPreference = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)
    }

    companion object {
        const val PREF_FILE = "TIAN_LONG_APP"

        private const val PREF_KEY_LOCAL_LANGUAGE = "PREF_KEY_LOCAL_LANGUAGE"
        const val USE_LOCAL_LANGUAGE_SETTING = true

        protected var inst: SharePreference? = null
        protected var sharedPreference: SharedPreferences? = null

        fun createInstance(context: Context) {
            if (inst == null) {
                inst = SharePreference(context)
            }
        }

        val language: String
            get() {
                val result: String = if (USE_LOCAL_LANGUAGE_SETTING) {
                    if (localLanguage.isBlank()) {
                        systemLanguage
                    } else {
                        localLanguage
                    }
                } else {
                    LanguageCode.ZH_TW.code
                }

                return result
            }

        /**
         * Local language record
         */
        var localLanguage: String
            set(value) {
                putString(PREF_KEY_LOCAL_LANGUAGE, value)
            }
            get() = getString(PREF_KEY_LOCAL_LANGUAGE)

        /**
         * System language record
         */
        private val systemLanguage: String
            get() {
                return if (Locale.getDefault().language.lowercase().equals("zh")) {
                    Locale.getDefault().language.lowercase() + "_" + Locale.getDefault().country.lowercase()
                } else {
                    Locale.getDefault().language.lowercase()
                }
            }

        // region SharedPreference put and get function
        private fun putString(key: String, value: String) {
            sharedPreference?.edit()
                ?.putString(key, value)
                ?.apply()
        }

        private fun getString(key: String): String {
            return sharedPreference?.getString(key, "") ?: ""
        }

        private fun getInt(key: String, defaultValue: Int = 0): Int {
            return sharedPreference?.getInt(key, defaultValue) ?: 0
        }

        private fun putInt(key: String, value: Int) {
            sharedPreference?.edit()
                ?.putInt(key, value)
                ?.apply()
        }

        private fun getLong(key: String, defaultValue: Long = 0): Long {
            return sharedPreference?.getLong(key, defaultValue) ?: 0L
        }

        private fun putLong(key: String, value: Long) {
            sharedPreference?.edit()
                ?.putLong(key, value)
                ?.apply()
        }

        private fun getBoolean(key: String, defaultValue: Boolean = false): Boolean {
            return sharedPreference?.getBoolean(key, defaultValue) ?: defaultValue
        }

        private fun putBoolean(key: String, value: Boolean) {
            sharedPreference?.edit()
                ?.putBoolean(key, value)
                ?.apply()
        }

        /**
         * Saves object into the Preferences.
         *
         * @param classMame Object of model class (of type [T]) to save
         * @param key Key with which Shared preferences to
         **/
        private fun <T> put(key: String, classMame: T) {
            //Convert object to JSON String.
            val jsonString = GsonBuilder().create().toJson(classMame)
            //Save that String in SharedPreferences
            putString(key, jsonString)
        }

        /**
         * Get object from the Preferences.
         *
         * @param key Key with which Shared preferences to
         **/
        private inline fun <reified T> get(key: String): T? {
            return try {
                val value = getString(key)
                //Convert object to JSON String.
                GsonBuilder().create().fromJson<T>(value, object : TypeToken<T>() {}.type)
            } catch (e: Exception) {
                null
            }
        }

        /**
         * Get array from the Preferences.
         *
         * @param key Key with which Shared preferences to
         **/
        private fun <T> parseArray(key: String, typeToken: Type): T? {
            val json = sharedPreference?.getString(key, null) ?: return null
            val gson = GsonBuilder().create()
            return gson.fromJson(json, typeToken)
        }
        // endregion
    }
}
