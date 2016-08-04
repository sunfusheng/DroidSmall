package com.sunfusheng.small.app.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.sunfusheng.small.lib.framework.base.BaseActivity;
import com.sunfusheng.small.lib.framework.util.ToastTip;

import net.wequick.small.Small;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @Bind(R.id.tv_weather)
    TextView tvWeather;
    @Bind(R.id.tv_phone)
    TextView tvPhone;
    @Bind(R.id.tv_number)
    TextView tvNumber;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initData();
        initView();
        initListener();
    }

    private void initData() {
//        Intent intent = new Intent(this, SmallService.class);
//        intent.putExtra("small", SmallService.SMALL_CHECK_UPDATE);
//        startService(intent);
    }

    private void initView() {
        initToolBar(toolbar, false, "Android Small 插件化示例");
    }

    private void initListener() {
        tvPhone.setOnClickListener(this);
        tvWeather.setOnClickListener(this);
        tvNumber.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_weather:
                Small.openUri("weather", mContext);
                break;
            case R.id.tv_phone:
                Small.openUri("phone", mContext);
                break;
            case R.id.tv_number:
                Small.openUri("phone/Number?num=18600604600&toast=Fucking amazing!", mContext);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_update_plugin:
                Intent intent = new Intent(this, SmallService.class);
                intent.putExtra("small", SmallService.SMALL_CHECK_UPDATE);
                startService(intent);
                return true;
            case R.id.action_add_plugin:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, SmallService.class);
        intent.putExtra("small", SmallService.SMALL_UPDATE_BUNDLES);
        startService(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1000 && data != null) {
            String result = data.getStringExtra("result");
            if (!TextUtils.isEmpty(result)) {
                ToastTip.show("回传数据：" + result);
            }
        }
    }

}
