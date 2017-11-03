package com.crhsi.ylkjcjq.activitys;

import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.crhsi.ylkjcjq.http.httputils.AllUrl;
import com.crhsi.ylkjcjq.http.httputils.AsyncTaskManager;
import com.crhsi.ylkjcjq.http.httputils.HttpUtil;
import com.crhsi.ylkjcjq.http.httputils.JsonObjectBuilder;
import com.crhsi.ylkjcjq.http.requestparams.BaseRequestParm;
import com.crhsi.ylkjcjq.http.responsebeans.BaseResponseBean;
import com.crhsi.ylkjcjq.http.responsebeans.RequestListener;
import com.crhsi.ylkjcjq.utils.GlobleValue;
import com.crhsi.ylkjcjq.utils.MD5Util;
import com.crhsi.ylkjcjq.utils.Utils;
import com.crhsi.ylkjcjq.R;

import es.dmoral.toasty.Toasty;

public class CreateAccountActivity extends BaseActivity implements View.OnClickListener {

    private AutoCompleteTextView mUserNameView,mEmailView;
    private EditText mPasswordView,mPassWorldAgainView;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            hideDialog();
            switch (msg.what) {
                case GlobleValue.SUCCESS:
                    Toasty.success(CreateAccountActivity.this,"创建账户成功",Toast.LENGTH_SHORT,true).show();
                    finish();
                    break;
                case GlobleValue.FAIL:
                    Toasty.error(CreateAccountActivity.this,"创建出现错误",Toast.LENGTH_SHORT,true).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        ((TextView)findViewById(R.id.tv_tit)).setText("创建账户");

        mUserNameView = (AutoCompleteTextView) findViewById(R.id.username);
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        mPassWorldAgainView = (EditText) findViewById(R.id.passwordagain);
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 5;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                finish();
                break;
            case R.id.btncreate:
                mUserNameView.setError(null);
                mEmailView.setError(null);

                // Store values at the time of the login attempt.
                String name = mUserNameView.getText().toString();
                String email = mEmailView.getText().toString();
                String password = mPasswordView.getText().toString();
                String passwordagain = mPassWorldAgainView.getText().toString();

                boolean cancel = false;
                View focusView = null;

                // Check for a valid password, if the user entered one.
//                if (TextUtils.isEmpty(passwordagain) || !isPasswordValid(passwordagain)) {
//                    mPassWorldAgainView.setError(getString(R.string.error_invalid_password));
//                    focusView = mPassWorldAgainView;
//                    cancel = true;
//                }

                // Check for a valid password, if the user entered one.
                if (TextUtils.isEmpty(password) || !isPasswordValid(password)) {
                    mPasswordView.setError(getString(R.string.error_invalid_password));
                    focusView = mPasswordView;
                    cancel = true;
                }

                // Check for a valid email address.
                if (TextUtils.isEmpty(email) || !Utils.isEmail(email)) {
                    mEmailView.setError(getString(R.string.error_invalid_email));
                    focusView = mEmailView;
                    cancel = true;
                }

                // Check for a valid name.
                if (TextUtils.isEmpty(name)) {
                    mUserNameView.setError(getString(R.string.error_invalid_name));
                    focusView = mUserNameView;
                    cancel = true;
                }

                if (cancel) {
                    focusView.requestFocus();
                } else {
                    if(password.equals(passwordagain)){
                        Toasty.error(this,"登陆密码不能与提现密码一致",Toast.LENGTH_SHORT,true).show();
                        return;
                    }
                    JsonObjectBuilder builder = new JsonObjectBuilder();
                    builder.append("appellation", name);
                    builder.append("account", email);
                    builder.append("password", MD5Util.encrypt(password));
                    builder.append("withdraw", MD5Util.encrypt(passwordagain));
                    createAccount(builder.toString());
                }
                break;
            default:

                break;
        }
    }


    private  void createAccount(String date){

        String url = AllUrl.getInstance().getCreatAccountUrl();

        if (HttpUtil.isNetworkAvailable(this)) {
            showDialog();
            AsyncTaskManager.getInstance().StartHttp(new BaseRequestParm(url, date,
                            AsyncTaskManager.ContentTypeJson, "POST", ""),
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
            Toasty.error(this,"网络未连接",Toast.LENGTH_SHORT,true).show();
            return;
        }
    }

    private void analiData(BaseResponseBean bean) {
        try {
//            List<EndMenu> discussList = GsonUtils.toList(GsonUtils.getRootJsonObject(bean.getResult()), "content", EndMenu.class);
            handler.sendEmptyMessage(GlobleValue.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            handler.sendEmptyMessage(GlobleValue.FAIL);
        }
    }
}
