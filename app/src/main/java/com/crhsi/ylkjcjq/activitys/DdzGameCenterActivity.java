package com.crhsi.ylkjcjq.activitys;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.crhsi.ylkjcjq.R;
import com.crhsi.ylkjcjq.ddznet.TestClient;
import com.ddz.boyaddz.DDZActivity;
import com.netddz.NetDDZActivity;
import com.vilyever.socketclient.SocketClient;
import com.zhy.autolayout.AutoLayoutActivity;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class DdzGameCenterActivity extends BaseActivity implements View.OnClickListener {

    private TestClient mTestClient;
    private SocketClient localSocketClient;
    protected TestClient getmTestClient() {
        if (this.mTestClient == null) {
            this.mTestClient = new TestClient();
        }
        return this.mTestClient;
    }

    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            switch (msg.what) {
                case 1000: {
                    final SweetAlertDialog sDialog = new SweetAlertDialog(DdzGameCenterActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("创建成功")
                            .setContentText("正在进入房间001...");
                    sDialog.show();

                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            sDialog.dismissWithAnimation();
                            startActivity(new Intent(DdzGameCenterActivity.this, NetDDZActivity.class));
                        }
                    },2000);
                }
                break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ddz_game_center);
        TextView tv_tit = (TextView) findViewById(R.id.tv_tit);
        tv_tit.setText("斗地主游戏中心");
    }

    @Override
    protected void onStart() {
        super.onStart();
        localSocketClient = getmTestClient().connect();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                finish();
                break;
            case R.id.quicly_start:
                startActivity(new Intent(this, DDZActivity.class));
                break;
            case R.id.creat_room:
                createRoom();
                break;
            case R.id.comein_room:
                startActivity(new Intent(this, DdzGamePlayActivity.class));
                break;
        }
    }

    //创建房间
    private void createRoom(){

        //...
        mHandler.sendEmptyMessage(1000);
    }
}
