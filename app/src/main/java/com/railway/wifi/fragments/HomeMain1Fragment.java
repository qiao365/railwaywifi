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
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.railway.wifi.http.httputils.HttpUtil;
import com.railway.wifi.utils.GlobleValue;
import com.railway.wifi.utils.PicassoImageLoader;
import com.railway.wifi.views.ObservableScrollView;
import com.railway.wifi.R;
import com.railway.wifi.http.httputils.AllUrl;
import com.railway.wifi.http.httputils.AsyncTaskManager;
import com.railway.wifi.http.httputils.GsonUtils;
import com.railway.wifi.http.requestparams.BaseRequestParm;
import com.railway.wifi.http.responsebeans.BaseResponseBean;
import com.railway.wifi.http.responsebeans.RequestListener;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;


/**
 * 首页
 *
 * @author CJQ
 */

public class HomeMain1Fragment extends Fragment implements View.OnClickListener, ObservableScrollView.ScrollViewListener, SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mRecyclerView;
    private ImageView image_header;
    private ObservableScrollView personalScrollView;
    private ImageView nodataImageView;
    private int height;
    private View layoutHead;
    private ImageView imageView;
    private ImageView mTabLineIv;
    private TextView mTvOne, mTvTwo, tv_zongzichang, zongzichang, tvWallet2, tvWallet;
    private int screenWidth;
    private PieChart mChart;
    private DrawerLayout mDrawerLayout;
    private ListView mListView;
    private SwipeRefreshLayout mSwipeRefreshWidget;
    private Context mContext;
    private String devicesNum = "--";
    private TextView tvDevices;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main1, null);
        mContext = view.getContext();
        initView(view);
        initBanners(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getDivices();
    }

    private void initView(View view) {
        tvDevices = (TextView) view.findViewById(R.id.tvDevices);
        mSwipeRefreshWidget = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_widget);
        mSwipeRefreshWidget.setOnRefreshListener(this);
        mChart = (PieChart) view.findViewById(R.id.spread_pie_chart);
        // 设置 pieChart 图表基本属性
        mChart.setUsePercentValues(false);            //使用百分比显示
        mChart.getDescription().setEnabled(false);    //设置pieChart图表的描述
        mChart.setBackgroundColor(Color.WHITE);      //设置pieChart图表背景色
