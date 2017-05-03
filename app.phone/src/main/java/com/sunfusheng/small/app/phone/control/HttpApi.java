package com.sunfusheng.small.app.phone.control;

import com.sunfusheng.small.app.phone.model.PhoneEntity;
import com.sunfusheng.small.lib.framework.okhttp.OkHttpProxy;

import java.io.IOException;

/**
 * Created by sunfusheng on 15/11/19.
 */
public class HttpApi implements UrlManager {

    private static HttpApi mApi;

    private HttpApi() {}

    public static HttpApi getInstance() {
        if (mApi == null) {
            synchronized (HttpApi.class) {
                if (mApi == null) {
                    mApi = new HttpApi();
                }
            }
        }
        return mApi;
    }

    // 手机号码归属地
    public PhoneEntity getPhoneLocation(String phone) throws IOException, com.alibaba.fastjson.JSONException, org.json.JSONException {
        return OkHttpProxy.get().url(URL_PHONE_NUM_PLACE)
                .addParams("key", API_KEY)
                .addParams("phone", phone)
                .build()
                .execute(PhoneEntity.class);
    }

}
