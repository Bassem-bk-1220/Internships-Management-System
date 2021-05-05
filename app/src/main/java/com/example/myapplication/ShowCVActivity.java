package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ScrollView;

public class ShowCVActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_c_v);
        WebView webView=findViewById(R.id.web_view_cv);
       String url= getIntent().getStringExtra("file_upload");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
       webView.loadUrl("https://firebasestorage.googleapis.com/v0/b/intership-management-system.appspot.com/o/Uploadfiles%2F1619965647270?alt=media&token=1fea7cf9-dbea-4998-a42e-929c53c18317");
    }
}