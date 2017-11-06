package com.railway.wifi.activitys;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.railway.wifi.http.httputils.HttpUtil;
import com.railway.wifi.http.responsebeans.RequestListener;
import com.railway.wifi.utils.GlobleValue;
import com.railway.wifi.utils.LoginConfig;
import com.railway.wifi.utils.MD5Util;
import com.google.gson.JsonObject;
import com.railway.wifi.R;
import com.railway.wifi.http.httputils.AllUrl;
import com.railway.wifi.http.httputils.AsyncTaskManager;
import com.railway.wifi.http.httputils.GsonUtils;
import com.railway.wifi.http.requestparams.BaseRequestParm;
import com.railway.wifi.http.responsebeans.BaseResponseBean;

import es.dmoral.toasty.Toasty;

/**
 * A login screen that offers login .
 */

public class LoginActivity extends BaseActivity {

    private EditText email;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            hideDialog();
            switch (msg.what) {
                case GlobleValue.SUCCESS:
                    mLoginConfig.setUserName(email.getText().toString());
                    backfinish();
                    break;
                case GlobleValue.FAIL:
                    Toasty.error(LoginActivity.this, "网络连接失败", Toast.LENGTH_SHORT, true).show();
                    break;
            }
        }
    };

    private void setIslog(Boolean bolean) {
        mLoginConfig.setIsLog(bolean);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = (EditText) findViewById(R.id.email);
        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
    }

    private void backfinish() {
        Intent mIntent = new Intent();
        // 设置结果，并进行传送
        this.setResult(RESULT_OK, mIntent);
        finish();
    }

    private void attemptLogin() {
        doLogin("", MD5Util.encrypt(""));
    }

    private void doLogin(final String name, String psw) {
        String url = AllUrl.getInstance().getLoginUrl();
        if (HttpUtil.isNetworkAvailable(this)) {
            showDialog();
            AsyncTaskManager.getInstance().StartHttpNotToken(new BaseRequestParm(url, "",
                            AsyncTaskManager.ContentTypeJson, "GET", null),
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
                    }, this);
        } else {
            Toasty.error(this,"网络未连接", Toast.LENGTH_SHORT,true).show();
        }
    }

    private void analiData(BaseResponseBean bean) {
        // 数据解析
        JsonObject json = GsonUtils.getRootJsonObject(bean.getResult());
        if (json.has("Result")) {
            String Result = GsonUtils.getKeyValue(json, "Result").getAsString();
            if (Result.equals("Success")) {
                setIslog(true);
                handler.sendEmptyMessage(GlobleValue.SUCCESS);
            } else {
                setIslog(false);
            }
        } else {
            setIslog(false);
        }
    }

}

