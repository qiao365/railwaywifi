package com.crhsi.ylkjcjq.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.TextView;

import com.crhsi.ylkjcjq.fragments.HomeMain1Fragment;
import com.crhsi.ylkjcjq.R;
import com.crhsi.ylkjcjq.fragments.HomeMain5Fragment;
import com.crhsi.ylkjcjq.fragments.HomeMain6Fragment;
import com.zhy.autolayout.AutoLayoutActivity;

public class MainActivity extends AutoLayoutActivity implements View.OnClickListener {
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private HomeMain1Fragment mHomeMain1Fragment;
    private HomeMain5Fragment mHomeMain5Fragment;
    private HomeMain6Fragment mHomeMain6Fragment;
    private ImageView btn_tab1,btn_tab5,btn_tab6;
    private TextView tv_tab1,tv_tab5,tv_tab6;
    private FrameLayout fmpan;
    private LayoutInflater inflater;

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

    private void initView() {
        inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        fmpan = (FrameLayout) findViewById(R.id.bottom);
        btn_tab1 = (ImageView) findViewById(R.id.btn_tab1);
        tv_tab1 = (TextView) findViewById(R.id.tv_tab1);

        btn_tab5 = (ImageView) findViewById(R.id.btn_tab5);
        tv_tab5 = (TextView) findViewById(R.id.tv_tab5);

        btn_tab6 = (ImageView) findViewById(R.id.btn_tab6);
        tv_tab6 = (TextView) findViewById(R.id.tv_tab6);
        btn_tab1.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View view) {
        hidebtn();
        switch (view.getId()) {
            case R.id.tab1:
                btn_tab1.setVisibility(View.VISIBLE);
                tv_tab1.setVisibility(View.VISIBLE);
                transaction = fragmentManager.beginTransaction();
                mHomeMain1Fragment = new HomeMain1Fragment();
                transaction.replace(R.id.fragment_stub, mHomeMain1Fragment);
                transaction.commit();
                break;
            case R.id.tab5:
                btn_tab5.setVisibility(View.VISIBLE);
                tv_tab5.setVisibility(View.VISIBLE);
                transaction = fragmentManager.beginTransaction();
                mHomeMain5Fragment = new HomeMain5Fragment();
                transaction.replace(R.id.fragment_stub, mHomeMain5Fragment);
                transaction.commit();
                break;
            case R.id.tab6:
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

    private void hidebtn(){
        btn_tab1.setVisibility(View.GONE);
        tv_tab1.setVisibility(View.GONE);
        btn_tab5.setVisibility(View.GONE);
        tv_tab5.setVisibility(View.GONE);
        btn_tab6.setVisibility(View.GONE);
        tv_tab6.setVisibility(View.GONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK) {
//            String result = data.getExtras().getString("result");
//            Intent mIntent = new Intent(this,TransferActivity.class);
//            mIntent.putExtra("code",result);
//            startActivity(mIntent);
//        }else {
//            mHomeMain1Fragment.onActivityResult(requestCode, resultCode, data);
//        }
    }
}

