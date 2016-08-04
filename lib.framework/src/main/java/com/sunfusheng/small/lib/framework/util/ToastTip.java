package com.sunfusheng.small.lib.framework.util;

import android.text.TextUtils;
import android.widget.Toast;

import com.sunfusheng.small.lib.framework.DroidFramework;

/**
 * Created by sunfusheng on 15/8/7.
 */
public class ToastTip {

    private static Toast mToast;

    public static void show(String message) {
        if (TextUtils.isEmpty(message)) return;
        int duration;
        if (message.length() > 10) {
            duration = Toast.LENGTH_LONG; //如果字符串比较长，那么显示的时间也长一些。
        } else {
            duration = Toast.LENGTH_SHORT;
        }
        if (mToast == null) {
            mToast = Toast.makeText(DroidFramework.getContext(), message, duration);
        } else {
            mToast.setText(message);
            mToast.setDuration(duration);
        }
        mToast.show();
    }

}
