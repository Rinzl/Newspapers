package com.thd.newspapers.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thd.newspapers.R;

public class WebViewActivity extends AppCompatActivity {
    private WebView webView;
    private TextView textView;
    private CoordinatorLayout linearLayout;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        linearLayout = findViewById(R.id.webViewContainer);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        webView = findViewById(R.id.webview);
        textView = findViewById(R.id.tvHeadline);
        webView.setWebViewClient(new WebViewClient());
        Intent intent = getIntent();
        String url = intent.getStringExtra(MainActivity.INTENT_WEBVIEW_KEY);
        String headline = intent.getStringExtra(MainActivity.INTENT_TITLE_KEY);
        textView.setText(headline);
        textView.setSelected(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.loadUrl(url);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("CALLED");
        webView.removeAllViews();
        linearLayout.removeAllViews();
        webView.clearCache(true);
        webView.clearHistory();
        webView.destroy();
    }
}
