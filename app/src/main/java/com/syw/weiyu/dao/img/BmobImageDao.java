package com.syw.weiyu.dao.img;

import android.content.Context;
import com.bmob.BmobProFile;
import com.bmob.btp.callback.LocalThumbnailListener;
import com.bmob.btp.callback.UploadListener;
import com.orhanobut.logger.Logger;
import com.syw.weiyu.core.AppConstants;
import com.syw.weiyu.core.Listener;

/**
 * author: youwei
 * date: 2015-06-16
 * desc:
 */
public class BmobImageDao {
    private static BmobProFile bmobProFile;
    private static BmobImageDao imageDao;
    private BmobImageDao() {}
    public static BmobImageDao getInstance(Context ctx) {
        bmobProFile = BmobProFile.getInstance(ctx);
        if (imageDao == null) {
            synchronized (BmobImageDao.class) {
                if (imageDao == null) imageDao = new BmobImageDao();
            }
        }
        return imageDao;
    }
    public void save(String filePath, final Listener<String> listener) {
        bmobProFile.upload(filePath, new UploadListener() {

            //�ļ���������׺��������ļ�����Ψһ�ģ���������Ҫ��¼�¸��ļ���������������ػ��߽�������ͼ�Ĵ���
            //url���ļ���������ַ��������ϴ�����ͼƬ���͵��ļ�����url��ַ������ֱ����������鿴�������404���󣩣�
            // ���url��ַ��Ҫ����URLǩ����ſ��Ա����ʵ�����URLǩ���ַֿ����Ͳ��������ַ�ʽ�����ߵ���������ʱЧ�ԡ�����ɲ鿴URLǩ��
            //δ����ǩ����֤��URL��ַ��ʽ��
            // URL=url(�ļ��ϴ��ɹ�֮�󷵻ص�url��ַ)?t=���ͻ������ͣ�&a=(Ӧ����Կ�е�AccessKey)
            @Override
            public void onSuccess(String fileName, String url) {
                String signedURL = bmobProFile.signURL(fileName, url, AppConstants.bmob_access_key, 0, null);
                Logger.i("signedURL:" + signedURL);
                listener.onSuccess(signedURL);
            }

            @Override
            public void onProgress(int ratio) {
            }

            @Override
            public void onError(int statuscode, String errormsg) {
                listener.onFailure("�ϴ�����" + errormsg);
            }
        });
    }

    public void getThumbnail(String filePath, final Listener<String> listener) {
        bmobProFile.getLocalThumbnail(filePath, 1, new LocalThumbnailListener() {

            @Override
            public void onError(int statuscode, String errormsg) {
                // TODO Auto-generated method stub
                Logger.i("MainActivity -localThumbnail-->��������ͼʧ�� :" + statuscode + "," + errormsg);
                listener.onFailure(errormsg);
            }

            @Override
            public void onSuccess(String thumbnailPath) {
                // TODO Auto-generated method stub
                Logger.i("MainActivity -localThumbnail-->���ɺ������ͼ·�� :" + thumbnailPath);
                listener.onSuccess(thumbnailPath);
            }
        });
    }
}
