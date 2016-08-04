package com.sunfusheng.small.app.shanghai.weather.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.sunfusheng.small.app.shanghai.weather.R;
import com.sunfusheng.small.app.shanghai.weather.control.SingleControl;
import com.sunfusheng.small.app.shanghai.weather.model.CityWeatherDataEntity;
import com.sunfusheng.small.app.shanghai.weather.model.CityWeatherEntity;
import com.sunfusheng.small.lib.framework.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ShanghaiWeatherActivity extends BaseActivity<SingleControl> {

    @Bind(R.id.tv_weather)
    TextView tvWeather;
    @Bind(R.id.tv_result)
    TextView tvResult;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shanghai_weather);
        ButterKnife.bind(this);

        initView();
        initListener();
    }

    private void initView() {
        initToolBar(toolbar, true, "Shanghai`s Weather");
    }

    private void initListener() {
        tvWeather.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_weather:
                mControl.getCityWeather("上海");
                break;
        }
    }

    public void getCityWeatherCallBack() {
        CityWeatherEntity cityWeatherEntity = mModel.get("CityWeatherEntity");
        if (cityWeatherEntity == null || cityWeatherEntity.getRetData() == null) return;
        CityWeatherDataEntity entity = cityWeatherEntity.getRetData();
        tvResult.setText(entity.getCity() + "天气" + "\n" +
                "发布时间：" + entity.getDate() + " " + entity.getTime() + "\n" +
                "当前温度：" + entity.getTemp() + "\n" +
                "最高温度：" + entity.getH_tmp() + "\n" +
                "最低温度：" + entity.getL_tmp() + "\n" +
                "风力：" + entity.getWS());
    }
}
