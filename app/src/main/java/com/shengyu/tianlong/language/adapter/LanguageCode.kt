/*******************************************************************************
 *  Copyright (C) 2023 by shengyu.
 ******************************************************************************/

package com.shengyu.tianlong.language.adapter

/**
 * Adapter for language list
 *
 * @param code language code
 * @param textShow -> The text for language list display
 */
enum class LanguageCode(val code: String,val textShow: String) {
    ZH_TW("zh_tw", "繁體中文"),
    ZH_CN("zh_cn", "简体中文"),
    EN("en", "English"),
    JA("ja", "日本語"),
    KO("ko", "한국인"),
    ES("es", "Español"),
    ID("id", "Bahasa Indonesia"),
    TH("th", "ภาษาไทย"),
    VI("vi", "Tiếng Việt")
}