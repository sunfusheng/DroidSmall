package com.sunfusheng.small.app.shanghai.weather.control;

/**
 * Created by sunfusheng on 15/11/19.
 *
 * http://www.haoservice.com/center
 */
public interface UrlManager {

    String API_KEY = "e11c0040a991442a9426aae6976bd919";

    String APISTORE_BASE_URL = "http://apis.haoservice.com/";

    // 根据城市名称查询天气
    String URL_CITY_WEATHER = APISTORE_BASE_URL + "weather";

}
