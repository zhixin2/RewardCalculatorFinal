package com.example.rewardcalculator;


import android.content.Intent;
import android.net.http.SslError;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class WebActivity extends AppCompatActivity implements View.OnClickListener {

    private Button backBtn;
    private WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_activity);
        webView = (WebView) findViewById(R.id.webView);
        backBtn = (Button) findViewById(R.id.back_btn);
        backBtn.setOnClickListener(this);
        Intent intent = getIntent();
        String url = intent.getStringExtra("URL");
        webView.getSettings().setJavaScriptEnabled(true);
        if (TextUtils.isEmpty(url)){
            finish();
        }
        webView.loadUrl(url);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_btn:
                finish();
                break;
            default:
                break;
        }
    }

}
