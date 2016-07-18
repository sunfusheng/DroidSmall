package com.sunfusheng.small.app.phone;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.sunfusheng.small.lib.framework.base.BaseActivity;
import com.sunfusheng.small.lib.framework.util.ToastTip;

import net.wequick.small.Small;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NumberActivity extends BaseActivity {

    @Bind(R.id.et_number)
    EditText etNumber;
    @Bind(R.id.tv_number)
    TextView tvNumber;
    @Bind(R.id.tv_result)
    TextView tvResult;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number);
        ButterKnife.bind(this);

        initData();
        initView();
    }

    private void initData() {
        Uri uri = Small.getUri(this);
        if (uri != null) {
            String num = uri.getQueryParameter("num");
            if (!TextUtils.isEmpty(num)) {
                etNumber.setText(num);
            }
            String toast = uri.getQueryParameter("toast");
            if (!TextUtils.isEmpty(toast)) {
                ToastTip.show("传递参数：" + toast);
            }
        }
    }

    private void initView() {
        initToolBar(toolbar, true, "NumberActivity");
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("result", "Great!");
        setResult(1000, intent);
        super.onBackPressed();
    }

}
