package com.sunfusheng.small.app.shanghai.weather.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.sunfusheng.small.app.shanghai.weather.R;
import com.sunfusheng.small.app.shanghai.weather.control.SingleControl;
import com.sunfusheng.small.app.shanghai.weather.model.CityWeatherEntity;
import com.sunfusheng.small.lib.framework.base.BaseActivity;
import com.sunfusheng.small.lib.framework.util.ToastTip;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShanghaiWeatherActivity extends BaseActivity<SingleControl> {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_weather)
    TextView tvWeather;
    @BindView(R.id.tv_result)
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shanghai_weather);
        ButterKnife.bind(this);

        initView();
        initListener();
    }

    private void initView() {
        initToolBar(toolbar, true, "上海天气");
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
        CityWeatherEntity weatherEntity = mModel.get("CityWeatherEntity");

        if (weatherEntity == null) return;
        if (weatherEntity.getError_code() != 0 && !TextUtils.isEmpty(weatherEntity.getReason())) {
            tvResult.setText(weatherEntity.getReason());
            return;
        }

        if (weatherEntity.getResult() == null) return;
        CityWeatherEntity.ResultEntity.TodayEntity entity = weatherEntity.getResult().getToday();
        if (entity == null) return;

        tvResult.setText(entity.getCity() + "天气" + "\n" +
                "发布时间：" + entity.getDate_y() + " " + entity.getWeek() + "\n" +
                "当前温度：" + entity.getTemperature() + "\n" +
                "风力：" + entity.getWind() + "\n" +
                "建议：" + entity.getDressing_advice());
    }
}
