package com.railway.wifi.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.railway.wifi.R;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameCenterActivity extends AutoLayoutActivity implements View.OnClickListener {

    // 图片封装为一个数组
    private int[] icon = {R.mipmap.ic_ddz, R.mipmap.ic_wuziqi};
    private String[] iconName = {"斗地主", "五子棋"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_center);
        TextView tv_tit = (TextView) findViewById(R.id.tv_tit);
        tv_tit.setText("游戏中心");
        GridView mGridView = (GridView) findViewById(R.id.mGridView);
        //新建适配器
        String[] from = {"image", "text"};
        int[] to = {R.id.image, R.id.text};
        SimpleAdapter sim_adapter = new SimpleAdapter(this, getData(),
                R.layout.activity_gamecenter_item, from, to);
        //配置适配器
        mGridView.setAdapter(sim_adapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                HashMap<String, Object> item=(HashMap<String, Object>) adapterView.getItemAtPosition(i);
//                setTitle((String)item.get("ItemText"));
                switch (i) {
                    case 0:
                        startActivity(new Intent(GameCenterActivity.this, DdzGameLoginActivity.class));
                        break;
                }
            }
        });
    }

    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> data_list = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < icon.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", icon[i]);
            map.put("text", iconName[i]);
            data_list.add(map);
        }
        return data_list;
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
