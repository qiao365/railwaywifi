package com.railway.wifi.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.railway.wifi.R;
import com.railway.wifi.utils.LoginConfig;
import com.railway.wifi.views.CompletedView;

/**
 * @author CJQ
 */
public class HomeMain6Fragment extends Fragment implements View.OnClickListener {

    private TextView name;
    private int mCurrentProgress = 50;
    private CompletedView mCompletedView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main6, null);
        initView(view);
        new Thread(new HomeMain6Fragment.ProgressRunable()).start();
        return view;
    }

    private void initView(View view) {
        mCompletedView = (CompletedView)view.findViewById(R.id.mCompletedView);
        name = (TextView) view.findViewById(R.id.name);
        name.setText(LoginConfig.getUserName());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.aboutus:
//                startActivity(new Intent(getContext(), AboutUsActivity.class));
//                break;
            default:

                break;
        }
    }

    class ProgressRunable implements Runnable {

        @Override
        public void run() {
            mCompletedView.setProgress(mCurrentProgress);
        }
    }
}
