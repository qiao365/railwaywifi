package com.railway.wifi.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.railway.wifi.activitys.WebViewActivity;
import com.railway.wifi.http.httputils.HttpUtil;
import com.railway.wifi.utils.GlobleValue;
import com.railway.wifi.utils.PicassoImageLoader;
import com.railway.wifi.views.CompletedView;
import com.railway.wifi.views.ObservableScrollView;
import com.railway.wifi.R;
import com.railway.wifi.http.httputils.AllUrl;
import com.railway.wifi.http.httputils.AsyncTaskManager;
import com.railway.wifi.http.httputils.GsonUtils;
import com.railway.wifi.http.requestparams.BaseRequestParm;
import com.railway.wifi.http.responsebeans.BaseResponseBean;
import com.railway.wifi.http.responsebeans.RequestListener;
import com.github.mikephil.charting.animation.Easing;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;


/**
 * 首页
 *
 * @author CJQ
 */

public class HomeMain1Fragment extends Fragment implements View.OnClickListener, ObservableScrollView.ScrollViewListener, SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshWidget;
    private Context mContext;
    private String devicesNum = "--";
    private TextView tvDevices;
    private LinearLayout topBtn1, topBtn2, topBtn3;
    private CompletedView mCompletedView;
    private int mCurrentProgress = 50;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main1, null);
        mContext = view.getContext();
        initView(view);
        initBanners(view);
        new Thread(new ProgressRunable()).start();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getDivices();
    }

    class ProgressRunable implements Runnable {

        @Override
        public void run() {
            mCompletedView.setProgress(mCurrentProgress);
        }
    }

    private void initView(View view) {
        mCompletedView = (CompletedView) view.findViewById(R.id.mCompletedView);
        tvDevices = (TextView) view.findViewById(R.id.tvDevices);
        topBtn1 = (LinearLayout) view.findViewById(R.id.topBtn1);
        topBtn2 = (LinearLayout) view.findViewById(R.id.topBtn2);
        topBtn3 = (LinearLayout) view.findViewById(R.id.topBtn3);
        topBtn1.setOnClickListener(this);
        topBtn2.setOnClickListener(this);
        topBtn3.setOnClickListener(this);
        mSwipeRefreshWidget = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_widget);
        //设置卷内的颜色
        mSwipeRefreshWidget.setColorSchemeResources(android.R.color.holo_orange_light, android.R.color.holo_red_light, android.R.color.holo_blue_bright,
                android.R.color.holo_green_light);
        mSwipeRefreshWidget.setOnRefreshListener(this);
    }

    private void initBanners(View view) {
        List<String> imageUrls = new ArrayList<String>();
        List<String> titles = new ArrayList<String>();
        imageUrls.add("http://pic2.16pic.com/00/24/38/16pic_2438497_b.jpg");
        imageUrls.add("http://pic35.photophoto.cn/20150528/0005018358146030_b.jpg");
        titles.add("");
        titles.add("");
        Banner banner = (Banner) view.findViewById(R.id.banner);
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new PicassoImageLoader());
        //设置图片集合
        banner.setImages(imageUrls);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.ZoomOutSlide);
        //设置标题集合（当banner样式有显示title时）
//        banner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(1500);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        if (!HttpUtil.isNetworkAvailable(getContext())) {
            return;
        }
        switch (view.getId()) {
            case R.id.topBtn1:
                Intent mintent = new Intent(mContext, WebViewActivity.class);
                mintent.putExtra("url", "https://www.baidu.com");
                startActivity(mintent);
                break;
            case R.id.topBtn2:
                Intent mintent2 = new Intent(mContext, WebViewActivity.class);
                mintent2.putExtra("url", "http://blog.csdn.net/");
                startActivity(mintent2);
                break;
            case R.id.topBtn3:
                Intent mintent3 = new Intent(mContext, WebViewActivity.class);
                mintent3.putExtra("url", "http://www.jianshu.com/p/8a458cab472d");
                startActivity(mintent3);
                break;
            default:
                break;
        }
    }

    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
//		Log.i("TAG","y--->"+y+"    height-->"+height);
//        if (y <= height) {
//            float scale = (float) y / height;
//            float alpha = (255 * scale);
////			Log.i("TAG","alpha--->"+alpha);
//            //layout全部透明
////			layoutHead.setAlpha(scale);
//            //只是layout背景透明(仿知乎滑动效果)
//            layoutHead.setBackgroundColor(Color.argb((int) alpha, 20, 52, 161));
//            layoutHead.setAlpha(scale);
//
//            tv_zongzichang.setAlpha(1 - scale);
//            zongzichang.setAlpha(1 - scale);
//        }
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

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GlobleValue.SUCCESS:
                    tvDevices.setText("本车厢当前设备连接数：" + devicesNum);
                    break;
                case GlobleValue.FAIL:

                    break;
                case GlobleValue.SUCCESS2:

                    break;
            }
        }
    };

    //  获取设备连接数
    private void getDivices() {
        String url = AllUrl.getInstance().getAllDevicesUrl();
        if (HttpUtil.isNetworkAvailable(getContext())) {
            AsyncTaskManager.getInstance().StartHttpNotToken(new BaseRequestParm(url, "",
                            AsyncTaskManager.ContentTypeJson, "GET", null),
                    new RequestListener<BaseResponseBean>() {

                        @Override
                        public void onFailed() {
                        }

                        @Override
                        public void onComplete(BaseResponseBean bean) {
                            if (bean.isSuccess()) {
                                analiData(bean);
                            }
                        }
                    }, getContext());
        }
    }

    private void analiData(BaseResponseBean bean) {
        // 数据解析
        JsonObject json = GsonUtils.getRootJsonObject(bean.getResult());
        if (json.has("Result")) {
            String Result = GsonUtils.getKeyValue(json, "Result").getAsString();
            if (Result.equals("Success") && json.has("sta_list")) {
                JsonArray dataArray = GsonUtils.getKeyValue(json, "sta_list").getAsJsonArray();
                devicesNum = dataArray.size() + "";
                handler.sendEmptyMessage(GlobleValue.SUCCESS);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
