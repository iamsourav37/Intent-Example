package com.backpakcersourav.intentexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewExample extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_example);
        getSupportActionBar().setTitle("Backpacker Sourav APP");

        WebView view = findViewById(R.id.webView);
        view.loadUrl("https://www.youtube.com/channel/UCwXw85EAyUWPWDojx-hYG_Q");

        WebSettings webSettings = view.getSettings();
        webSettings.setJavaScriptEnabled(true);
        view.setWebViewClient(new WebViewClient());
    }
}
