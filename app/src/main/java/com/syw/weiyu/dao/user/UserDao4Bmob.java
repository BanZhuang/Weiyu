package com.syw.weiyu.dao.user;

import android.support.annotation.NonNull;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobGeoPoint;
import cn.bmob.v3.listener.CountListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import com.syw.weiyu.AppContext;
import com.syw.weiyu.AppException;
import com.syw.weiyu.api.Listener;
import com.syw.weiyu.api.Null;
import com.syw.weiyu.bean.MLocation;
import com.syw.weiyu.bean.User;
import com.syw.weiyu.bean.UserList;
import com.syw.weiyu.util.Async2Sync;
import net.tsz.afinal.FinalDb;

import java.util.List;

/**
 * author: youwei
 * date: 2015-06-03
 * desc: 用户存取类，使用Bmob存储
 */
public class UserDao4Bmob implements UserDao {
    @Override
    public void create(String id, String name, String gender, MLocation location, final Listener<Null> listener) {
        User user = new User(id,name,gender);
        BmobGeoPoint gpsAdd = new BmobGeoPoint(Double.parseDouble(location.getLongitude()), Double.parseDouble(location.getLatitude()));
        user.setGpsAdd(gpsAdd);
        user.setAddressStr(location.getAddress());
        user.save(AppContext.getCtx(), new SaveListener() {
            @Override
            public void onSuccess() {
                listener.onSuccess(null);
            }

            @Override
            public void onFailure(int i, String s) {
                listener.onFailure(s);
            }
        });
    }

    @Override
    public void update(@NonNull String id, String name, String gender, MLocation location, final Listener<Null> listener) {
        try {
            User user = getUser(id);
            user.setName(name);
            user.setGender(gender);
            BmobGeoPoint gpsAdd = new BmobGeoPoint(Double.parseDouble(location.getLongitude()),Double.parseDouble(location.getLatitude()));
            user.setGpsAdd(gpsAdd);
            user.setAddressStr(location.getAddress());
            user.update(AppContext.getCtx(), new UpdateListener() {
                @Override
                public void onSuccess() {
                    listener.onSuccess(null);
                }

                @Override
                public void onFailure(int i, String s) {
                    listener.onFailure("更新用户资料出错:"+s);
                }
            });
        } catch (AppException e) {
            listener.onFailure("更新用户资料出错:"+e.getMessage());
        }
    }

    @Override
    public void getNearbyUsers(final MLocation location,final int pageSize, final int pageIndex, final Listener<UserList> listener) {
        BmobQuery<User> countQuery = new BmobQuery<>();
        countQuery.count(AppContext.getCtx(), User.class, new CountListener() {
            @Override
            public void onSuccess(final int i) {
                BmobGeoPoint gpsAdd = new BmobGeoPoint(Double.parseDouble(location.getLongitude()), Double.parseDouble(location.getLatitude()));
                BmobQuery<User> bmobQuery = new BmobQuery<>();
                bmobQuery.addWhereNear("gpsAdd", gpsAdd);
                bmobQuery.setLimit(pageSize);//获取最接近用户地点的n条数据
                bmobQuery.setSkip((pageIndex-1)*pageSize);
                bmobQuery.findObjects(AppContext.getCtx(), new FindListener<User>() {
                    @Override
                    public void onSuccess(List<User> list) {
                        listener.onSuccess(new UserList(i,list));

                        //save to db
                        FinalDb finalDb = FinalDb.create(AppContext.getCtx());
                        for (User user : list) {
                            if (finalDb.findById(user.getId(),User.class) == null) finalDb.save(user);
                        }
                    }

                    @Override
                    public void onError(int i, String s) {
                        listener.onFailure(s);
                    }
                });
            }

            @Override
            public void onFailure(int i, String s) {
                listener.onFailure(s);
            }
        });
    }

    @Override
    public User getUser(final String id) throws AppException {
        FinalDb finalDb = FinalDb.create(AppContext.getCtx());
        User user = finalDb.findById(id, User.class);
        if (user == null) user = new Async2Sync<User>(){
            @Override
            public void doSthAsync(final Listener<User> listener) {
                BmobQuery<User> bmobQuery = new BmobQuery<>();
                bmobQuery.addWhereEqualTo("id",id);
                bmobQuery.setLimit(1);
                bmobQuery.findObjects(AppContext.getCtx(), new FindListener<User>() {
                    @Override
                    public void onSuccess(List<User> list) {
                        User u = list.get(0);
                        if (u == null) {
                            listener.onFailure("无该用户信息");
                        } else {
                            listener.onSuccess(u);
                            //save to db
                            FinalDb finalDb = FinalDb.create(AppContext.getCtx());
                            if (finalDb.findById(u.getId(), User.class) == null) finalDb.save(u);
                        }
                    }

                    @Override
                    public void onError(int i, String s) {
                        listener.onFailure(s);
                    }
                });
            }
        }.get();
        if (user==null) throw new AppException("获取用户信息出错");
        return user;
    }
}