//        mChart.setExtraOffsets(5, 5, 5, 5);        //设置pieChart图表上下左右的偏移，类似于外边距
        mChart.setDragDecelerationFrictionCoef(0.95f);//设置pieChart图表转动阻力摩擦系数[0,1]
        mChart.setRotationAngle(0);                   //设置pieChart图表起始角度
        mChart.setRotationEnabled(true);              //设置pieChart图表是否可以手动旋转
        mChart.setHighlightPerTapEnabled(true);       //设置piecahrt图表点击Item高亮是否可用
        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);// 设置pieChart图表展示动画效果

        // 设置 pieChart 图表Item文本属性
        mChart.setDrawEntryLabels(true);              //设置pieChart是否只显示饼图上百分比不显示文字（true：下面属性才有效果）
        mChart.setEntryLabelColor(Color.WHITE);       //设置pieChart图表文本字体颜色
        mChart.setEntryLabelTextSize(10f);            //设置pieChart图表文本字体大小

        // 设置 pieChart 内部圆环属性
        mChart.setDrawHoleEnabled(true);              //是否显示PieChart内部圆环(true:下面属性才有意义)
        mChart.setHoleRadius(0f);                    //设置PieChart内部圆的半径(这里设置28.0f)
        mChart.setTransparentCircleRadius(0f);       //设置PieChart内部透明圆的半径(这里设置31.0f)
        mChart.setTransparentCircleColor(Color.BLACK);//设置PieChart内部透明圆与内部圆间距(31f-28f)填充颜色
        mChart.setTransparentCircleAlpha(50);         //设置PieChart内部透明圆与内部圆间距(31f-28f)透明度[0~255]数值越小越透明
        mChart.setHoleColor(Color.WHITE);             //设置PieChart内部圆的颜色
        mChart.setDrawCenterText(true);               //是否绘制PieChart内部中心文本（true：下面属性才有意义）
        mChart.setCenterText("Test");                 //设置PieChart内部圆文字的内容
        mChart.setCenterTextSize(0f);                //设置PieChart内部圆文字的大小
        mChart.setCenterTextColor(Color.RED);         //设置PieChart内部圆文字的颜色

        // 获取pieCahrt图列
        Legend l = mChart.getLegend();
        l.setEnabled(true);                    //是否启用图列（true：下面属性才有意义）
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setForm(Legend.LegendForm.DEFAULT); //设置图例的形状
        l.setFormSize(10);                      //设置图例的大小
        l.setFormToTextSpace(10f);              //设置每个图例实体中标签和形状之间的间距
        l.setDrawInside(false);
        l.setWordWrapEnabled(true);              //设置图列换行(注意使用影响性能,仅适用legend位于图表下面)
        l.setXEntrySpace(10f);                  //设置图例实体之间延X轴的间距（setOrientation = HORIZONTAL有效）
        l.setYEntrySpace(8f);                  //设置图例实体之间延Y轴的间距（setOrientation = VERTICAL 有效）
        l.setYOffset(0f);                      //设置比例块Y轴偏移量
        l.setTextSize(14f);                      //设置图例标签文本的大小
        l.setTextColor(Color.parseColor("#ff9933"));//设置图例标签文本的颜色
        setData();

    }

    /**
     * 设置饼图的数据
     */
    private void setData() {
        ArrayList<PieEntry> pieEntryList = new ArrayList<PieEntry>();
        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(Color.parseColor("#f17548"));
        colors.add(Color.parseColor("#FF9933"));
        //饼图实体 PieEntry
        PieEntry CashBalance = new PieEntry(15, "流量剩余 150 M");
        PieEntry ConsumptionBalance = new PieEntry(85, "已用流量 850 M");
        pieEntryList.add(CashBalance);
        pieEntryList.add(ConsumptionBalance);
        //饼状图数据集 PieDataSet
        PieDataSet pieDataSet = new PieDataSet(pieEntryList, "总流量 :1G");
        pieDataSet.setSliceSpace(3f);           //设置饼状Item之间的间隙
        pieDataSet.setSelectionShift(10f);      //设置饼状Item被选中时变化的距离
        pieDataSet.setColors(colors);           //为DataSet中的数据匹配上颜色集(饼图Item颜色)
        //最终数据 PieData
        PieData pieData = new PieData(pieDataSet);
        pieData.setDrawValues(true);            //设置是否显示数据实体(百分比，true:以下属性才有意义)
        pieData.setValueTextColor(Color.WHITE);  //设置所有DataSet内数据实体（百分比）的文本颜色
        pieData.setValueTextSize(12f);          //设置所有DataSet内数据实体（百分比）的文本字体大小
//        pieData.setValueTypeface(mTfLight);     //设置所有DataSet内数据实体（百分比）的文本字体样式
        pieData.setValueFormatter(new PercentFormatter());//设置所有DataSet内数据实体（百分比）的文本字体格式
        mChart.setData(pieData);
        mChart.highlightValues(null);
        mChart.invalidate();                    //将图表重绘以显示设置的属性和数据
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
    }

    ;

    @Override
    public void onClick(View view) {
//        Picasso.with(context).load("http://i.imgur.com/DvpvklR.png").into(imageView);
        switch (view.getId()) {
//            case R.id.iv_menu:
//                if (mDrawerLayout.isDrawerOpen(Gravity.END)){
//                    mDrawerLayout.closeDrawer(Gravity.END,true);
//                }else {
//                    mDrawerLayout.openDrawer(Gravity.END,true);
//                }
//                break;
//            case R.id.mRL1:
//                mDrawerLayout.closeDrawer(Gravity.END,true);
//                startActivityForResult(new Intent(view.getContext(), CaptureActivity.class), 0);
//                break;
//
//            case R.id.mRL2:
//                mDrawerLayout.closeDrawer(Gravity.END,true);
//                break;
//            case R.id.tvWallet2:
//            case R.id.tvWallet3:
//                Intent min = new Intent(view.getContext(), ReceivablesCodeActivity.class);
//                min.putExtra("code",tvWallet2.getText().toString());
//                startActivity(min);
//                break;
            default:

                break;
        }
    }

    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
//		Log.i("TAG","y--->"+y+"    height-->"+height);
        if (y <= height) {
            float scale = (float) y / height;
            float alpha = (255 * scale);
//			Log.i("TAG","alpha--->"+alpha);
            //layout全部透明
//			layoutHead.setAlpha(scale);
            //只是layout背景透明(仿知乎滑动效果)
            layoutHead.setBackgroundColor(Color.argb((int) alpha, 20, 52, 161));
            layoutHead.setAlpha(scale);

            tv_zongzichang.setAlpha(1 - scale);
            zongzichang.setAlpha(1 - scale);
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
