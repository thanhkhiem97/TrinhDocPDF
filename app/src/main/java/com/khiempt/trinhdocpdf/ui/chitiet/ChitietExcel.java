package com.khiempt.trinhdocpdf.ui.chitiet;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.khiempt.trinhdocpdf.BuildConfig;
import com.khiempt.trinhdocpdf.R;

import java.io.File;
import java.util.Objects;

public class ChitietExcel extends AppCompatActivity {
    WebView mWebview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment);
        Intent intent = getIntent();
        String value1 = intent.getStringExtra("NAME");
        Log.d("Asd123123", value1);
        mWebview = findViewById(R.id.webview);
        mWebview.getSettings().setJavaScriptEnabled(true);
        WebSettings settings = mWebview.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setBuiltInZoomControls(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        mWebview.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
//        Log.d("tasdasd12qweqwe", url);
        final WebViewClient client = new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }

        };
        mWebview.setWebViewClient(client);
        String pdf = "http://www.adobe.com/devnet/acrobat/pdfs/" + value1;
        mWebview.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=" + pdf);
        String url = "http://docs.google.com/gview?embedded=true&url="
                + value1;


        String name =value1;
        String fileUrl = name;
        File file = new File(fileUrl);
        String mime = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

        Uri theUri = FileProvider.getUriForFile(Objects.requireNonNull(getApplication()),
                BuildConfig.APPLICATION_ID + ".provider", file);

//        Intent intent = new Intent();
//
//        intent.setAction(android.content.Intent.ACTION_VIEW);
//        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
//        intent.setDataAndType(theUri, mime);
//
//        startActivity(intent);

        mWebview.loadUrl(theUri.toString());

    }
}
