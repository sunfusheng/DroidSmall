package com.sunfusheng.small.app.phone.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.sunfusheng.small.app.phone.R;
import com.sunfusheng.small.app.phone.control.SingleControl;
import com.sunfusheng.small.app.phone.model.PhoneEntity;
import com.sunfusheng.small.lib.framework.base.BaseActivity;
import com.sunfusheng.small.lib.framework.util.ToastTip;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PhoneActivity extends BaseActivity<SingleControl> {

    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.tv_phone)
    TextView tvPhone;
    @Bind(R.id.tv_result)
    TextView tvResult;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        ButterKnife.bind(this);

        initView();
        initListener();
    }

    private void initView() {
        initToolBar(toolbar, true, "Shandong`s Num");
    }

    private void initListener() {
        tvPhone.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_phone:
                String tel = etPhone.getText().toString().trim();
                if (TextUtils.isEmpty(tel) || tel.length() != 11) {
                    ToastTip.show("请输入正确的号码");
                    return;
                }
                mControl.getPhoneLocation(tel);
                break;
        }
    }

    public void getPhoneLocationCallBack() {
        PhoneEntity phoneEntity = mModel.get("PhoneEntity");
        if (phoneEntity == null || phoneEntity.getRetData() == null) return;
        PhoneEntity.RetDataEntity entity = phoneEntity.getRetData();
        tvResult.setText("电话号码："+entity.getTelString()+"\n"+
                "供应商："+entity.getCarrier()+"\n"+
                "归属地："+entity.getProvince());
    }
}
