package com.railway.wifi.activitys;

import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.railway.wifi.R;
import com.crhsi.wifi.ddznet.TestClient;
import com.vilyever.socketclient.SocketClient;
import com.zhy.autolayout.AutoLayoutActivity;

public class DdzGamePlayActivity extends AutoLayoutActivity implements View.OnClickListener{

    protected static DdzGamePlayActivity instance;
    private TestClient mTestClient;
    private SocketClient localSocketClient;
    public static DdzGamePlayActivity getInstance() {
        return instance;
    }

    protected TestClient getmTestClient() {
        if (this.mTestClient == null) {
            this.mTestClient = new TestClient();
        }
        return this.mTestClient;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ddz_game_play);
        TextView tv_tit = (TextView)findViewById(R.id.tv_tit);
        tv_tit.setText("斗地主游戏");
    }

    @Override
    protected void onStart() {
        super.onStart();
        instance = this;
        localSocketClient = getmTestClient().connect();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                finish();
                break;
        }
    }

    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            switch (msg.what) {
                case 1000: {
                }
                break;

            }
        }
    };
}
