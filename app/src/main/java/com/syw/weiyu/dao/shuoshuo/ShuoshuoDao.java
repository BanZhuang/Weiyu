package com.syw.weiyu.dao.shuoshuo;

import com.syw.weiyu.core.Listener;
import com.syw.weiyu.core.Null;
import com.syw.weiyu.bean.Account;
import com.syw.weiyu.bean.MLocation;
import com.syw.weiyu.bean.Shuoshuo;
import com.syw.weiyu.bean.ShuoshuoList;

/**
 * author: youwei
 * date: 2015-06-03
 * desc:
 */
public interface ShuoshuoDao {
    /**
     * 获取附近的说说列表
     * @param location
     * @param pageSize
     * @param pageIndex
     * @param listener
     */
    void getNearbyShuoshuos(MLocation location,int pageSize,int pageIndex,Listener<ShuoshuoList> listener);

    void getUserShuoshuos(String userId, int pageSize, int pageIndex, Listener<ShuoshuoList> listener);

    void getShuoshuo(String id, Listener<Shuoshuo> listener);

    /**
     * 发布说说
     * @param account
     * @param location
     * @param content
     * @param timeStamp
     * @param listener
     */
    void create(Account account,MLocation location,String content,long timeStamp, final Listener<Null> listener);

    /**
     * 评论数++
     * @param shuoshuo
     */
    void addCommentCount(Shuoshuo shuoshuo);

    /**
     * 喜欢数++
     * @param shuoshuo
     */
    void addLikedCount(Shuoshuo shuoshuo);
}
