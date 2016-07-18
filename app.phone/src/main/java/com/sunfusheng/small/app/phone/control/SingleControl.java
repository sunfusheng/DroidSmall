package com.sunfusheng.small.app.phone.control;

import com.sunfusheng.small.app.phone.model.PhoneEntity;
import com.sunfusheng.small.lib.framework.annotation.AsyncAtomMethod;
import com.sunfusheng.small.lib.framework.base.BaseControl;
import com.sunfusheng.small.lib.framework.proxy.MessageProxy;

/**
 * Created by sunfusheng on 15/11/5.
 */
public class SingleControl extends BaseControl {

    private static HttpApi mApi;

    public SingleControl(MessageProxy mMessageCallBack) {
        super(mMessageCallBack);
        mApi = HttpApi.getInstance();
    }

    @AsyncAtomMethod
    public void getPhoneLocation(String tel) {
        try {
            PhoneEntity phoneEntity = mApi.getPhoneLocation(tel);
            mModel.put("PhoneEntity", phoneEntity);
            sendMessage("getPhoneLocationCallBack");
        } catch (Exception e) {
            dealWithException(e);
        }
    }

}
