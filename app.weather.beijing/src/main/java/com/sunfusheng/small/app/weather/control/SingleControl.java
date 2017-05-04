package com.sunfusheng.small.app.weather.control;


import com.sunfusheng.small.app.weather.model.CityWeatherEntity;
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


    @AsyncAtomMethod(withCancelableDialog = true, withMessage = "正在查询天气...")
    public void getCityWeather(String cityname) {
        try {
            CityWeatherEntity entity = mApi.getCityWeather(cityname);
            mModel.put("CityWeatherEntity", entity);
            sendMessage("getCityWeatherCallBack");
        } catch (Exception e) {
            dealWithException(e);
        }
    }

}
