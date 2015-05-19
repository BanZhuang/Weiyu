package com.syw.weiyu.api.impl;

import com.syw.weiyu.AppException;
import com.syw.weiyu.api.IUserApi;
import com.syw.weiyu.bean.Account;
import com.syw.weiyu.bean.User;
import com.syw.weiyu.dao.im.RongCloud;
import com.syw.weiyu.dao.im.TokenDao;
import com.syw.weiyu.dao.location.CreateUserPoiDao;
import com.syw.weiyu.dao.location.LocationDao;
import com.syw.weiyu.dao.user.AccountDao;

/**
 * author: youwei
 * date: 2015-05-12
 * desc: 用户接口实现
 */
public class UserApi implements IUserApi {

    /**
     * 注册接口
     * 1.使用账户信息在LBS云创建POI节点
     * 2.获取token
     * 3.设置账户
     * @param user
     */
    @Override
    public void register(User user) throws AppException {
        new CreateUserPoiDao().create(user, new LocationDao().get());
        String token = new TokenDao().get(user.getId(), user.getName(), null);
        Account account = new Account();
        account.setId(user.getId());
        account.setName(user.getName());
        account.setGender(user.getGender());
        account.setLocation(new LocationDao().get());
        account.setToken(token);
        new AccountDao().set(account);
    }

    /**
     * 登录接口
     * 1.连接IM服务器
     * @return
     */
    @Override
    public void login() throws AppException {
        RongCloud.connect(new AccountDao().get().getToken());
    }

    /**
     * 登出
     * 1.更新最后在线时间
     * 2.登出IM服务器
     * @param id
     */
    @Override
    public void logout(String id) {

    }

    /**
     * 是否在线
     * @param id
     * @return
     */
    @Override
    public boolean isOnline(String id) {
        return false;
    }

    /**
     * 获取最后在线时间
     * @param id
     * @return
     */
    @Override
    public int getLastOnlineTimestamp(String id) {
        return 0;
    }

    /**
     * 获取用户资料
     * @param id
     * @return
     */
    @Override
    public User getUserProfile(String id) {
        return null;
    }

    /**
     * 更新用户资料
     */
    @Override
    public void updateUserProfile(User user) {

    }
}
