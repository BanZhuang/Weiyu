package com.syw.weiyu.util;

import android.util.LruCache;
import com.alibaba.fastjson.JSON;
import com.syw.weiyu.AppContext;
/**
 * author: songyouwei
 * date: 2015-05-22
 * desc: Ӧ�����ݻ��棨RAM or localFile��
 */
public class AppCacher {
    public static final String KEY_NEARBYSHUOSHUOS = "key_nearbyshuoshuos";
    public static final String KEY_NEARBYUSERS = "key_nearbyusers";

    static final int cacheSize = (int) (Runtime.getRuntime().maxMemory()/2);//���heap size��һ���
    static LruCache lruCache = new LruCache<String,Object>(cacheSize) {
        @Override
        protected void entryRemoved(boolean evicted, String key, Object oldValue, Object newValue) {
            super.entryRemoved(evicted, key, oldValue, newValue);
            ACache.get(AppContext.getCtx()).put(key, JSON.toJSONString(oldValue));
        }

        @Override
        protected Object create(String key) {
            return JSON.parse(ACache.get(AppContext.getCtx()).getAsString(key));
        }
    };
    public static void put(String k,Object v) {
        lruCache.put(k,v);
    }
    public static Object get(String k) {
        return lruCache.get(k);
    }
}
