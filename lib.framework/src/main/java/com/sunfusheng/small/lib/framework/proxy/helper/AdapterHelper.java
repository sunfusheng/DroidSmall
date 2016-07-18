package com.sunfusheng.small.lib.framework.proxy.helper;

import com.sunfusheng.small.lib.framework.base.BaseAsyncListAdapter;
import com.sunfusheng.small.lib.framework.base.BaseControl;
import com.sunfusheng.small.lib.framework.proxy.handler.AsyncAdapterHandler;

/**
 * Created by sunfusheng on 15/11/5.
 */
public class AdapterHelper<T extends BaseControl, R extends BaseAsyncListAdapter> extends BaseHelper<T, R> {

    public AdapterHelper(R refrenceObj) {
        super(refrenceObj, new AsyncAdapterHandler(refrenceObj));
    }
}
