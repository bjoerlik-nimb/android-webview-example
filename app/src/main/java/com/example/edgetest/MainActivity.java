package com.example.edgetest;

import static android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.edgetest.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    static public Insets insets = null;
    static public int toolbarHeight;
    static public View toolbar = null;
    static public View fab = null;

    static public boolean ignoreInsets = false;
    static public MainActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = this;

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        toolbar = findViewById(R.id.toolbar);
        this.toolbarHeight = toolbar.getHeight();
        fab = findViewById(R.id.fab);

        ((View)findViewById(R.id.toolbar)).setVisibility(View.VISIBLE);


        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();



                View toolbar = findViewById(R.id.toolbar);

                toolbar.post(new Runnable() {
                    @Override
                    public void run() {
                        ViewGroup.LayoutParams layoutParams = toolbar.getLayoutParams();
                        ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) toolbar.getLayoutParams();
                        mlp.topMargin = 0;
                        toolbar.setLayoutParams(mlp);
                    }
                });

            }
        });

        // From example
        // Step 1
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        // Step 2
        WindowInsetsControllerCompat windowInsetsController =
                ViewCompat.getWindowInsetsController(getWindow().getDecorView());
        if (windowInsetsController == null) {
            return;
        }

        windowInsetsController.setAppearanceLightNavigationBars(true);

        // Step 3
        ViewCompat.setOnApplyWindowInsetsListener(binding.fab, (v, windowInsets) -> {
            if (!ignoreInsets) {
                Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
                // Apply the insets as a margin to the view. Here the system is setting
                // only the bottom, left, and right dimensions, but apply whichever insets are
                // appropriate to your layout. You can also update the view padding
                // if that's more appropriate.
                ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
                // mlp.leftMargin = insets.left;
                mlp.bottomMargin = insets.bottom + 24;
                mlp.rightMargin = insets.right + 24;
                v.setLayoutParams(mlp);
            }

            // Return CONSUMED if you don't want want the window insets to keep being
            // passed down to descendant views.
            return WindowInsetsCompat.CONSUMED;
        });

        ViewCompat.setOnApplyWindowInsetsListener(binding.toolbar, (v, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            this.insets = insets;

            // Apply the insets as a margin to the view. Here the system is setting
            // only the bottom, left, and right dimensions, but apply whichever insets are
            // appropriate to your layout. You can also update the view padding
            // if that's more appropriate.
            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            if (!ignoreInsets) {
                mlp.topMargin = insets.top;

                View toolbar = MainActivity.toolbar;
                ViewGroup.LayoutParams params = toolbar.getLayoutParams();
                toolbarHeight = params.height;
                params.height = MainActivity.toolbarHeight;
                toolbar.setLayoutParams(params);

                v.setLayoutParams(mlp);
            }

            // Return CONSUMED if you don't want want the window insets to keep being
            // passed down to descendant views.
            return WindowInsetsCompat.CONSUMED;
        });

        transparentStatusAndNavigation();
        setStatusBarColor(Color.BLACK);
    }

    static public void setStatusBarColor(int color) {
        activity.getWindow().getDecorView().setSystemUiVisibility(SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    private void transparentStatusAndNavigation() {

        //make full transparent statusBar
        /*if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            );
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
        }*/
    }

    private void setWindowFlag(final int bits, boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }




    private void timeThingy() {
        /*Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Howdy?");
                final WebView view = findViewById(R.id.webview);
                view.post(new Runnable() {
                    @Override
                    public void run() {
                        ViewGroup webViewContainer = (ViewGroup) view;
                        webViewContainer.removeView(view);
                        view.removeAllViews();
                        //view.getLayoutParams().height = 0;
                        view.destroy();

                        View toolbar = findViewById(R.id.toolbar);
                        ViewGroup.LayoutParams params = toolbar.getLayoutParams();
                        params.height = MainActivity.this.toolbarHeight;
                        toolbar.setLayoutParams(params);

                        ViewGroup.LayoutParams layoutParams = toolbar.getLayoutParams();
                        ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) toolbar.getLayoutParams();
                        mlp.topMargin = MainActivity.this.insets.top;
                        toolbar.setLayoutParams(mlp);
                    }
                });
            }
        }, 2000);*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}