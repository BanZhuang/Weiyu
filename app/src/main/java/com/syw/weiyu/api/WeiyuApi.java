package com.syw.weiyu.api;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import com.syw.weiyu.AppException;
import com.syw.weiyu.R;
import com.syw.weiyu.adp.WeiyuBannerCustomEventPlatformAdapter;
import com.syw.weiyu.adp.WeiyuCustomEventPlatformEnum;
import com.syw.weiyu.av.WeiyuLayout;
import com.syw.weiyu.bean.Account;
import com.syw.weiyu.bean.Shuoshuo;
import com.syw.weiyu.bean.ShuoshuoList;
import com.syw.weiyu.bean.User;
import com.syw.weiyu.controller.listener.WeiyuListener;
import com.syw.weiyu.dao.im.RongCloud;
import com.syw.weiyu.dao.im.TokenDao;
import com.syw.weiyu.dao.location.LocationDao;
import com.syw.weiyu.dao.location.UserPoiDao;
import com.syw.weiyu.dao.shuoshuo.ShuoshuoDao;
import com.syw.weiyu.dao.user.AccountDao;
import com.syw.weiyu.splash.WeiyuSplash;
import com.syw.weiyu.splash.WeiyuSplashListener;
import com.syw.weiyu.util.WeiyuSize;
import com.syw.weiyu.util.WeiyuSplashMode;

/**
 * author: songyouwei
 * date: 2015-05-26
 * desc: Api
 */
public class WeiyuApi {

    private WeiyuApi(){}
    private static WeiyuApi api;
    public static WeiyuApi get() {
        if (api == null) api = new WeiyuApi();
        return api;
    }

    /**
     * =========================================
     * ----------------�û�����------------------
     * =========================================
     */

    /**
     * ע��ӿ�
     * 1.ʹ���˻���Ϣ��LBS�ƴ���POI�ڵ�
     * 2.��ȡtoken
     * 3.�����˻�
     * @param id
     * @param name
     * @param gender
     * @throws AppException
     */
    public void register(String id,String name,String gender) throws AppException {
        new UserPoiDao().create(new User(id, name, gender), new LocationDao().get());
        String token = new TokenDao().get(id, name, null);
        Account account = new Account(id,name,gender,token,new LocationDao().get());
        new AccountDao().set(account);
    }

    /**
     * ��¼�ӿ�
     * 1.����IM������
     * @return
     */
    public void login() throws AppException {
        RongCloud.connect(new AccountDao().get().getToken());
    }

    /**
     * �ǳ�
     * 1.�����������ʱ��
     * 2.�ǳ�IM������
     * @param id
     */
    public void logout(String id) {

    }

    /**
     * �Ƿ�����
     * @param id
     * @return
     */
    public boolean isOnline(String id) {
        return false;
    }

    /**
     * ��ȡ�������ʱ��
     * @param id
     * @return
     */
    public int getLastOnlineTimestamp(String id) {
        return 0;
    }

    /**
     * ��ȡ�û�����
     * @param id
     * @return
     */
    public User getUserProfile(String id) {
        return null;
    }

    /**
     * �����û�����
     */
    public void updateUserProfile(User user) {

    }

    /**
     * =========================================
     * ----------------λ�ò���------------------
     * =========================================
     */

    /**
     * ��λ������λ������
     */
    public void locate() {
        new LocationDao().set();
    }

    public void updateUserLocation(Listener listener) {

    }

    public void getCachedLocation(Listener listener) {

    }


    /**
     * =========================================
     * ----------------˵˵����------------------
     * =========================================
     */

    /**
     * ˢ�£���ȡ��һҳ��˵˵��
     * @return
     * @throws AppException
     */
    public ShuoshuoList refreshNearbyShuoshuo() throws AppException {
        return getNearbyShuoshuo(0);
    }

    /**
     * ��ȡ������˵˵
     * ���ȴ��ڴ滺���л�ȡ
     * @param pageIndex
     * @return
     * @throws AppException
     */
    public ShuoshuoList getNearbyShuoshuo(int pageIndex) throws AppException {
        return null;
    }

