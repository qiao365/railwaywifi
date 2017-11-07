package com.railway.wifi.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.railway.wifi.R;

public class WebViewActivity extends AppCompatActivity {

    private  String url = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        WebView mWebView = (WebView)findViewById(R.id.mWebView);

        url = getIntent().getExtras().getString("url").toString();
        WebSettings webSettings = mWebView.getSettings();
        // 将JavaScript设置为可用，这一句话是必须的，不然所做一切都是徒劳的
        webSettings.setJavaScriptEnabled(true);
        // 给webview添加JavaScript接口
//        mWebView.addJavascriptInterface(new JsInterface(), "control");
        // 通过webview加载html页面
        mWebView.loadUrl(url);
    }
}
