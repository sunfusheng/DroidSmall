package com.sunfusheng.small.app.weather.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.sunfusheng.small.app.weather.R;
import com.sunfusheng.small.app.weather.control.SingleControl;
import com.sunfusheng.small.app.weather.model.CityWeatherEntity;
import com.sunfusheng.small.lib.framework.base.BaseActivity;
import com.sunfusheng.small.lib.framework.util.ToastTip;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BeijingWeatherActivity extends BaseActivity<SingleControl> {

    @BindView(R.id.tv_weather)
    TextView tvWeather;
    @BindView(R.id.tv_result)
    TextView tvResult;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_status)
    TextView tvStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        ButterKnife.bind(this);

        initView();
        initListener();
    }

    private void initView() {
        initToolBar(toolbar, true, "北京天气");

//        tvStatus.setVisibility((getSettingsSharedPreferences().small_update() == 1) ? View.VISIBLE : View.GONE);
    }

    private void initListener() {
        tvWeather.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_weather:
                mControl.getCityWeather("北京");
                break;
        }
    }

    public void getCityWeatherCallBack() {
        CityWeatherEntity weatherEntity = mModel.get("CityWeatherEntity");

        if (weatherEntity == null) return;
        if (weatherEntity.getError_code() != 0 && !TextUtils.isEmpty(weatherEntity.getReason())) {
            ToastTip.show(weatherEntity.getReason());
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
