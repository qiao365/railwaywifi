package com.railway.wifi.activitys;

import android.graphics.Color;
import android.os.Bundle;

import com.railway.wifi.utils.LoginConfig;
import com.zhy.autolayout.AutoLayoutActivity;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class BaseActivity extends AutoLayoutActivity {

    private  SweetAlertDialog pDialog;
    public LoginConfig mLoginConfig;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoginConfig = new LoginConfig(this);
        initDialog();
    }

    private void initDialog(){
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#4dc1fd"));
        pDialog.setTitleText("Waiting...");
        pDialog.setCancelable(false);
    }

    public void showDialog(){
        initDialog();
        pDialog.show();
    }

    public void showDialog(String txt){
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#4dc1fd"));
        pDialog.setTitleText(txt);
        pDialog.setCancelable(false);
    }

    public void hideDialog(){
        pDialog.dismissWithAnimation();
    }
}
