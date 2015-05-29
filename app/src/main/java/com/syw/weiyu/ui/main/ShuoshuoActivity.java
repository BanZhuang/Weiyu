package com.syw.weiyu.ui.main;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.paging.listview.PagingListView;
import com.syw.weiyu.AppException;
import com.syw.weiyu.api.Listener;
import com.syw.weiyu.api.WeiyuApi;
import com.syw.weiyu.bean.Shuoshuo;
import com.syw.weiyu.R;
import com.syw.weiyu.bean.ShuoshuoList;
import com.syw.weiyu.ui.adapter.ShuoshuoListAdapter;

import com.syw.weiyu.util.Toaster;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

import java.util.List;

/**
 * author: youwei
 * date: 2015-05-11
 * desc: 说说（首页）
 */
public class ShuoshuoActivity extends FragmentActivity {

    int pageIndex = 0;
    int totalPage = 0;

    PagingListView listView;
    ShuoshuoListAdapter adapter;

    PtrClassicFrameLayout mPtrFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wy_activity_shuoshuo);
        initViews();
        initUltraPullToRefresh();
        initListView();
    }


    @Override
    protected void onResume() {
        super.onResume();

        new Handler(){
            @Override
            public void handleMessage(Message msg) {
                try {
                    ShuoshuoList list = WeiyuApi.get().getCachedNearbyShuoshuo();
                    adapter.set(list.getShuoshuos());
                } catch (AppException e) {
                    mPtrFrame.autoRefresh();
                }
            }
        }.sendEmptyMessageDelayed(1, 500);

        //add banner ad
        if (listView.getHeaderViewsCount() == 0) {
            listView.addHeaderView(WeiyuApi.get().getBannerAdView(this, null));
        }
    }

    @Override
    protected void onDestroy() {
        WeiyuApi.get().onBannerDestory();
        super.onDestroy();
    }

    private void initViews() {
        ImageView btnAdd = (ImageView) findViewById(R.id.header_iv_right);
        btnAdd.setImageResource(R.drawable.wy_icon_add);
        btnAdd.setVisibility(View.VISIBLE);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = getAddShuoshuoAlertDialog();
                dialog.show();
            }
        });

        TextView tvTitle = (TextView) findViewById(R.id.header_tv_title);
        tvTitle.setText("首页");
    }


    /**
     * 初始化下拉刷新框架
     */
    private void initUltraPullToRefresh() {
        mPtrFrame = (PtrClassicFrameLayout) findViewById(R.id.rotate_header_list_view_frame);
        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                pageIndex = 0;
                WeiyuApi.get().getNearbyShuoshuo(0, new Listener<ShuoshuoList>() {
                    @Override
                    public void onCallback(@NonNull CallbackType callbackType, @Nullable ShuoshuoList data, @Nullable String msg) {
                        //结束下拉刷新
                        mPtrFrame.refreshComplete();
                        if (callbackType == CallbackType.onSuccess) {
                            if (data != null) {
                                adapter.set(data.getShuoshuos());
                                totalPage = data.getTotal();
                                //set has more page
                                listView.setHasMoreItems(pageIndex + 1 < totalPage);
                            }
                        } else Toaster.e(ShuoshuoActivity.this, msg);
                    }
                });
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });
    }

    /**
     * 初始化列表视图
     */
    private void initListView() {
        listView = (PagingListView) findViewById(R.id.lv_shuoshuo);
        adapter = new ShuoshuoListAdapter(this);
        listView.setAdapter(adapter);
        listView.setHasMoreItems(false);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,final int position, long id) {
                //开启聊天
//                RongIM.getInstance().startPrivateChat(
//                        ShuoshuoActivity.this,
//                        adapter.getData().get(position - 1).get("userId"),
//                        adapter.getData().get(position - 1).get("shuoshuo_tv_name"));

            }
        });

        listView.setPagingableListener(new PagingListView.Pagingable() {
            @Override
            public void onLoadMoreItems() {
                if (pageIndex + 1 < totalPage) {
                    WeiyuApi.get().getNearbyShuoshuo(++pageIndex, new Listener<ShuoshuoList>() {
                        @Override
                        public void onCallback(@NonNull CallbackType callbackType, @Nullable ShuoshuoList data, @Nullable String msg) {
                            if (callbackType == CallbackType.onSuccess) {
                                if (data != null) {
                                    adapter.append(data.getShuoshuos());
                                    totalPage = data.getTotal();
                                    //set has more page
                                    listView.onFinishLoading(pageIndex + 1 < totalPage, null);
                                }
                            } else {
                                Toaster.e(ShuoshuoActivity.this, msg);
                                --pageIndex;
                            }
                            //结束下拉刷新
                            mPtrFrame.refreshComplete();
                        }
                    });
                } else {
                    listView.onFinishLoading(false, null);
                }
            }
        });
    }

    private AlertDialog getAddShuoshuoAlertDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(ShuoshuoActivity.this,AlertDialog.THEME_HOLO_LIGHT);
        // Get the layout inflater
        final LayoutInflater inflater = ShuoshuoActivity.this.getLayoutInflater();
        final View view = inflater.inflate(R.layout.wy_dialog_addshuoshuo, null);
        final EditText contentET = (EditText)view.findViewById(R.id.et_shuoshuo_content);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view)
                // Add action buttons
                .setPositiveButton("发送", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
//                        LBSCloud.getInstance().publishShuoshuo(contentET.getText().toString(), new AjaxCallBack<String>() {
//                            @Override
//                            public void onSuccess(String s) {
//                                if (JSON.parseObject(s).getString("status").equals("0")) {
//                                    Toaster.i(ShuoshuoActivity.this,"发送成功");
//                                } else {
//                                    Toaster.e(ShuoshuoActivity.this,"发送失败");
//                                }
//
//                            }
//
//                            @Override
//                            public void onFailure(Throwable t, int errorNo, String strMsg) {
//                                Toaster.e(ShuoshuoActivity.this, "网络错误");
//                            }
//                        });
                        WeiyuApi.get().publishShuoshuo(contentET.getText().toString(), new Listener<String>() {
                            @Override
                            public void onCallback(@NonNull CallbackType callbackType, @Nullable String data, @Nullable String msg) {
                                if (callbackType == CallbackType.onSuccess) Toaster.i(ShuoshuoActivity.this, "发送成功");
                                else Toaster.e(ShuoshuoActivity.this, msg);
                            }
                        });

                        new Handler() {
                            @Override
                            public void handleMessage(Message msg) {
                                mPtrFrame.autoRefresh();
                            }
                        }.sendEmptyMessageDelayed(1, 1000);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        builder.create().dismiss();
                    }
                });
        return builder.create();
    }


    /**
     * 两次返回键退出，间隔2秒
     */
    private long currentTime=0;
    private long oldTime=0;
    @Override
    public void onBackPressed() {
        currentTime = System.currentTimeMillis();
        if ((currentTime - oldTime) > 2000 || oldTime == 0) {
            Toast.makeText(this, "再按一次退出", 2000).show();
            oldTime = currentTime;
        } else {
            finish();
        }
    }
}