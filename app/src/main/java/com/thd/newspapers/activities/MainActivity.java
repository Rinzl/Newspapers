package com.thd.newspapers.activities;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.thd.newspapers.R;
import com.thd.newspapers.adapter.NewsAdapter;
import com.thd.newspapers.model.News;
import com.thd.newspapers.utils.CrawlerAsyncTask;
import com.thd.newspapers.utils.GlideApp;

import java.util.ArrayList;
import java.util.Currency;

public class MainActivity extends AppCompatActivity
        implements MaterialSearchView.OnQueryTextListener,
        CrawlerAsyncTask.OnCompleteTask,
        SwipeRefreshLayout.OnRefreshListener,
        Drawer.OnDrawerItemClickListener{

    // variable declaration
    public static String INTENT_WEBVIEW_KEY = "IntentWebViewKey";
    public static String INTENT_TITLE_KEY = "TITLE_KEY";
    public static String HOME_URL = "tin-moi.epi";
    public static String THEGIOI_URL = "the-gioi.epi";
    public static String VANHOA_URL = "van-hoa.epi";
    public static String KINHTE_URL = "kinh-te.epi";
    public static String GIAODUC_URL = "giao-duc.epi";
    public static String THETHAO_URL = "the-thao.epi";
    public static String SUCKHOE_URL = "doi-song.epi";
    public static String GIAITRI_URL = "giai-tri.epi";
    public static String XAHOI_URL = "xa-hoi.epi";

    private RecyclerView rvNews;
    private TextView tvTitle;
    private Toolbar toolbar;
    private MaterialSearchView searchView;
    private SwipeRefreshLayout refreshLayout;
    private CrawlerAsyncTask asyncTask;
    private Drawer result;
    private AccountHeader header;
    private String currentURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);
        init();
        currentURL = HOME_URL;
        final IProfile profile = new ProfileDrawerItem().withName("Mike Penz").withEmail("mikepenz@gmail.com").withIcon(R.drawable.user);
        header = new AccountHeaderBuilder()
                .withActivity(this)
                .withCompactStyle(true)
                .withHeaderBackground(R.drawable.bg)
                .addProfiles(profile)
                .withSavedInstance(savedInstanceState)
                .build();
        result =new DrawerBuilder()
                .withActivity(this)
                .withAccountHeader(header)
                .withToolbar(toolbar)
                .withActionBarDrawerToggle(true)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("Trang Chủ").withIdentifier(1).withIcon(FontAwesome.Icon.faw_home),
                        new PrimaryDrawerItem().withName("Xã Hội").withIcon(FontAwesome.Icon.faw_thumbs_up),
                        new PrimaryDrawerItem().withName("Thế Giới").withIcon(FontAwesome.Icon.faw_globe),
                        new PrimaryDrawerItem().withName("Kinh Tế").withIcon(FontAwesome.Icon.faw_dollar_sign),
                        new PrimaryDrawerItem().withName("Văn Hóa").withIcon(FontAwesome.Icon.faw_university),
                        new PrimaryDrawerItem().withName("Giáo Dục").withIcon(FontAwesome.Icon.faw_graduation_cap),
                        new PrimaryDrawerItem().withName("Thể Thao").withIcon(FontAwesome.Icon.faw_football_ball),
                        new PrimaryDrawerItem().withName("Đời Sống").withIcon(FontAwesome.Icon.faw_stethoscope),
                        new PrimaryDrawerItem().withName("Giải Trí").withIcon(FontAwesome.Icon.faw_gamepad),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName("Cài Đặt").withIcon(FontAwesome.Icon.faw_cog),
                        new PrimaryDrawerItem().withName("Trợ Giúp").withIcon(FontAwesome.Icon.faw_magic),
                        new PrimaryDrawerItem().withName("About").withIcon(FontAwesome.Icon.faw_info_circle)
                )
                .withOnDrawerItemClickListener(this)
                .withSavedInstance(savedInstanceState)
                .build();
        tvTitle.setText(R.string.newspapers);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(this);
        asyncTask = new CrawlerAsyncTask();
        asyncTask.setOnCompleteTask(this);
        asyncTask.execute(HOME_URL);
        refreshLayout.setOnRefreshListener(this);
        if (savedInstanceState == null ) {
            refreshLayout.setRefreshing(true);
        }
        refreshLayout.setColorSchemeResources(R.color.refresh_color_1, R.color.refresh_color_2, R.color.refresh_color_3);
    }

    private void init() {
        rvNews = findViewById(R.id.rvNews);
        tvTitle = toolbar.findViewById(R.id.tvTitle);
        searchView = findViewById(R.id.mysearch);
        refreshLayout = findViewById(R.id.refreshLayout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.actionSearch);
        searchView.setMenuItem(item);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) searchView.closeSearch();
        else if (result.isDrawerOpen()) result.closeDrawer();
        else super.onBackPressed();
    }

    @Override
    public void onComplete(final ArrayList<News> newsList) {
        NewsAdapter adapter = new NewsAdapter(newsList, MainActivity.this);
        rvNews.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        rvNews.setItemAnimator(new DefaultItemAnimator());

        adapter.setListener(new NewsAdapter.OnclickListener() {
            @Override
            public void onClick(int position) {
//                Intent intent = new Intent(MainActivity.this,WebViewActivity.class);
//                intent.putExtra(INTENT_WEBVIEW_KEY, newsList.get(position).getNewsUrl());
//                intent.putExtra(INTENT_TITLE_KEY, newsList.get(position).getSource());
//                startActivity(intent);
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                builder.setToolbarColor(getResources().getColor(R.color.primary));
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(MainActivity.this, Uri.parse(newsList.get(position).getNewsUrl()));
            }
        });
        rvNews.setAdapter(adapter);
        if(refreshLayout.isRefreshing()) refreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        asyncTask = new CrawlerAsyncTask();
        asyncTask.setOnCompleteTask(MainActivity.this);
        asyncTask.execute(currentURL);
    }

    @Override
    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
        asyncTask = new CrawlerAsyncTask();
        asyncTask.setOnCompleteTask(MainActivity.this);
        switch (position) {
            case 1 : {
                asyncTask.execute(HOME_URL);
                refreshLayout.setRefreshing(true);
                currentURL = HOME_URL;
                tvTitle.setText(R.string.home);
                break;
            }
            case 2 : {
                asyncTask.execute(XAHOI_URL);
                refreshLayout.setRefreshing(true);
                currentURL = XAHOI_URL;
                tvTitle.setText("Xã Hội");
                break;
            }
            case 3 : {
                asyncTask.execute(THEGIOI_URL);
                refreshLayout.setRefreshing(true);
                currentURL = THEGIOI_URL;
                break;
            }
            case 4 : {
                asyncTask.execute(KINHTE_URL);
                refreshLayout.setRefreshing(true);
                currentURL = KINHTE_URL;
                tvTitle.setText(R.string.xa_hoi);

                break;
            }
            case 5 : {
                asyncTask.execute(VANHOA_URL);
                refreshLayout.setRefreshing(true);
                currentURL = VANHOA_URL;
                tvTitle.setText("Văn Hóa");
                break;
            }
            case 6 : {
                asyncTask.execute(GIAODUC_URL);
                refreshLayout.setRefreshing(true);
                currentURL = GIAODUC_URL;
                tvTitle.setText(R.string.giao_duc);
                break;
            }
            case 7 : {
                asyncTask.execute(THETHAO_URL);
                refreshLayout.setRefreshing(true);
                currentURL = THETHAO_URL;
                tvTitle.setText(R.string.the_thao);
                break;
            }
            case 8 : {
                asyncTask.execute(SUCKHOE_URL);
                refreshLayout.setRefreshing(true);
                currentURL = SUCKHOE_URL;
                tvTitle.setText(R.string.suc_khoe);
                break;
            }
            case 9 : {
                asyncTask.execute(GIAITRI_URL);
                refreshLayout.setRefreshing(true);
                currentURL = GIAITRI_URL;
                tvTitle.setText(R.string.giai_tri);
                break;
            }
            default: {
                break;
            }
        }
        return false;
    }
}
