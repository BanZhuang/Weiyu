package com.syw.weiyu.bean;

import cn.bmob.v3.BmobObject;

/**
 * author: youwei
 * date: 2015-05-20
 * desc: 评论实体类
 */
public class Comment extends BmobObject {
    private String userName;
    private String userId;
    private String userGender;
    private String content;
    private long ssId;
    private long timestamp;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getSsId() {
        return ssId;
    }

    public void setSsId(long ssId) {
        this.ssId = ssId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }
}