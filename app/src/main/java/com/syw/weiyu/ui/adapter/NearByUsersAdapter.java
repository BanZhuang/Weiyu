package com.syw.weiyu.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.paging.listview.PagingBaseAdapter;
import com.syw.weiyu.R;
import com.syw.weiyu.bean.User;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songyouwei on 2015/4/27.
 */
public class NearByUsersAdapter extends PagingBaseAdapter {

    public void set(List<User> data) {
        users.clear();
        users.addAll(data);
        notifyDataSetChanged();
    }

    public void append(List<User> data) {
        users.addAll(data);
        notifyDataSetChanged();
    }

    public List<User> getData() {
        return users;
    }

    LayoutInflater mInflater;
    Context ctx;
    List<User> users = new ArrayList<>();
//
    public NearByUsersAdapter(Context ctx) {
        this.ctx = ctx;
        mInflater = LayoutInflater.from(ctx);
    }

    //在外面先定义，ViewHolder静态类
    static class ViewHolder {
        public ImageView portrait;
        public TextView name;
        public TextView address;
    }

//    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //然后重写getView
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.wy_nearbyuser_lv_item, null);
            holder = new ViewHolder();
            holder.portrait = (ImageView) convertView.findViewById(R.id.nearbyuser_iv_portrait);
            holder.name = (TextView) convertView.findViewById(R.id.nearbyuser_tv_name);
            holder.address = (TextView) convertView.findViewById(R.id.nearbyuser_tv_address);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final User user = users.get(position);
        holder.portrait.setImageResource(
                user.getGender().equals("男") ?
                        R.drawable.wy_icon_male :
                        R.drawable.wy_icon_female
        );
        holder.name.setText(user.getName());
        holder.address.setText(user.getLocation()==null?user.getAddressStr():user.getLocation().getAddress());

        //点击用户时开启私聊
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RongIM.getInstance().startConversation(ctx, Conversation.ConversationType.PRIVATE, user.getId(), user.getName());
            }
        });

        return convertView;
    }
}
