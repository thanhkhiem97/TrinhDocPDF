package com.khiempt.trinhdocpdf;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.khiempt.trinhdocpdf.constain.Constants;
import com.khiempt.trinhdocpdf.ui.AllFileFragment;
import com.khiempt.trinhdocpdf.ui.SharedPref;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import org.jetbrains.annotations.NotNull;

import java.io.File;

import static android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawer;
    NavigationView navigationView;
    TabLayout tabLayout;
    ViewPager viewPager;
    FrameLayout header;
    ImageView actionImage;
    ImageView imageView;
    TextView tvTitle;
    BottomBar bottomBar;
    FragmentPagerAdapter adapter;
    private AppBarConfiguration mAppBarConfiguration;
    private static final String[] PERMISSIONS = new String[]{Manifest.permission.CAMERA, Manifest.permission.CALL_PHONE,
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};//, Manifest.permission.PROCESS_OUTGOING_CALLS
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 98;
    private File path = new File(Environment.getExternalStorageDirectory() + "");
    String extStore = System.getenv("EXTERNAL_STORAGE");
    File f_exts = new File(extStore);
    int themeIdcurrent;

    public static SharedPref sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        setTabLayout(1);
        checkPermissionCall();
        sharedpreferences = new SharedPref(getApplication());
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {
                if (tabId == R.id.action_home) {
                    setTabLayout(1);
                    sharedpreferences.putString(Constants.KEY_BOTTOM, "1");
                } else if (tabId == R.id.action_ganday) {
                    setTabLayout(2);
                    sharedpreferences.putString(Constants.KEY_BOTTOM, "2");
                } else if (tabId == R.id.action_dautrang) {
                    setTabLayout(3);
                    sharedpreferences.putString(Constants.KEY_BOTTOM, "3");
                }

            }
        });

        SharedPreferences locationpref = getApplicationContext()
                .getSharedPreferences("MainActivity", MODE_PRIVATE);
        themeIdcurrent = locationpref.getInt("themeid", R.style.Theme_TrinhDocPDF);
    }

    private void checkPermissionCall() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int hasPermission1 = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int hasPermission2 = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            if (hasPermission1 != PackageManager.PERMISSION_GRANTED
                    || hasPermission2 != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_CODE_ASK_PERMISSIONS);
            }
        }
    }

    public void init() {
        drawer = findViewById(R.id.drawer_layout);
        bottomBar = findViewById(R.id.bottomBar);
        header = findViewById(R.id.header);
        actionImage = findViewById(R.id.action_image);
        imageView = findViewById(R.id.imageView);
        tvTitle = findViewById(R.id.tv_title);
        navigationView = findViewById(R.id.navnav_view_view);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.openDrawer(GravityCompat.START);
            }
        });

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

    }


    @SuppressLint("WrongViewCast")
    public void setTabLayout(int poso) {
        tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.viewPager);

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        TabLayoutAdapter adapter = new TabLayoutAdapter(this, getSupportFragmentManager(), 5, poso);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.selectTab(tabLayout.getTabAt(1));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            /**
             * This method will be invoked when the current page is scrolled, either as part
             * of a programmatically initiated smooth scroll or a user initiated touch scroll.
             *
             * @param position             Position index of the first page currently being displayed.
             *                             Page position+1 will be visible if positionOffset is nonzero.
             * @param positionOffset       Value from [0, 1) indicating the offset from the page at position.
             * @param positionOffsetPixels Value in pixels indicating the offset from position.
             */
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            /**
             * Called when the scroll state changes. Useful for discovering when the user
             * begins dragging, when the pager is automatically settling to the current page,
             * or when it is fully stopped/idle.
             *
             * @param state The new scroll state.
             * @see ViewPager#SCROLL_STATE_IDLE
             * @see ViewPager#SCROLL_STATE_DRAGGING
             * @see ViewPager#SCROLL_STATE_SETTLING
             */
            @Override
            public void onPageScrollStateChanged(int state) {
                Log.d("asdasdasda",state+"");
            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setCurrentItem(1, true);
        tabLayout.setTabTextColors(

                // Unselected Tab Text Color

                ContextCompat.getColor(MainActivity.this, R.color.color_white_01_v2),

                // Selected Tab Text Color

                ContextCompat.getColor(MainActivity.this, R.color.white)
        );
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition());
                Drawable qqqq = getResources().getDrawable(R.drawable.ic_baseline_search_24);

                switch (tab.getPosition()) {
                    case 0:
                        tabLayout.setTabTextColors(

                                // Unselected Tab Text Color

                                ContextCompat.getColor(MainActivity.this, R.color.black),

                                // Selected Tab Text Color

                                ContextCompat.getColor(MainActivity.this, R.color.md_red_700)
                        );
                        imageView.setImageResource(R.drawable.ic_outline_view_headline_24_black);
                        actionImage.setImageResource(R.drawable.ic_outline_filter_alt_24_black);
                        Drawable img = getResources().getDrawable(R.drawable.ic_baseline_search_24_black);
                        tvTitle.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
                        tvTitle.setTextColor(ContextCompat.getColor(MainActivity.this,
                                R.color.black));
                        header.setBackgroundColor(ContextCompat.getColor(MainActivity.this,
                                R.color.white));
                        tabLayout.setBackgroundColor(ContextCompat.getColor(MainActivity.this,
                                R.color.white));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this,
                                    R.color.white));
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                                getWindow().getDecorView().getWindowInsetsController().setSystemBarsAppearance(APPEARANCE_LIGHT_STATUS_BARS, APPEARANCE_LIGHT_STATUS_BARS);
                            }

                        }
                        break;

                    case 1:
                        tabLayout.setTabTextColors(

                                // Unselected Tab Text Color

                                ContextCompat.getColor(MainActivity.this, R.color.color_white_01_v2),

                                // Selected Tab Text Color

                                ContextCompat.getColor(MainActivity.this, R.color.white)
                        );
                        tvTitle.setCompoundDrawablesWithIntrinsicBounds(qqqq, null, null, null);
                        imageView.setImageResource(R.drawable.ic_outline_view_headline_24);
                        actionImage.setImageResource(R.drawable.ic_outline_filter_alt_24);
                        tvTitle.setTextColor(ContextCompat.getColor(MainActivity.this,
                                R.color.white));
                        header.setBackgroundColor(ContextCompat.getColor(MainActivity.this,
                                R.color.md_red_700));
                        tabLayout.setBackgroundColor(ContextCompat.getColor(MainActivity.this,
                                R.color.md_red_700));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this,
                                    R.color.md_red_700));
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                                getWindow().getDecorView().getWindowInsetsController().setSystemBarsAppearance(0, APPEARANCE_LIGHT_STATUS_BARS);
                            }
                        }
                        break;
                    case 2:
                        tabLayout.setTabTextColors(

                                // Unselected Tab Text Color

                                ContextCompat.getColor(MainActivity.this, R.color.color_white_01_v2),

                                // Selected Tab Text Color

                                ContextCompat.getColor(MainActivity.this, R.color.white)
                        );
                        tvTitle.setCompoundDrawablesWithIntrinsicBounds(qqqq, null, null, null);
                        imageView.setImageResource(R.drawable.ic_outline_view_headline_24);
                        actionImage.setImageResource(R.drawable.ic_outline_filter_alt_24);
                        tvTitle.setTextColor(ContextCompat.getColor(MainActivity.this,
                                R.color.white));
                        header.setBackgroundColor(ContextCompat.getColor(MainActivity.this,
                                R.color.md_blue_700));
                        tabLayout.setBackgroundColor(ContextCompat.getColor(MainActivity.this,
                                R.color.md_blue_700));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this,
                                    R.color.md_blue_700));
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                                getWindow().getDecorView().getWindowInsetsController().setSystemBarsAppearance(0, APPEARANCE_LIGHT_STATUS_BARS);
                            }
                        }
                        break;
                    case 3:
                        tabLayout.setTabTextColors(

                                // Unselected Tab Text Color

                                ContextCompat.getColor(MainActivity.this, R.color.color_white_01_v2),

                                // Selected Tab Text Color

                                ContextCompat.getColor(MainActivity.this, R.color.white)
                        );
                        tvTitle.setCompoundDrawablesWithIntrinsicBounds(qqqq, null, null, null);
                        imageView.setImageResource(R.drawable.ic_outline_view_headline_24);
                        actionImage.setImageResource(R.drawable.ic_outline_filter_alt_24);
                        tvTitle.setTextColor(ContextCompat.getColor(MainActivity.this,
                                R.color.white));
                        header.setBackgroundColor(ContextCompat.getColor(MainActivity.this,
                                R.color.md_green_700));
                        tabLayout.setBackgroundColor(ContextCompat.getColor(MainActivity.this,
                                R.color.md_green_700));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this,
                                    R.color.md_green_700));
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                                getWindow().getDecorView().getWindowInsetsController().setSystemBarsAppearance(0, APPEARANCE_LIGHT_STATUS_BARS);
                            }
                        }
                        break;
                    case 4:
                        tabLayout.setTabTextColors(

                                // Unselected Tab Text Color

                                ContextCompat.getColor(MainActivity.this, R.color.color_white_01_v2),

                                // Selected Tab Text Color

                                ContextCompat.getColor(MainActivity.this, R.color.white)
                        );
                        tvTitle.setCompoundDrawablesWithIntrinsicBounds(qqqq, null, null, null);
                        imageView.setImageResource(R.drawable.ic_outline_view_headline_24);
                        actionImage.setImageResource(R.drawable.ic_outline_filter_alt_24);
                        tvTitle.setTextColor(ContextCompat.getColor(MainActivity.this,
                                R.color.white));
                        header.setBackgroundColor(ContextCompat.getColor(MainActivity.this,
                                R.color.md_yellow_700));
                        tabLayout.setBackgroundColor(ContextCompat.getColor(MainActivity.this,
                                R.color.md_yellow_700));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this,
                                    R.color.md_yellow_700));
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                                getWindow().getDecorView().getWindowInsetsController().setSystemBarsAppearance(0, APPEARANCE_LIGHT_STATUS_BARS);
                            }
                        }
                        break;
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    /**
     * Called when an item in the navigation menu is selected.
     *
     * @param item The selected item
     * @return true to display the item as the selected item
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                setTabLayout(1);
                bottomBar.setDefaultTabPosition(0);
                break;
            case R.id.nav_chude:

                break;
            case R.id.nav_ngongu:
                break;
            case R.id.nav_banxemtruoc:
                break;

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}