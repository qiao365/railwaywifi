package com.crhsi.ylkjcjq.activitys;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

import com.crhsi.ylkjcjq.R;
import com.crhsi.ylkjcjq.adapters.RecyclerViewDetialAdapter;
import com.crhsi.ylkjcjq.models.RecordItem;
import com.crhsi.ylkjcjq.models.CoinObject;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;

public class CoinDetialActivity extends AutoLayoutActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {


    private SwipeRefreshLayout mSwipeRefreshWidget;
    List<RecordItem> datas = new ArrayList<>();
    RecyclerViewDetialAdapter mRecyclerViewAdapter = new RecyclerViewDetialAdapter(datas);
    private CoinObject mCoinObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_detial);

        datas.add(new RecordItem());

        mCoinObject = (CoinObject) getIntent().getExtras().getSerializable("CoinObject");
        //recyclerView填充数据(忽略不计)
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mRecyclerViewAdapter);

        mSwipeRefreshWidget = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_widget);

        mSwipeRefreshWidget.setColorSchemeResources(R.color.blue, R.color.community_bg,
                R.color.umeng_fb_color_btn_normal, R.color.umeng_fb_color_btn_pressed);
        mSwipeRefreshWidget.setOnRefreshListener(this);

        // 这句话是为了，第一次进入页面的时候显示加载进度条
        mSwipeRefreshWidget.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));

        mSwipeRefreshWidget.post(new Runnable() {
            @Override
            public void run() {
//                mSwipeRefreshWidget.setRefreshing(true);
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                finish();
                break;
            case R.id.btn_shoukuan:
                Intent min = new Intent(view.getContext(), ReceivablesCodeActivity.class);
                if (mCoinObject.getItemAddress() != null) {
                    min.putExtra("code", mCoinObject.getItemAddress());
                } else {
                    min.putExtra("code", mCoinObject.getProjectAddress());
                }
                startActivity(min);
                break;
            case R.id.btn_zhuanzhang:
                startActivity(new Intent(this, TransferActivity.class));
                break;
            default:

                break;
        }
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshWidget.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshWidget.setRefreshing(false);
            }
        }, 1000);
    }
}
