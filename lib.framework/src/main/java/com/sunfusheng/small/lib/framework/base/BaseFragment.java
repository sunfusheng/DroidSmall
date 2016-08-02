package com.sunfusheng.small.lib.framework.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.orhanobut.logger.Logger;
import com.sunfusheng.small.lib.framework.DroidFramework;
import com.sunfusheng.small.lib.framework.sharedpreferences.SettingsSharedPreferences;

import de.devland.esperandro.Esperandro;

/**
 * Created by sunfusheng on 16/7/18.
 */
public class BaseFragment<T extends BaseControl> extends BaseAsyncFragment<T> implements View.OnClickListener {

    protected Context mContext;
    protected Activity mActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.i(DroidFramework.LOG_FRAGMENT, "(" + getClass().getSimpleName() + ".java:1)");

        mContext = getContext();
        mActivity = getActivity();
    }

    @Override
    public void onClick(View v) {
    }

    protected <P> P getSharedPreferences(Class<P> spClass) {
        return Esperandro.getPreferences(spClass, getActivity());
    }

    protected SettingsSharedPreferences getSettingsSharedPreferences() {
        return getSharedPreferences(SettingsSharedPreferences.class);
    }

}
