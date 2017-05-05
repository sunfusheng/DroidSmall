package com.sunfusheng.small.app.main;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.sunfusheng.small.lib.framework.base.BaseActivity;
import com.sunfusheng.small.lib.framework.util.ToastTip;

import net.wequick.small.Small;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_weihai_phone)
    TextView tvWeihaiPhone;
    @BindView(R.id.tv_beijing_phone)
    TextView tvBeijingPhone;
    @BindView(R.id.tv_beijing_weather)
    TextView tvBeijingWeather;
    @BindView(R.id.tv_shanghai_weather)
    TextView tvShanghaiWeather;
    @BindView(R.id.tv_tip)
    TextView tvTip;

    private LocalBroadcastManager mLocalBroadcastManager;
    private MyBroadcastReceiver mBroadcastReceiver;
    private ProgressDialog mDialog;

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
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
        mBroadcastReceiver = new MyBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("DroidSmall");
        mLocalBroadcastManager.registerReceiver(mBroadcastReceiver, intentFilter);

        if (getSettingsSharedPreferences().manifest_code() <= 0) {
            getSettingsSharedPreferences().manifest_code(0);
        }
        if (getSettingsSharedPreferences().updates_code() <= 0) {
            getSettingsSharedPreferences().updates_code(0);
        }
        if (getSettingsSharedPreferences().additions_code() <= 0) {
            getSettingsSharedPreferences().additions_code(0);
        }

        // 检查权限
        if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }

    private void initView() {
        initToolBar(toolbar, false, "Small插件化示例");

//        if (getSettingsSharedPreferences().small_update() == 1) {
//            String tip1 = tvBeijingWeather.getText().toString();
//            String tip2 = "（更新的插件）";
//            tvBeijingWeather.setText(SpannableStringUtil.getSpannableString(tip1 + tip2, tip2, Color.parseColor("#ff5555")));
//        }
//
//        if (getSettingsSharedPreferences().small_add() == 1) {
//            String tip1 = tvShanghaiWeather.getText().toString();
//            String tip2 = "（增加的插件）";
//            tvShanghaiWeather.setText(SpannableStringUtil.getSpannableString(tip1 + tip2, tip2, Color.parseColor("#ff5555")));
//        }
    }

    private void initListener() {
        tvBeijingPhone.setOnClickListener(this);
        tvWeihaiPhone.setOnClickListener(this);
        tvBeijingWeather.setOnClickListener(this);
        tvShanghaiWeather.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_beijing_phone:
                Small.openUri("phone/beijing?num=18600604600&toast=Fucking Amazing!", this);
                break;
            case R.id.tv_weihai_phone:
                Small.openUri("phone/weihai", this);
                break;
            case R.id.tv_beijing_weather:
                Small.openUri("weather_beijing/beijing", this);
                break;
            case R.id.tv_shanghai_weather:
                Small.openUri("weather_shanghai/shanghai", this);
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
        Intent intent = new Intent(this, SmallService.class);
        switch (item.getItemId()) {
            case R.id.action_update_plugin:
                tvTip.setText("【更新插件】");
                intent.putExtra("small", SmallService.SMALL_CHECK_UPDATE);
                startService(intent);
                return true;
            case R.id.action_add_plugin:
                tvTip.setText("【增加插件】");
                intent.putExtra("small", SmallService.SMALL_CHECK_ADD);
                startService(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, SmallService.class);
        intent.putExtra("small", SmallService.SMALL_UPDATE_BUNDLES);
        startService(intent);
    }

    @Override
    protected void onDestroy() {
        mLocalBroadcastManager.unregisterReceiver(mBroadcastReceiver);
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode != 1 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            ToastTip.show("请手动打开存储读写权限！");
        }
    }

    public class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int status = intent.getIntExtra("status", -1000);
            String tip = intent.getStringExtra("tip");
            tvTip.setText(tvTip.getText() + "\n" + tip);

            switch (status) {
                case SmallService.STATUS_START:
                    mDialog = new ProgressDialog(MainActivity.this);
                    mDialog.setCanceledOnTouchOutside(true);
                    mDialog.setMessage(tip);
                    mDialog.show();
                    break;
                case SmallService.STATUS_DOWNLOADING:
                    mDialog.setMessage(tip);
                    break;
                case SmallService.STATUS_DOWNLOAD_SUCCESS:
                case SmallService.STATUS_FAILED:
                    mDialog.dismiss();
                    ToastTip.show(tip);
                    break;
                case SmallService.STATUS_TOAST:
                    ToastTip.show(tip);
                    break;
            }
        }
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
