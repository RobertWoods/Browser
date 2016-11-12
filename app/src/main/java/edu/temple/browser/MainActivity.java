package edu.temple.browser;

import android.app.Activity;
import android.app.FragmentManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toolbar;
import android.webkit.WebView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity implements NavigationFragment.OnFragmentInteractionListener{

    ArrayList<WebViewFragment> webFragments = new ArrayList<WebViewFragment>(1);
    int currentWebFragment = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setActionBar(toolbar);
    
        FragmentManager fm = getFragmentManager();
        WebViewFragment webViewFrag = new WebViewFragment();
        Uri data = getIntent().getData();
        Bundle bundle = new Bundle();
        bundle.putString(webViewFrag.URL_FETCHER,
                data != null ? data.toString() : "https://google.com");
        webFragments.add(webViewFrag);
        webViewFrag.setArguments(bundle);
        NavigationFragment navFrag = new NavigationFragment();
        fm.beginTransaction().replace(R.id.browserFrame, webViewFrag)
                .replace(R.id.navigationFrame, navFrag).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.newButton:
                onNew();
                break;
            case R.id.nextButton:
                onNext();
                break;
            case R.id.prevButton:
                onPrev();
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
        return true;
    }

    public void onNext() {
        try {
            WebViewFragment webViewFrag = webFragments.get(currentWebFragment+1);
            currentWebFragment++;
            getFragmentManager().beginTransaction().hide(webFragments.get(currentWebFragment-1))
                    .show(webViewFrag)
                    .commit();
        } catch(IndexOutOfBoundsException e) {
            Toast.makeText(this, "No more pages", Toast.LENGTH_SHORT).show();
        }
    }

    public void onPrev() {
        try {
            WebViewFragment webViewFrag = webFragments.get(currentWebFragment-1);
            currentWebFragment--;
            getFragmentManager().beginTransaction().hide(webFragments.get(currentWebFragment+1))
                    .show(webViewFrag)
                    .commit();
        } catch(IndexOutOfBoundsException e) {
            Toast.makeText(this, "No more pages", Toast.LENGTH_SHORT).show();
        }
    }

    public void onNew() {
        WebViewFragment webViewFrag = new WebViewFragment();
        webFragments.add(webViewFrag);
        int oldWebFragment = currentWebFragment;
        currentWebFragment = webFragments.size() - 1;
        getFragmentManager().beginTransaction().hide(webFragments.get(oldWebFragment)).
                add(R.id.browserFrame, webViewFrag)
                .commit();
    }

    @Override
    public void onTextEntered(String url) {
        webFragments.get(currentWebFragment).navigateToUrl(url);
    }
}
