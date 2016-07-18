package com.sunfusheng.small.app.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.sunfusheng.small.lib.framework.base.BaseActivity;
import com.sunfusheng.small.lib.framework.util.ToastTip;

import net.wequick.small.Small;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

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

        initView();
        initListener();
    }

    private void initView() {
        initToolBar(toolbar, false, "DroidSmall");
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
                Small.openUri("phone/Number?num=18600604601&toast=Fucking amazing!", mContext);
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
            case R.id.action_wether:
                updatePatchBundle("com.sunfusheng.small.app.weather", "libcom_sunfusheng_small_app_weather.so");
                return true;
            case R.id.action_phone:
                updatePatchBundle("com.sunfusheng.small.app.phone", "libcom_sunfusheng_small_app_phone.so");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updatePatchBundle(final String pkgName, final String fileName) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String path = Environment.getExternalStorageDirectory() + "/DroidSmall";
                    File inFile = new File(path, fileName);
                    if (!inFile.exists()) return;
                    net.wequick.small.Bundle bundle = Small.getBundle(pkgName);
                    File outFile = bundle.getPatchFile();

                    InputStream is = new FileInputStream(inFile);
                    OutputStream os = new FileOutputStream(outFile);

                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = is.read(buffer)) != -1) {
                        os.write(buffer, 0, length);
                    }

                    os.flush();
                    os.close();
                    is.close();
                    bundle.upgrade();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
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
