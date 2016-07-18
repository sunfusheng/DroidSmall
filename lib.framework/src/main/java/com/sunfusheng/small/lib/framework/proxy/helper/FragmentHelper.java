package com.sunfusheng.small.lib.framework.proxy.helper;

import com.sunfusheng.small.lib.framework.base.BaseAsyncFragment;
import com.sunfusheng.small.lib.framework.base.BaseControl;
import com.sunfusheng.small.lib.framework.proxy.handler.BaseHandler;

public class FragmentHelper<T extends BaseControl, R extends BaseAsyncFragment> extends BaseHelper<T, R> {

    public FragmentHelper(R refrenceObj, BaseHandler handler) {
        super(refrenceObj, handler);
    }

    public void onDestoryView() {
        if (mControl != null) {
            mControl.onDestroyView();
        }
    }

    public void onAttachView() {}
}
