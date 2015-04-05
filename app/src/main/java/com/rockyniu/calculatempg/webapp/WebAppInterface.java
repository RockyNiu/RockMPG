package com.rockyniu.calculatempg.webapp;

import android.app.Activity;
import android.content.Context;
import android.webkit.JavascriptInterface;

import com.rockyniu.calculatempg.util.ToastHelper;

/**
 * Created by Lei on 2015/4/4.
 */
public class WebAppInterface {
    private Context mContext;

    /**
     * Instantiate the interface and set the context
     */
    public WebAppInterface(Context context) {
        mContext = context;
    }

    /**
     * Show a toast from the web page
     */
    @JavascriptInterface
    public void showToast(String message) {
        ToastHelper.showToastInternal((Activity) mContext, message);
    }
}
