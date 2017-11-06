package com.crhsi.ylkjcjq.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.crhsi.ylkjcjq.activitys.ReceivablesCodeActivity;
import com.crhsi.ylkjcjq.http.httputils.HttpUtil;
import com.crhsi.ylkjcjq.utils.GlobleValue;
import com.crhsi.ylkjcjq.utils.PicassoImageLoader;
import com.crhsi.ylkjcjq.views.ObservableScrollView;
import com.railway.wifi.R;
import com.crhsi.ylkjcjq.activitys.CoinDetialActivity;
import com.crhsi.ylkjcjq.activitys.CreatePackageActivity;
import com.crhsi.ylkjcjq.adapters.EndMenuItemAdapter;
import com.crhsi.ylkjcjq.adapters.RecyclerViewAdapter;
import com.crhsi.ylkjcjq.http.httputils.AllUrl;
import com.crhsi.ylkjcjq.http.httputils.AsyncTaskManager;
import com.crhsi.ylkjcjq.http.httputils.GsonUtils;
import com.crhsi.ylkjcjq.http.requestparams.BaseRequestParm;
import com.crhsi.ylkjcjq.http.responsebeans.BaseResponseBean;
import com.crhsi.ylkjcjq.http.responsebeans.RequestListener;
import com.crhsi.ylkjcjq.models.CoinObject;
import com.crhsi.ylkjcjq.models.Wallet;
import com.crhsi.ylkjcjq.utils.LoginConfig;
import com.crhsi.ylkjcjq.views.RecyclerViewItemClickListener;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.xys.libzxing.zxing.activity.CaptureActivity;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;


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

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main1, null);
        mContext = view.getContext();
        initView(view);
        initBanners(view);
        return view;
    }

    private void initView(View view) {
//        mChart.setUsePercentValues(true);//显示成百分比
//        mChart.getDescription().setEnabled(false);
//        mChart.setExtraOffsets(5, 10, 5, 5);
//        mChart.getLegend().setEnabled(false);//图例是否可用
//
//        mChart.setDragDecelerationFrictionCoef(0.95f);
//
////        mChart.setCenterTextTypeface(mTfLight);
//        mChart.setCenterText("22");
//
//        mChart.setDrawHoleEnabled(true);
//        mChart.setHoleColor(Color.WHITE);
//
//        mChart.setTransparentCircleColor(Color.WHITE);
//        mChart.setTransparentCircleAlpha(110);
//
//        mChart.setHoleRadius(0f);//半径
//        mChart.setTransparentCircleRadius(0f);// 半透明圈
//        mChart.setDrawCenterText(true);//饼状图中间可以添加文字
//
//        mChart.setRotationAngle(90);// 初始旋转角度
//        // enable rotation of the chart by touch
//        mChart.setRotationEnabled(true);// 可以手动旋转
//        mChart.setHighlightPerTapEnabled(true);
//
//        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
//        mChart.setEntryLabelColor(Color.WHITE);
//        mChart.setEntryLabelTextSize(12f);
//
        mSwipeRefreshWidget = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_widget);

         mChart = (PieChart) view.findViewById(R.id.spread_pie_chart);
        // 设置 pieChart 图表基本属性
        mChart.setUsePercentValues(false);            //使用百分比显示
        mChart.getDescription().setEnabled(false);    //设置pieChart图表的描述
        mChart.setBackgroundColor(Color.WHITE);      //设置pieChart图表背景色
        mChart.setExtraOffsets(5, 10, 60, 10);        //设置pieChart图表上下左右的偏移，类似于外边距
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
        PieDataSet pieDataSet = new PieDataSet(pieEntryList, "流量总览");
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
//                startActivityForResult(new Intent(view.getContext(),CreatePackageActivity.class),0);
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

    private void getWallet() {
        String url = AllUrl.getInstance().getAccountWalletsUrl();
        if (HttpUtil.isNetworkAvailable(mContext)) {
            AsyncTaskManager.getInstance().StartHttp(new BaseRequestParm(url, "",
                            AsyncTaskManager.ContentTypeJson, "GET", LoginConfig.getAuthorization()),
                    new RequestListener<BaseResponseBean>() {

                        @Override
                        public void onFailed() {
                            handler.sendEmptyMessage(GlobleValue.FAIL);
                        }

                        @Override
                        public void onComplete(BaseResponseBean bean) {
                            if (bean.isSuccess()) {
                                analiData(bean);
                            } else
                                handler.sendEmptyMessage(GlobleValue.FAIL);
                        }
                    }, mContext);
        } else {
            Toasty.error(mContext, "网络未连接", Toast.LENGTH_SHORT, true).show();
            return;
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GlobleValue.SUCCESS:


                    break;
                case GlobleValue.FAIL:

                    break;
                case GlobleValue.SUCCESS2:

                    break;
            }
        }
    };


    private List<Wallet> mWallets;

    private void analiData(BaseResponseBean bean) {
        try {
            mWallets = GsonUtils.toList(GsonUtils.getRootJsonObject(bean.getResult()),
                    "data", Wallet.class);
            handler.sendEmptyMessage(GlobleValue.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            handler.sendEmptyMessage(GlobleValue.FAIL);
        }
    }

    private List<CoinObject> mCoinObjects;

    private void analiDataCoins(BaseResponseBean bean) {
        try {
            mCoinObjects = GsonUtils.toList(GsonUtils.getRootJsonObject(bean.getResult()),
                    "data", CoinObject.class);
            handler.sendEmptyMessage(GlobleValue.SUCCESS2);
        } catch (Exception e) {
            e.printStackTrace();
            handler.sendEmptyMessage(GlobleValue.FAIL);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
