/*******************************************************************************
 *  Copyright (C) 2023 by shengyu.
 ******************************************************************************/

package com.shengyu.tainlong.util

import android.util.Log

/**
 * Wrapper around the android logger
 */
object Logger {
    // ------------------------------------------------------------------------
    // STATIC DATA
    // ------------------------------------------------------------------------
    private var sTagPrefix: String? = ""
    private var sLogToCrashlytics = false
    private const val sMaxCharCount = 1000

    // ------------------------------------------------------------------------
    // STATIC METHODS
    // ------------------------------------------------------------------------
    /**
     * Sets whether to send logs to crashlytics
     * @param aLogToCrashlytics set to true if you want logs to be sent to crashlytics
     */
    fun setLogToCrashlytics(aLogToCrashlytics: Boolean) {
        sLogToCrashlytics = aLogToCrashlytics
    }

    fun e(aTag: String?, aMessage: String) {
        writeLog(Log.ERROR, aTag, aMessage)
    }

    fun d(aTag: String?, aMessage: String) {
        writeLog(Log.DEBUG, aTag, aMessage)
    }

    fun d(aTag: String?, aMessage: String, aException: Throwable?) {
        writeLog(Log.DEBUG, aTag, aMessage, aException)
    }

    fun v(aTag: String?, aMessage: String) {
        writeLog(Log.VERBOSE, aTag, aMessage)
    }

    fun i(aTag: String?, aMessage: String) {
        writeLog(Log.INFO, aTag, aMessage)
    }

    fun w(aTag: String?, aMessage: String) {
        writeLog(Log.WARN, aTag, aMessage)
    }

    fun e(aTag: String?, aMessage: String, aException: Throwable?) {
        writeLog(Log.ERROR, aTag, aMessage, aException)
    }

    private fun writeLog(aPriority: Int, aTag: String?, aMessage: String) {
        printLine(aPriority, getPrefixedTag(aTag), aMessage)
    }

    private fun writeLog(aPriority: Int, aTag: String?, aMessage: String, aThrowable: Throwable?) {
        printLine(
            aPriority, getPrefixedTag(aTag), """
             $aMessage
             ${Log.getStackTraceString(aThrowable)}""".trimIndent()
        )
    }

    private fun printLine(aPriority: Int, aTag: String?, aMessage: String) {
        var theIndex = 0
        while (theIndex < aMessage.length) {
            val theNewLineIndex = aMessage.indexOf('\n', theIndex)
            var theEndIndex: Int
            theEndIndex = if (theNewLineIndex == -1) {
                aMessage.length
            } else {
                theNewLineIndex
            }
            theEndIndex = Math.min(theIndex + sMaxCharCount, theEndIndex)
            val theSubMessage = aMessage.substring(theIndex, theEndIndex)
            Log.println(aPriority, aTag, theSubMessage)
            theIndex += theSubMessage.length
            if (theNewLineIndex != -1) {
                theIndex++ // skip over new line
            }
        }
    }

    fun setTagPrefix(aTagPrefix: String?) {
        sTagPrefix = aTagPrefix
    }

    private fun getPrefixedTag(aTag: String?): String? {
        return sTagPrefix + aTag
    }
}