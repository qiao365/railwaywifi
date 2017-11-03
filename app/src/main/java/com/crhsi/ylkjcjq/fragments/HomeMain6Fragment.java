package com.crhsi.ylkjcjq.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crhsi.ylkjcjq.R;
import com.crhsi.ylkjcjq.activitys.AboutUsActivity;
import com.crhsi.ylkjcjq.utils.LoginConfig;


/**
 *
 * @author CJQ
 */
public class HomeMain6Fragment extends Fragment implements View.OnClickListener{

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main6, null);

        view.findViewById(R.id.aboutus).setOnClickListener(this);
        TextView mText_Myname = (TextView)view.findViewById(R.id.mText_Myname);
//        mText_Myname.setText(LoginConfig.getUserName());
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