    /**
     * ��ȡ˵˵����
     * @param shuoshuo
     * @return
     * @throws AppException
     */
    public Shuoshuo getShuoshuoDetail(Shuoshuo shuoshuo) throws AppException {
        shuoshuo.setCommentList(new ShuoshuoDao().getComments(shuoshuo.getId()));
        return shuoshuo;
    }

    public void publishShuoshuo(String content) throws AppException {
        new ShuoshuoDao().add(content);
    }

    /**
     * =========================================
     * ----------------��沿��------------------
     * =========================================
     */

    //adsmogo
    WeiyuLayout weiyuLayoutCode;

    /**
     * ��Activity��onDestroy�����µ���
     */
    public void onBannerDestory() {
        WeiyuLayout.clear();
        weiyuLayoutCode.clearThread();
    }

    Listener listener;
    public void init(Listener listener) {
        this.listener = listener;
    }

    public View getBannerAdView(Activity activity,final Listener listener) {
        /**
         * ��ʼ��adsMogoView
         * ��������һ��activity,�ڶ���mogoID����ֵΪâ����̨�����������â��ID���ǵ�һƽ̨ID��,���������ù��չʾλ��,���ĸ�������ߴ�,
         * ������Ƿ��ֶ�ˢ��true�����ֶ�ˢ�£�â����̨�ֻ�ʱ�����Ϊ���òŻ���Ч��	��false:�Զ��ֻ�
         */
        weiyuLayoutCode = new WeiyuLayout(activity, activity.getString(R.string.adsmogo_appid), WeiyuSize.WeiyuAutomaticScreen);

        weiyuLayoutCode.setWeiyuListener(new WeiyuListener() {
            @Override
            public void onInitFinish() {

            }

            @Override
            public void onRequestAd(String s) {

            }

            @Override
            public void onRealClickAd() {

            }

            @Override
            public void onReceiveAd(ViewGroup viewGroup, String s) {

            }

            @Override
            public void onFailedReceiveAd() {
                listener.onCallback(Listener.CallbackType.onAdError,"onFailedReceive");
            }

            @Override
            public void onClickAd(String s) {
                listener.onCallback(Listener.CallbackType.onAdClick,s);
            }

            @Override
            public boolean onCloseAd() {
                listener.onCallback(Listener.CallbackType.onAdClose,"");
                return false;
            }

            @Override
            public void onCloseMogoDialog() {

            }

            @Override
            public Class<? extends WeiyuBannerCustomEventPlatformAdapter> getCustomEvemtPlatformAdapterClass(WeiyuCustomEventPlatformEnum weiyuCustomEventPlatformEnum) {
                return null;
            }
        });
        weiyuLayoutCode.setCloseButtonVisibility(View.VISIBLE);
        weiyuLayoutCode.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT));
        weiyuLayoutCode.downloadIsShowDialog = true;

        return weiyuLayoutCode;
    }

    public void showSplashAd(Activity activity,final Listener listener) {
        WeiyuSplash weiyuSplash = new WeiyuSplash(activity,activity.getString(R.string.adsmogo_appid), WeiyuSplashMode.FULLSCREEN);
        //���ÿ���������
        weiyuSplash.setWeiyuSplashListener(new WeiyuSplashListener() {
            @Override
            public void onSplashClickAd(String s) {
                listener.onCallback(Listener.CallbackType.onAdClick,s);
            }

            @Override
            public void onSplashRealClickAd(String s) {

            }

            @Override
            public void onSplashError(String s) {
                listener.onCallback(Listener.CallbackType.onAdError,s);
            }

            @Override
            public void onSplashSucceed() {

            }

            @Override
            public void onSplashClose() {
                listener.onCallback(Listener.CallbackType.onAdClose,"");
            }
        });
        weiyuSplash.setCloseButtonVisibility(View.VISIBLE);
    }
}
