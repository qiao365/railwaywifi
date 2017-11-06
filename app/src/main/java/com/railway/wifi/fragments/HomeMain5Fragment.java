package com.railway.wifi.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.railway.wifi.R;
import com.railway.wifi.activitys.AboutUsActivity;
import com.railway.wifi.activitys.GameCenterActivity;


/**
 * @author CJQ
 */
public class HomeMain5Fragment extends Fragment implements View.OnClickListener {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main5, null);

        Button mButton = (Button) view.findViewById(R.id.btn);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), GameCenterActivity.class));
            }
        });
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.aboutus:
                startActivity(new Intent(getContext(), AboutUsActivity.class));
                break;
            default:

                break;
        }
    }
}
