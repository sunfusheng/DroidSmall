package com.sunfusheng.small;

import android.app.Application;

import net.wequick.small.Small;

/**
 * Created by sunfusheng on 16/7/8.
 */
public class DroidApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Small.preSetUp(this);
    }
}
