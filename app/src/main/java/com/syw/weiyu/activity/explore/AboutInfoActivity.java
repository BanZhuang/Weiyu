package com.syw.weiyu.activity.explore;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;

import com.syw.weiyu.R;

public class AboutInfoActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wy_activity_about_info);

        findViewById(R.id.header_iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

}
