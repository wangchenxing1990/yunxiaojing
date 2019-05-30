package com.yun.xiao.jing.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.yun.xiao.jing.ChessApp;
import com.yun.xiao.jing.R;

public class WebActivity extends AppCompatActivity implements View.OnClickListener {
    private WebView mWebView;
    private FrameLayout frame_layout;
    String url = "";

    public static void start(Activity activity, String url) {
        Intent intent = new Intent(activity, WebActivity.class);
        intent.putExtra("url", url);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ChessApp.addActivity(this);
        url = getIntent().getStringExtra("url");
        setContentView(R.layout.activity_web);
        frame_layout = findViewById(R.id.frame_layout);
        mWebView = findViewById(R.id.web_view);
        initWebView();
        frame_layout.setOnClickListener(this);
        if (!TextUtils.isEmpty(url)) {
            mWebView.loadUrl(url);
        }
    }

    public void initWebView() {
        mWebView.setVisibility(View.VISIBLE);
//        mWebView.clearCache(true);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setSupportZoom(false);
        webSettings.setSavePassword(false);
        webSettings.setSaveFormData(false);
        webSettings.setJavaScriptEnabled(true);
//        webSettings.setBuiltInZoomControls(true);
        webSettings.setBuiltInZoomControls(false);
//        webSettings.setUseWideViewPort(true);//集WebView是否应该使支持“视窗”HTML meta标记或应该使用视窗。
        webSettings.setLoadWithOverviewMode(true);  //是否使用WebView加载页面,也就是说,镜头拉出宽度适合在屏幕上的内容。
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setAllowFileAccess(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCacheEnabled(true);
//        webSettings.setBlockNetworkImage(true);//图片后台加载
        webSettings.setDefaultTextEncodingName("UTF-8");
        webSettings.setAppCachePath(getApplicationContext().getCacheDir().getAbsolutePath());
        webSettings.setPluginState(WebSettings.PluginState.ON);
        //H5
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                mWebView.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                mProgressBar.setVisibility(View.VISIBLE);
//                mWebView.setVisibility(View.GONE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
//                mProgressBar.setVisibility(View.GONE);
//                mWebView.setVisibility(View.VISIBLE);
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
//                mProgressBar.setProgress(newProgress);
            }

            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                return true;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.frame_layout:
                finish();
                break;
        }
    }
}
