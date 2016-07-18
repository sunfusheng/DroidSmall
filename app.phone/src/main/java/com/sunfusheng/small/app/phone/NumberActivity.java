package com.sunfusheng.small.app.phone;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number);
        ButterKnife.bind(this);

        setTitle("NumberActivity");

        Uri uri = Small.getUri(this);
        if (uri != null) {
            String num = uri.getQueryParameter("num");
            if (!TextUtils.isEmpty(num)) {
                etNumber.setText(num);
            }
            String toast = uri.getQueryParameter("toast");
            if (!TextUtils.isEmpty(toast)) {
                Toast.makeText(mContext, "传递参数："+toast, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("result", "Great!");
        setResult(1000, intent);
        super.onBackPressed();
    }

}
