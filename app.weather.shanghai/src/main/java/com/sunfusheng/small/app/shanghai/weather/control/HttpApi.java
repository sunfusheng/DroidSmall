package com.sunfusheng.small.app.shanghai.weather.control;

import com.sunfusheng.small.app.shanghai.weather.model.CityWeatherEntity;
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

    // 天气查询－根据城市名称
    public CityWeatherEntity getCityWeather(String cityname) throws IOException, com.alibaba.fastjson.JSONException, org.json.JSONException {
        return OkHttpProxy.get().url(URL_CITY_WEATHER)
                .addParams("key", API_KEY)
                .addParams("cityname", cityname)
                .build()
                .execute(CityWeatherEntity.class);
    }

}
