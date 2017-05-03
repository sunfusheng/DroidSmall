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

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhoneActivity extends BaseActivity<SingleControl> {

    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_result)
    TextView tvResult;
    @BindView(R.id.toolbar)
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
        if (phoneEntity == null) return;
        if (phoneEntity.getError_code() != 0 && !TextUtils.isEmpty(phoneEntity.getReason())) {
            ToastTip.show(phoneEntity.getReason());
            return;
        }
        PhoneEntity.ResultEntity entity = phoneEntity.getResult();
        tvResult.setText("电话号码：" + entity.getPhone() + "\n" +
                "供应商：" + entity.getCompany() + "\n" +
                "归属地：" + entity.getProvince() + " " + entity.getCity());
    }
}
