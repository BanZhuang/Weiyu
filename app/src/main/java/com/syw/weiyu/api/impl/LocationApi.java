package com.syw.weiyu.api.impl;

import com.baidu.location.BDLocation;
import com.syw.weiyu.api.ILocationApi;
import com.syw.weiyu.api.Listener;
import com.syw.weiyu.bean.MLocation;
import com.syw.weiyu.dao.location.LocationDao;
import com.syw.weiyu.third.lbs.LocSDK;

/**
 * author: youwei
 * date: 2015-05-19
 * desc:
 */
public class LocationApi implements ILocationApi {

    /**
     * ��λ������λ������
     */
    @Override
    public void locate() {
        new LocationDao().set();
    }

    @Override
    public void updateUserLocation(Listener listener) {

    }

    @Override
    public void getCachedLocation(Listener listener) {

    }
}
