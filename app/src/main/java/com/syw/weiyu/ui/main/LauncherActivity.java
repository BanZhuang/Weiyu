package com.syw.weiyu.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import com.orhanobut.logger.Logger;
import com.syw.weiyu.AppContext;
import com.syw.weiyu.R;
import com.syw.weiyu.api.AdApi;
import com.syw.weiyu.api.Listener;
import com.syw.weiyu.util.IOC;


public class LauncherActivity extends Activity {

    AdApi adApi = IOC.getAdApi();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wy_activity_launcher);

        if (AppContext.getInstance() == null) {
            //未作初始化时，加载开屏广告，并初始化AppContext
            showSplashAdThenGotoMainPage();
            AppContext.init(this);
        } else {
            //已做过初始化，并且有用户信息，直接进入主页面
            if (AppContext.getInstance().getUser() != null) {
                gotoMainPage();
            } else {
                //无用户信息，进登入页
                gotoLoginPage();
            }
        }
    }

    private void gotoLoginPage() {
        //进入注册页
        Intent intent = new Intent(LauncherActivity.this, LoginActivity.class);
        startActivity(intent);
        //销毁
        finish();
    }

    /**
     * 进入主页
     *   有用户信息→MainTabs主页面
     *   无用户信息→Login登入页
     */
    private void gotoMainPage() {
        //进入主页面
        Intent intent = new Intent(LauncherActivity.this, MainTabsActivity.class);
        startActivity(intent);
        //销毁
        finish();
    }

    private void showSplashAdThenGotoMainPage() {
        //[1s]后加载开屏广告页
        adApi.showSplashAd(this, new Listener() {
            @Override
            public void onCallback(Callback callback,String msg) {
                switch (callback) {
                    case onAdError:
                        Logger.d(msg);
                        gotoMainPage();
                        break;
                    case onAdClick:
                        Logger.d(msg);
                        break;
                    case onAdClose:
                        gotoMainPage();
                        break;
                }
            }
        });
    }


    //禁用返回键
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                || keyCode == KeyEvent.KEYCODE_HOME) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
