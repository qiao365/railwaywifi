package com.crhsi.ylkjcjq.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.crhsi.ylkjcjq.R;
import com.crhsi.ylkjcjq.utils.Utils;
import com.zhy.autolayout.AutoLayoutActivity;

public class AboutUsActivity extends AutoLayoutActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        TextView tv_tit = (TextView)findViewById(R.id.tv_tit);
        tv_tit.setText("关于我们");
        TextView currentVersion = (TextView)findViewById(R.id.currentVersion);
        String version = Utils.getVerName(this);
        currentVersion.setText("当前版本:"+version);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                finish();
                break;
        }
    }
}
