package com.sunfusheng.small.app.shanghai.weather.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sunfusheng on 15/11/20.
 */
public class CityWeatherEntity implements Serializable {


    /**
     * error_code : 0
     * reason : 成功
     * result : {"sk":{"temp":"25","wind_direction":"西南风","wind_strength":"4级","humidity":"31","time":"17:37"},"today":{"city":"北京","date_y":"2017年05月03日","week":"星期三","temperature":"16~28","weather":"阴","fa":"02","fb":"02","wind":"西南风 3-4 级","dressing_index":"热","dressing_advice":"天气热，建议着短裙、短裤、短薄外套、T恤等夏季服装。","uv_index":"弱","comfort_index":"--","wash_index":"较适宜","travel_index":"较适宜","exercise_index":"较适宜","drying_index":"--"},"future":[{"temperature":"13~27","weather":"多云","fa":"01","fb":"00","wind":"北风 3-4 级","week":"星期四","date":"20170504"},{"temperature":"10~24","weather":"晴","fa":"00","fb":"00","wind":"北风 4-5 级","week":"星期五","date":"20170505"},{"temperature":"15~29","weather":"晴","fa":"00","fb":"00","wind":"南风 3-4 级","week":"星期六","date":"20170506"},{"temperature":"17~32","weather":"晴","fa":"00","fb":"00","wind":"南风 微风","week":"星期日","date":"20170507"},{"temperature":"16~33","weather":"多云","fa":"01","fb":"00","wind":"东南风 微风","week":"星期一","date":"20170508"},{"temperature":"18~34","weather":"晴","fa":"00","fb":"01","wind":"西南风 微风","week":"星期二","date":"20170509"},{"temperature":"16~32","weather":"晴","fa":"00","fb":"01","wind":"东北风 微风","week":"星期三","date":"20170510"},{"temperature":"17~32","weather":"多云","fa":"01","fb":"01","wind":" 微风","week":"星期四","date":"20170511"},{"temperature":"18~35","weather":"晴","fa":"00","fb":"01","wind":"西北风 4级","week":"星期五","date":"20170512"}]}
     */

    private int error_code;
    private String reason;
    private ResultEntity result;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ResultEntity getResult() {
        return result;
    }

    public void setResult(ResultEntity result) {
        this.result = result;
    }

    public static class ResultEntity {
        /**
         * sk : {"temp":"25","wind_direction":"西南风","wind_strength":"4级","humidity":"31","time":"17:37"}
         * today : {"city":"北京","date_y":"2017年05月03日","week":"星期三","temperature":"16~28","weather":"阴","fa":"02","fb":"02","wind":"西南风 3-4 级","dressing_index":"热","dressing_advice":"天气热，建议着短裙、短裤、短薄外套、T恤等夏季服装。","uv_index":"弱","comfort_index":"--","wash_index":"较适宜","travel_index":"较适宜","exercise_index":"较适宜","drying_index":"--"}
         * future : [{"temperature":"13~27","weather":"多云","fa":"01","fb":"00","wind":"北风 3-4 级","week":"星期四","date":"20170504"},{"temperature":"10~24","weather":"晴","fa":"00","fb":"00","wind":"北风 4-5 级","week":"星期五","date":"20170505"},{"temperature":"15~29","weather":"晴","fa":"00","fb":"00","wind":"南风 3-4 级","week":"星期六","date":"20170506"},{"temperature":"17~32","weather":"晴","fa":"00","fb":"00","wind":"南风 微风","week":"星期日","date":"20170507"},{"temperature":"16~33","weather":"多云","fa":"01","fb":"00","wind":"东南风 微风","week":"星期一","date":"20170508"},{"temperature":"18~34","weather":"晴","fa":"00","fb":"01","wind":"西南风 微风","week":"星期二","date":"20170509"},{"temperature":"16~32","weather":"晴","fa":"00","fb":"01","wind":"东北风 微风","week":"星期三","date":"20170510"},{"temperature":"17~32","weather":"多云","fa":"01","fb":"01","wind":" 微风","week":"星期四","date":"20170511"},{"temperature":"18~35","weather":"晴","fa":"00","fb":"01","wind":"西北风 4级","week":"星期五","date":"20170512"}]
         */

        private SkEntity sk;
        private TodayEntity today;
        private List<FutureEntity> future;

        public SkEntity getSk() {
            return sk;
        }

        public void setSk(SkEntity sk) {
            this.sk = sk;
        }

        public TodayEntity getToday() {
            return today;
        }

        public void setToday(TodayEntity today) {
            this.today = today;
        }

        public List<FutureEntity> getFuture() {
            return future;
        }

        public void setFuture(List<FutureEntity> future) {
            this.future = future;
        }

