package edu.temple.browser;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationFragment.OnFragmentInteractionListener{

    ArrayList<WebViewFragment> webFragments = new ArrayList<WebViewFragment>(1);
    FragmentStatePagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pagerAdapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return webFragments.get(position);
            }

            @Override
            public int getCount() {
                return webFragments.size();
            }
        };

        ViewPager vp = (ViewPager) findViewById(R.id.browserFrame);
        vp.setAdapter(pagerAdapter);
//        vp.setOffscreenPageLimit(10);

        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        WebViewFragment webViewFrag = new WebViewFragment();
        Uri data = getIntent().getData();
        Bundle bundle = new Bundle();
        bundle.putString(webViewFrag.URL_FETCHER,
                data != null ? data.toString() : "https://google.com");
        webViewFrag.setArguments(bundle);
        webFragments.add(webViewFrag);
        pagerAdapter.notifyDataSetChanged();
        NavigationFragment navFrag = new NavigationFragment();
        fm.beginTransaction().replace(R.id.navigationFrame, navFrag).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.newButton:
                onNew();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inf = getMenuInflater();
        inf.inflate(R.menu.appbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void onNew() {
        WebViewFragment webViewFrag = new WebViewFragment();
        webFragments.add(webViewFrag);
        pagerAdapter.notifyDataSetChanged();
        ((ViewPager) findViewById(R.id.browserFrame)).setCurrentItem(
                pagerAdapter.getCount()-1);
    }

    @Override
    public void onTextEntered(String url) {
        int curr = ((ViewPager) findViewById(R.id.browserFrame)).getCurrentItem();
        Log.d("HEY", pagerAdapter.getItem(curr).toString());
        ((WebViewFragment) pagerAdapter.getItem(curr)).navigateToUrl(url);
    }
}
