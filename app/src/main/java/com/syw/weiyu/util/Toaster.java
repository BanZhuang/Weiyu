package com.syw.weiyu.util;

import android.app.Activity;
import com.devspark.appmsg.AppMsg;

/**
 * author: songyouwei
 * date: 2015-05-21
 * desc: ��˾er
 */
public class Toaster {
    public static void i(Activity ctx,String msg) {
        // create {@link AppMsg} with specify type
        AppMsg.makeText(ctx,msg,AppMsg.STYLE_INFO).show();
    }
    public static void e(Activity ctx,String msg) {
        // create {@link AppMsg} with specify type
        AppMsg.makeText(ctx,msg,AppMsg.STYLE_ALERT).show();
    }
}
