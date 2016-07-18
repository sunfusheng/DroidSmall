package com.sunfusheng.small.app.weather.control;

/**
 * Created by sunfusheng on 15/11/19.
 */
public interface UrlManager {

    // 百度ApiStore api key
    String APISTORE_API_KEY = "35f39c8f685e249ebfe9b073ab5ab79d";

    // 百度ApiStore base url
    String APISTORE_BASE_URL = "http://apis.baidu.com/apistore/";

    // 天气查询－根据城市名称
    String URL_CITY_WEATHER = APISTORE_BASE_URL + "weatherservice/cityname";

}
