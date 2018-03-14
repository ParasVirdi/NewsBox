package com.gradledevextreme.light.newsbox.WebView_y;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.*;
import android.widget.ProgressBar;

import com.gradledevextreme.light.newsbox.R;

public class WebVieww extends AppCompatActivity {

    WebView wb;
    public static String mUrl;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // remove title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_web_vieww);

        progressBar = (ProgressBar) findViewById(R.id.loader);


        wb = (WebView) findViewById(R.id.webView);
        wb.getSettings().setBuiltInZoomControls(true);
        wb.getSettings().setDisplayZoomControls(false);
        wb.setWebViewClient(new WebViewClient());
        wb.loadUrl(mUrl);

        wb.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if (progress == 100) {
                    progressBar.setVisibility(View.GONE);
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                }
            }
        });

    }
}
