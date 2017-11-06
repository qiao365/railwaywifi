package com.crhsi.ylkjcjq.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.railway.wifi.R;
import com.zhy.autolayout.AutoLayoutActivity;

/**
 * Created by ylkjcjq on 2017/10/31.
 */

public class DdzGameLoginActivity extends AutoLayoutActivity implements View.OnClickListener {


    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            switch (msg.what) {
                case 1000: {
                    startActivity(new Intent(DdzGameLoginActivity.this, DdzGameCenterActivity.class));
                    finish();
                }
                break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ddzgame_login);
        TextView tv_tit = (TextView) findViewById(R.id.tv_tit);
        tv_tit.setText("斗地主游戏大厅");
        Button btn_next = (Button) findViewById(R.id.btn_next);
        btn_next.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                finish();
                break;
            case R.id.btn_next:
                uplodaData();
                break;
        }
    }

    private void uplodaData() {
        //...
        mHandler.sendEmptyMessage(1000);
    }
}
