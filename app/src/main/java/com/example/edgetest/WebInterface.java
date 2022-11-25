package com.example.edgetest;

import android.webkit.JavascriptInterface;
import android.widget.Toast;

public class WebInterface {

    SecondFragment fragment;

    /** Instantiate the interface and set the context */
    WebInterface(SecondFragment c) {
        fragment = c;
    }

    /** Show a toast from the web page */
    @JavascriptInterface
    public void showToast(String toast) {
        Toast.makeText(fragment.getContext(), toast, Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public void close() {
        fragment.close();;
    }

    @JavascriptInterface
    public void removeHeader() {
        fragment.removeHeader();;
    }

    @JavascriptInterface
    public void extend() {
        fragment.extend();;
    }

    @JavascriptInterface
    public void restore() {
        fragment.restore();;
    }


}
