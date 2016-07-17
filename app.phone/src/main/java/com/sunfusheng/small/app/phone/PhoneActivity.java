package com.sunfusheng.small.app.phone;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PhoneActivity extends AppCompatActivity {

    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.tv_phone)
    TextView tvPhone;
    @Bind(R.id.tv_result)
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        ButterKnife.bind(this);

        setTitle("PhoneActivity");
    }
}
