package com.railway.wifi.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.railway.wifi.fragments.HomeMain1Fragment;
import com.railway.wifi.R;
import com.railway.wifi.fragments.HomeMain5Fragment;
import com.railway.wifi.fragments.HomeMain6Fragment;
import com.railway.wifi.http.httputils.AllUrl;
import com.railway.wifi.http.httputils.AsyncTaskManager;
import com.railway.wifi.http.httputils.GsonUtils;
import com.railway.wifi.http.httputils.HttpUtil;
import com.railway.wifi.http.requestparams.BaseRequestParm;
import com.railway.wifi.http.responsebeans.BaseResponseBean;
import com.railway.wifi.http.responsebeans.RequestListener;
import com.railway.wifi.utils.GlobleValue;

import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private HomeMain1Fragment mHomeMain1Fragment;
    private HomeMain5Fragment mHomeMain5Fragment;
    private HomeMain6Fragment mHomeMain6Fragment;
    private ImageView btn_tab1, btn_tab5, btn_tab6;
    private TextView tv_tab1, tv_tab5, tv_tab5_bottom, tv_tab6;
    private FrameLayout fmpan;
    private LayoutInflater inflater;
    private static final int requestCode = 0x0001;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GlobleValue.SUCCESS:

                    break;
                case GlobleValue.SUCCESS2:
                    showhasLog();
                    break;
            }
        }
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        mHomeMain1Fragment = new HomeMain1Fragment();
        transaction.replace(R.id.fragment_stub, mHomeMain1Fragment);
        transaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Islog();
    }

    private void initView() {
        inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        fmpan = (FrameLayout) findViewById(R.id.bottom);
        btn_tab1 = (ImageView) findViewById(R.id.btn_tab1);
        tv_tab1 = (TextView) findViewById(R.id.tv_tab1);

        btn_tab5 = (ImageView) findViewById(R.id.btn_tab5);
        tv_tab5 = (TextView) findViewById(R.id.tv_tab5);
        tv_tab5_bottom = (TextView) findViewById(R.id.tv_tab5_bottom);

        btn_tab6 = (ImageView) findViewById(R.id.btn_tab6);
        tv_tab6 = (TextView) findViewById(R.id.tv_tab6);
        btn_tab1.setVisibility(View.VISIBLE);
    }

    private Boolean getIslog() {
        return mLoginConfig.getIsLog();
    }

    ;

    private void setIslog(Boolean bolean) {
        mLoginConfig.setIsLog(bolean);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tab1:
                hidebtn();
                btn_tab1.setVisibility(View.VISIBLE);
                tv_tab1.setVisibility(View.VISIBLE);
                transaction = fragmentManager.beginTransaction();
                mHomeMain1Fragment = new HomeMain1Fragment();
                transaction.replace(R.id.fragment_stub, mHomeMain1Fragment);
                transaction.commit();
                break;
            case R.id.tab5:
                if (getIslog()) {
                    showLogoutDialog();
                } else {
                    startActivityForResult(new Intent(MainActivity.this, LoginActivity.class), MainActivity.requestCode);
                }
                break;
            case R.id.tab6:
                hidebtn();
                btn_tab6.setVisibility(View.VISIBLE);
                tv_tab6.setVisibility(View.VISIBLE);
                transaction = fragmentManager.beginTransaction();
                mHomeMain6Fragment = new HomeMain6Fragment();
                transaction.replace(R.id.fragment_stub, mHomeMain6Fragment);
                transaction.commit();
                break;
            default:

                break;
        }
    }

    private void showLogoutDialog() {
        final SweetAlertDialog sDialog = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("确定要退出网络吗？");
        sDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sDialog.dismissWithAnimation();
                logout();
            }
        });
        sDialog.show();
    }

    private void hidebtn() {
        btn_tab1.setVisibility(View.GONE);
        tv_tab1.setVisibility(View.GONE);
        btn_tab6.setVisibility(View.GONE);
        tv_tab6.setVisibility(View.GONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MainActivity.requestCode) {
            if(data != null && data.getStringExtra("islog").equals("true")){
                showhasLog();
                setIslog(true);
            }
        }
    }

    private void showhasLog() {
        tv_tab5_bottom.setText("已连接");
        tv_tab5.setText("已连接");
        btn_tab5.setVisibility(View.VISIBLE);
        tv_tab5.setVisibility(View.VISIBLE);
    }

    private void Islog() {
        String url = AllUrl.getInstance().getIsLogUrl();
        if (HttpUtil.isNetworkAvailable(this)) {
            AsyncTaskManager.getInstance().StartHttpNotToken(new BaseRequestParm(url, "",
                            AsyncTaskManager.ContentTypeJson, "GET", null),
                    new RequestListener<BaseResponseBean>() {

                        @Override
                        public void onFailed() {
                            setIslog(false);
                        }

                        @Override
                        public void onComplete(BaseResponseBean bean) {
                            if (bean.isSuccess()) {
                                analiData(bean);
                            } else {
                                setIslog(false);
                            }
                        }
                    }, this);
        } else {
            setIslog(false);
            Toasty.error(this, "网络未连接", Toast.LENGTH_SHORT, true).show();
        }
    }

    private void analiData(BaseResponseBean bean) {
        // 数据解析
        JsonObject json = GsonUtils.getRootJsonObject(bean.getResult());
        if (json.has("Result")) {
            String Result = GsonUtils.getKeyValue(json, "Result").getAsString();
            if (Result.equals("Success")) {
                setIslog(true);
                handler.sendEmptyMessage(GlobleValue.SUCCESS2);
            } else {
                setIslog(false);
            }
        } else {
            setIslog(false);
        }
    }

    private void logout() {
        hideLogin();
        String url = AllUrl.getInstance().getShutDownIpUrl();
        if (HttpUtil.isNetworkAvailable(this)) {
            AsyncTaskManager.getInstance().StartHttpNotToken(new BaseRequestParm(url, "",
                            AsyncTaskManager.ContentTypeJson, "GET", null),
                    new RequestListener<BaseResponseBean>() {

                        @Override
                        public void onFailed() {
                        }

                        @Override
                        public void onComplete(BaseResponseBean bean) {
                        }
                    }, this);
        } else {
            Toasty.error(this, "网络未连接", Toast.LENGTH_SHORT, true).show();
        }
    }

    private void hideLogin() {
        btn_tab5.setVisibility(View.GONE);
        tv_tab5.setVisibility(View.GONE);
        tv_tab5_bottom.setText("未连接");
        tv_tab5.setText("未连接");
        mLoginConfig.setUserName("");
        setIslog(false);
    }
}

