package com.syw.weiyu.third;

import com.syw.weiyu.AppConstants;
import com.tencent.xinge.XingeApp;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * author: youwei
 * date: 2015-06-04
 * desc: �Ÿ�����
 */
public class XGPush {
    /**
     * ��ָ���û�ID������ͨ��Ϣ
     * {"ret_code":0}  //�ɹ�
     * {"ret_code":-1, "err_msg":"error description"}  //ʧ��
     ע��ret_codeΪ0��ʾ�ɹ�������Ϊʧ�ܣ�������鿴��¼��
     * @param toUserId
     * @param title
     * @param content
     * @return
     */
    public static boolean pushNotification(String toUserId,String title,String content) {
        JSONObject result = XingeApp.pushAccountAndroid(AppConstants.xgpush_access_id, AppConstants.xgpush_secret_key, title, content, toUserId);
        try {
            return result.getInt("ret_code")==0;
        } catch (JSONException e) {
            return false;
        }
    }

//    public static boolean push
}
