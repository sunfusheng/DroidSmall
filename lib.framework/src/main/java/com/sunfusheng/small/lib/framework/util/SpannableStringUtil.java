package com.sunfusheng.small.lib.framework.util;

import android.support.annotation.ColorInt;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;

public class SpannableStringUtil {

    public static SpannableString getSpannableString(String content, String tag, @ColorInt int color) {
        if (TextUtils.isEmpty(content)) {
            return new SpannableString("");
        }

        SpannableString spannableString = new SpannableString(content);
        if (TextUtils.isEmpty(tag)) {
            return spannableString;
        }

        int index = content.indexOf(tag);
        if (index != -1) {
            spannableString.setSpan(new ForegroundColorSpan(color), index, index + tag.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        }
        return spannableString;
    }

}