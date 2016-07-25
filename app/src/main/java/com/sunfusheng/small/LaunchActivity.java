package com.sunfusheng.small;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import net.wequick.small.Small;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

    }

    @Override
    protected void onStart() {
        super.onStart();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Small.setUp(LaunchActivity.this, new Small.OnCompleteListener() {
                    @Override
                    public void onComplete() {
                        Small.openUri("main", LaunchActivity.this);
                        finish();
                    }
                });
            }
        }, 1000);
    }

}
