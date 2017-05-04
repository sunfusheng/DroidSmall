package com.sunfusheng.small.app.shanghai.weather;

import android.app.Application;

import com.sunfusheng.small.lib.framework.DroidFramework;

/**
 * Created by sunfusheng on 16/7/18.
 */
public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DroidFramework.init(this, true);
    }
}
