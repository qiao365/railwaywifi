package com.railway.wifi.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.railway.wifi.R;
import com.railway.wifi.activitys.LoginActivity;
import com.railway.wifi.utils.LoginConfig;
import com.railway.wifi.views.CompletedView;

import es.dmoral.toasty.Toasty;

/**
 * @author CJQ
 */
public class HomeMain6Fragment extends Fragment implements View.OnClickListener {

    private TextView name;
    private int mCurrentProgress = 50;
    private CompletedView mCompletedView;
    private ImageView imgOne, imgCenter, imgTwo;
    private TextView tvOne, tvCenter, tvTwo, tv_chongzhi;
    private Context mContext;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main6, null);
        mContext = view.getContext();
        initView(view);
        new Thread(new HomeMain6Fragment.ProgressRunable()).start();
        return view;
    }

    private void initView(View view) {
        imgOne = (ImageView) view.findViewById(R.id.imgOne);
        imgCenter = (ImageView) view.findViewById(R.id.imgCenter);
        imgTwo = (ImageView) view.findViewById(R.id.imgTwo);
        tvOne = (TextView) view.findViewById(R.id.tvOne);
        tvCenter = (TextView) view.findViewById(R.id.tvCenter);
        tvTwo = (TextView) view.findViewById(R.id.tvTwo);
        tv_chongzhi = (TextView) view.findViewById(R.id.tv_chongzhi);
        imgOne.setOnClickListener(this);
        imgCenter.setOnClickListener(this);
        imgTwo.setOnClickListener(this);
        tv_chongzhi.setOnClickListener(this);
        mCompletedView = (CompletedView) view.findViewById(R.id.mCompletedView);
        name = (TextView) view.findViewById(R.id.name);
        name.setText(getTel(LoginConfig.getUserName()));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgOne:
                backDef();
                imgOne.setImageResource(R.mipmap.ic_buywifi_pre);
                tvOne.setTextColor(getResources().getColor(R.color.White));
                break;
            case R.id.imgCenter:
                backDef();
                imgCenter.setImageResource(R.mipmap.ic_buywifi_pre);
                tvCenter.setTextColor(getResources().getColor(R.color.White));
                break;
            case R.id.imgTwo:
                backDef();
                imgTwo.setImageResource(R.mipmap.ic_buywifi_pre);
                tvTwo.setTextColor(getResources().getColor(R.color.White));
                break;
            case R.id.tv_chongzhi:
                Toasty.error(mContext, "试运营不可充值", Toast.LENGTH_SHORT, true).show();
                break;
            default:

                break;
        }
    }

    private void backDef() {
        imgOne.setImageResource(R.mipmap.ic_buywifi_def);
        imgCenter.setImageResource(R.mipmap.ic_buywifi_def);
        imgTwo.setImageResource(R.mipmap.ic_buywifi_def);
        tvOne.setTextColor(getResources().getColor(R.color.fontBlack1));
        tvCenter.setTextColor(getResources().getColor(R.color.fontBlack1));
        tvTwo.setTextColor(getResources().getColor(R.color.fontBlack1));
    }

    class ProgressRunable implements Runnable {

        @Override
        public void run() {
            mCompletedView.setProgress(mCurrentProgress);
        }
    }

    private String getTel(String tel) {
        if (tel == null || tel.equals("")) {
            return "";
        }
        String t = tel.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        return t;
    }
}
