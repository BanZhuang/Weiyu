package com.syw.weiyu;

/**
 * author: youwei
 * date: 2015-05-19
 * desc: �������ã�����һЩ���������õĲ���
 */
public class AppConstants {
//  Rong Cloud
    public static final String rongcloud_app_key = "uwd1c0sxdj831";
    public static final String rongcloud_app_secret = "s3RAbMx2TkaUl";
    public static final String rongcloud_app_key_debug = "pvxdm17jx5n8r";
    public static final String rongcloud_app_secret_debug = "zFfxVu2WUusvL";
//  urls
    public static final String url_user_gettoken = "https://api.cn.rong.io/user/get.json";
    public static final String url_user_refresh = "https://api.cn.rong.io/user/refresh.json";
//  �Զ���Ŀͻ��˳���
    public static final String RC_ACTION_RECEIVE_MESSAGE = "rc_action_receive_message";
    public static final String RC_UNREAD_COUNT = "rc_unread_count";

//  Baidu LocSDK
    public static final String locsdk_ak = "mUv2tjv4ZdI06Enf9E6GEsZ6";

//  Baidu LBSyun
    public static final String lbsyun_ak = "pzRrx7tXVcoAlxQRGAiMBfVq";
    public static final String geotable_id_user = "93391";
    public static final String geotable_id_shuoshuo = "99489";
    public static final String coord_type = "3";
//  Ĭ�ϵġ������������İ뾶����ʼ������5000����������Ը��������й��������㡿[300�����Լ���ߺ����Ϻ��ľ���]
    public static final String default_radius = "5000000";
//   Ĭ�ϵ�ַ��Ϣ����λʧ��ʱʹ�� 
    public static final String address_default = "�����ж�������������";
    public static final String city_default = "����";
    public static final String province_default = "����";
    public static final String district_default = "������";
    public static final String longitude_default = "116.4038740000";
    public static final String latitude_default = "39.9148890000";
//  Ĭ��ҳ��С
    public static final String page_size_default = "20";

//  AdsMoGo
    public static final String adsmogo_appid = "5dc8eb0ca17f4663b43dcd7f811fba95";


//  LBSyun urls
//   table options 
    public static final String url_create_table = "http://api.map.baidu.com/geodata/v3/geotable/create";
    public static final String url_list_table = "http://api.map.baidu.com/geodata/v3/geotable/list";
    public static final String url_detail_table = "http://api.map.baidu.com/geodata/v3/geotable/detail";
    public static final String url_update_table = "http://api.map.baidu.com/geodata/v3/geotable/update";
    public static final String url_delete_table = "http://api.map.baidu.com/geodata/v3/geotable/delete";

//   column options 
    public static final String url_create_column = "http://api.map.baidu.com/geodata/v3/column/create";
    public static final String url_list_column = "http://api.map.baidu.com/geodata/v3/column/list";
    public static final String url_detail_column = "http://api.map.baidu.com/geodata/v3/column/detail";
    public static final String url_update_column = "http://api.map.baidu.com/geodata/v3/column/update";
    public static final String url_delete_column = "http://api.map.baidu.com/geodata/v3/column/delete";

//   poi options 
    public static final String url_create_poi = "http://api.map.baidu.com/geodata/v3/poi/create";
    public static final String url_list_poi = "http://api.map.baidu.com/geodata/v3/poi/list";
    public static final String url_detail_poi = "http://api.map.baidu.com/geodata/v3/poi/detail";
    public static final String url_update_poi = "http://api.map.baidu.com/geodata/v3/poi/update";
    public static final String url_delete_poi = "http://api.map.baidu.com/geodata/v3/poi/delete";
    public static final String url_upload_poi = "http://api.map.baidu.com/geodata/v3/poi/upload";

//   job options 
    public static final String url_listimportdata_job = "http://api.map.baidu.com/geodata/v3/job/listimportdata";
    public static final String url_list_job = "http://api.map.baidu.com/geodata/v3/job/list";
    public static final String url_detail_job = "http://api.map.baidu.com/geodata/v3/job/detail";

//   search 
    public static final String url_local_search = "http://api.map.baidu.com/geosearch/v3/local";
    public static final String url_nearby_search = "http://api.map.baidu.com/geosearch/v3/nearby";
    public static final String url_bound_search = "http://api.map.baidu.com/geosearch/v3/bound";
    public static final String url_detail_search = "http://api.map.baidu.com/geosearch/v3/detail";
}