        public static class SkEntity {
            /**
             * temp : 25
             * wind_direction : 西南风
             * wind_strength : 4级
             * humidity : 31
             * time : 17:37
             */

            private String temp;
            private String wind_direction;
            private String wind_strength;
            private String humidity;
            private String time;

            public String getTemp() {
                return temp;
            }

            public void setTemp(String temp) {
                this.temp = temp;
            }

            public String getWind_direction() {
                return wind_direction;
            }

            public void setWind_direction(String wind_direction) {
                this.wind_direction = wind_direction;
            }

            public String getWind_strength() {
                return wind_strength;
            }

            public void setWind_strength(String wind_strength) {
                this.wind_strength = wind_strength;
            }

            public String getHumidity() {
                return humidity;
            }

            public void setHumidity(String humidity) {
                this.humidity = humidity;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }
        }

        public static class TodayEntity {
            /**
             * city : 北京
             * date_y : 2017年05月03日
             * week : 星期三
             * temperature : 16~28
             * weather : 阴
             * fa : 02
             * fb : 02
             * wind : 西南风 3-4 级
             * dressing_index : 热
             * dressing_advice : 天气热，建议着短裙、短裤、短薄外套、T恤等夏季服装。
             * uv_index : 弱
             * comfort_index : --
             * wash_index : 较适宜
             * travel_index : 较适宜
             * exercise_index : 较适宜
             * drying_index : --
             */

            private String city;
            private String date_y;
            private String week;
            private String temperature;
            private String weather;
            private String fa;
            private String fb;
            private String wind;
            private String dressing_index;
            private String dressing_advice;
            private String uv_index;
            private String comfort_index;
            private String wash_index;
            private String travel_index;
            private String exercise_index;
            private String drying_index;

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getDate_y() {
                return date_y;
            }

            public void setDate_y(String date_y) {
                this.date_y = date_y;
            }

            public String getWeek() {
                return week;
            }

            public void setWeek(String week) {
                this.week = week;
            }

            public String getTemperature() {
                return temperature;
            }

            public void setTemperature(String temperature) {
                this.temperature = temperature;
            }

            public String getWeather() {
                return weather;
            }

            public void setWeather(String weather) {
                this.weather = weather;
            }

            public String getFa() {
                return fa;
            }

            public void setFa(String fa) {
                this.fa = fa;
            }

            public String getFb() {
                return fb;
            }

            public void setFb(String fb) {
                this.fb = fb;
            }

            public String getWind() {
                return wind;
            }

            public void setWind(String wind) {
                this.wind = wind;
            }

            public String getDressing_index() {
                return dressing_index;
            }

            public void setDressing_index(String dressing_index) {
                this.dressing_index = dressing_index;
            }

            public String getDressing_advice() {
                return dressing_advice;
            }

            public void setDressing_advice(String dressing_advice) {
                this.dressing_advice = dressing_advice;
            }

            public String getUv_index() {
                return uv_index;
            }

            public void setUv_index(String uv_index) {
                this.uv_index = uv_index;
            }

            public String getComfort_index() {
                return comfort_index;
            }

            public void setComfort_index(String comfort_index) {
                this.comfort_index = comfort_index;
            }

            public String getWash_index() {
                return wash_index;
            }

            public void setWash_index(String wash_index) {
                this.wash_index = wash_index;
            }

            public String getTravel_index() {
                return travel_index;
            }

            public void setTravel_index(String travel_index) {
                this.travel_index = travel_index;
            }

            public String getExercise_index() {
                return exercise_index;
            }

            public void setExercise_index(String exercise_index) {
                this.exercise_index = exercise_index;
            }

            public String getDrying_index() {
                return drying_index;
            }

            public void setDrying_index(String drying_index) {
                this.drying_index = drying_index;
            }
        }

        public static class FutureEntity {
            /**
             * temperature : 13~27
             * weather : 多云
             * fa : 01
             * fb : 00
             * wind : 北风 3-4 级
             * week : 星期四
             * date : 20170504
             */

            private String temperature;
            private String weather;
            private String fa;
            private String fb;
            private String wind;
            private String week;
            private String date;

            public String getTemperature() {
                return temperature;
            }

            public void setTemperature(String temperature) {
                this.temperature = temperature;
            }

            public String getWeather() {
                return weather;
            }

            public void setWeather(String weather) {
                this.weather = weather;
            }

            public String getFa() {
                return fa;
            }

            public void setFa(String fa) {
                this.fa = fa;
            }

            public String getFb() {
                return fb;
            }

            public void setFb(String fb) {
                this.fb = fb;
            }

            public String getWind() {
                return wind;
            }

            public void setWind(String wind) {
                this.wind = wind;
            }

            public String getWeek() {
                return week;
            }

            public void setWeek(String week) {
                this.week = week;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }
        }
    }
}
