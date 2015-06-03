package com.syw.weiyu.util;

/**
 * author: songyouwei
 * date: 2015-06-03
 * desc:
 */
public class DistanceUtil {
    /**
     * google maps�Ľű������
     */
    private static final double EARTH_RADIUS = 6378.137;

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * ��������侭γ�����꣨doubleֵ���������������룬��λΪ��
     */
    public static double getDistanceMeter(double lat1, double lng1, double lat2, double lng2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) +
                Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s;
    }
    /**
     * ��������侭γ�����꣨doubleֵ���������������룬��λΪǧ��
     */
    public static double getDistanceKiloMeter(double lat1, double lng1, double lat2, double lng2) {
        return getDistanceMeter(lat1,lng1,lat2,lng2) / 1000;
    }
}
