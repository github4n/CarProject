package com.mh.core.component;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.mh.core.js.UploadHandler;
import com.mh.core.tools.MHLogUtil;

/**
 * Created by MH on 2016/12/2.
 */

public class MHWebChromeClient extends WebChromeClient {

    private static final String TAG = "MHWebChromeClient";

    private Activity activity;
    private ProgressBar progressBar;
    private UploadHandler handler;

    public MHWebChromeClient(ProgressBar progressBar, Activity activity) {
        this.progressBar = progressBar;
        this.activity = activity;
        initWebChromeClient(activity);
    }

    public MHWebChromeClient(Activity activity) {
        this.activity = activity;
        initWebChromeClient(activity);
    }

    public MHWebChromeClient() {
        initWebChromeClient(null);
    }

    private void initWebChromeClient(Activity activity){
        if (activity != null){
            handler = new UploadHandler(activity);
        }
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        if (progressBar !=null) {
            progressBar.setProgress(newProgress);
            if (newProgress > 90){
                progressBar.setVisibility(View.GONE);
            }else{
                progressBar.setVisibility(View.VISIBLE);
            }
        }
    }


    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
        MHLogUtil.logD(TAG, "onShowFileChooser");
        if (handler != null) {
            handler.onShowFileChooser(filePathCallback,fileChooserParams);
        }
        return true;
    }


    public void openFileChooser(ValueCallback<Uri> uploadFile, String acceptType, String capture){
        MHLogUtil.logD(TAG, "openFileChooser");
        if (handler != null) {
            handler.openFileChooser(uploadFile, acceptType, capture);
        }
    }

    // Android 2.x
    public void openFileChooser(ValueCallback<Uri> uploadFile){
        MHLogUtil.logD(TAG, "openFileChooser  openFileChooser(ValueCallback<Uri> uploadFile)");
        if (handler != null) {
            handler.openFileChooser(uploadFile, null, null);
        }
    }


    // Android 3.0
    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
        MHLogUtil.logD(TAG, "openFileChooser ValueCallback<Uri> uploadMsg, String acceptType");
    }


    public void onActivityResult(int requestCode,int resultCode, Intent intent){
        if (handler != null) {
            handler.onResult(requestCode,resultCode, intent);
        }
    }


    @Override
    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {

        String message = "Console: " + consoleMessage.message() + " " + consoleMessage.sourceId() +  ":" + consoleMessage.lineNumber();

        switch (consoleMessage.messageLevel()) {
            case TIP:
                MHLogUtil.logV(TAG, message);
                break;
            case LOG:
                MHLogUtil.logI(TAG, message);
                break;
            case WARNING:
                MHLogUtil.logW(TAG, message);
                break;
            case ERROR:
                MHLogUtil.logE(TAG, message);
                break;
            case DEBUG:
                MHLogUtil.logD(TAG, message);
                break;
        }

        return true;
    }
}
