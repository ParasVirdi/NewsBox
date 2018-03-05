package com.gradledevextreme.light.newsbox.WebView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import android.widget.Toast;
import com.gradledevextreme.light.newsbox.R;
import im.delight.android.webview.AdvancedWebView;

public class WebView extends AppCompatActivity implements AdvancedWebView.Listener{




    private AdvancedWebView mWebView;
    public static String mUrl ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);


        mWebView = (AdvancedWebView) findViewById(R.id.webview);
        mWebView.setListener(this, this);
        mWebView.setGeolocationEnabled(false);
        mWebView.setMixedContentAllowed(true);
        mWebView.setCookiesEnabled(true);
        mWebView.setThirdPartyCookiesEnabled(true);



        mWebView.addHttpHeader("X-Requested-With", "");
        mWebView.loadUrl(mUrl);
    }




    @SuppressLint("NewApi")
    @Override
    protected void onResume() {
        super.onResume();
        mWebView.onResume();
        // ...
    }




    @SuppressLint("NewApi")
    @Override
    protected void onPause() {
        mWebView.onPause();
        // ...
        super.onPause();
    }




    @Override
    protected void onDestroy() {
        mWebView.onDestroy();
        // ...
        super.onDestroy();
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        mWebView.onActivityResult(requestCode, resultCode, intent);
        // ...
    }




    @Override
    public void onBackPressed() {
        if (!mWebView.onBackPressed()) {
            return;
        }
        // ...
        super.onBackPressed();
    }




    @Override
    public void onPageStarted(String url, Bitmap favicon) {
        mWebView.setVisibility(View.INVISIBLE);

    }





    @Override
    public void onPageFinished(String url) {
        mWebView.setVisibility(View.VISIBLE);
    }




    @Override
    public void onPageError(int errorCode, String description, String failingUrl) {
        Toast.makeText(WebView.this, "onPageError(errorCode = " + errorCode + ",  description = " + description + ",  failingUrl = " + failingUrl + ")", Toast.LENGTH_SHORT).show();
    }




    @Override
    public void onDownloadRequested(String url, String suggestedFilename, String mimeType, long contentLength, String contentDisposition, String userAgent) {
        Toast.makeText(WebView.this, "onDownloadRequested(url = " + url + ",  suggestedFilename = " + suggestedFilename + ",  mimeType = " + mimeType + ",  contentLength = " + contentLength + ",  contentDisposition = " + contentDisposition + ",  userAgent = " + userAgent + ")", Toast.LENGTH_LONG).show();

		/*if (AdvancedWebView.handleDownload(this, url, suggestedFilename)) {
            // download successfully handled
		}
		else {
			// download couldn't be handled because user has disabled download manager app on the device
		}*/
    }




    @Override
    public void onExternalPageRequest(String url) {
        Toast.makeText(WebView.this, "onExternalPageRequest(url = " + url + ")", Toast.LENGTH_SHORT).show();
    }
}

