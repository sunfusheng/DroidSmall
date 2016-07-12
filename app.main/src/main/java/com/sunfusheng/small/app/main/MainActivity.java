package com.sunfusheng.small.app.main;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import net.wequick.small.Small;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @Bind(R.id.tv_weather)
    TextView tvWeather;
    @Bind(R.id.tv_phone)
    TextView tvPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initListener();
    }

    private void initListener() {
        tvPhone.setOnClickListener(this);
        tvWeather.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_phone:
                Small.openUri("phone", MainActivity.this);
                break;
            case R.id.tv_weather:
                Small.openUri("weather", MainActivity.this);
                break;
        }
    }
}
