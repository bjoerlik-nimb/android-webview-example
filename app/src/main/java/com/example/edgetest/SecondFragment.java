package com.example.edgetest;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.edgetest.databinding.FragmentSecondBinding;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    private int toolbarHeight;
    private int topPadding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);

        WebView webView = binding.getRoot().findViewById(R.id.webview);
        webView.addJavascriptInterface(new WebInterface(this), "Android");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://10.0.2.2:4200");
        // webView.loadUrl("http://192.168.1.29:4200");
        MainActivity.ignoreInsets = true;

        View toolbar = MainActivity.toolbar;
        ViewGroup.LayoutParams params = toolbar.getLayoutParams();
        toolbarHeight = params.height;

        ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) toolbar.getLayoutParams();
        topPadding = mlp.topMargin;

        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
    }

    public void removeHeader() {
        View toolbar = MainActivity.toolbar;
        toolbar.post(new Runnable() {
            @Override
            public void run() {
                ViewGroup.LayoutParams params = toolbar.getLayoutParams();
                params.height = 0;
                toolbar.setLayoutParams(params);
            }
        });
    }

    public void extend() {
        View toolbar = MainActivity.toolbar;
        toolbar.post(new Runnable() {
            @Override
            public void run() {
                ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) toolbar.getLayoutParams();
                mlp.topMargin = 0;
                toolbar.setLayoutParams(mlp);
                MainActivity.setStatusBarColor(Color.BLUE);
            }
        });

        WebView webView = (WebView) binding.getRoot().findViewById(R.id.webview);
        try {
            webView.post(new Runnable() {
                @Override
                public void run() {
                    webView.loadUrl("javascript:test(" + SecondFragment.this.topPadding + ");");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void restore() {
        View toolbar = MainActivity.toolbar;
        toolbar.post(new Runnable() {
            @Override
            public void run() {
                ViewGroup.LayoutParams params = toolbar.getLayoutParams();
                params.height = toolbarHeight;
                toolbar.setLayoutParams(params);

                ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) toolbar.getLayoutParams();
                mlp.topMargin = topPadding;
                toolbar.setLayoutParams(mlp);
            }
        });
        WebView webView = (WebView) binding.getRoot().findViewById(R.id.webview);
        try {
            webView.post(new Runnable() {
                @Override
                public void run() {
                    webView.loadUrl("javascript:test(0);");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {

        View toolbar = MainActivity.toolbar;
        toolbar.post(new Runnable() {
            @Override
            public void run() {
                binding.webview.loadUrl("");
                binding.webview.setVisibility(View.GONE);
                ViewGroup.LayoutParams params = toolbar.getLayoutParams();
                params.height = toolbarHeight;
                toolbar.setLayoutParams(params);

                ViewGroup.LayoutParams layoutParams = toolbar.getLayoutParams();
                ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) toolbar.getLayoutParams();
                mlp.topMargin = topPadding;
                toolbar.setLayoutParams(mlp);

                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {

        MainActivity.ignoreInsets = true;

        super.onDestroyView();
        binding = null;
    }

}