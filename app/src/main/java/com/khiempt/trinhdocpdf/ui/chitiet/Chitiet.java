package com.khiempt.trinhdocpdf.ui.chitiet;

import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;
import com.khiempt.trinhdocpdf.R;

import java.io.File;

public class Chitiet extends AppCompatActivity {
    PDFView mWebview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_chitiet);
        Intent intent = getIntent();
        String value1 = intent.getStringExtra("NAME");
        Log.d("Asd123123",value1);
        mWebview = findViewById(R.id.pdfView);
        File file = new File(value1);
      mWebview.fromFile(file ).load();

    }

}
