package edu.temple.browser;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class WebViewFragment extends Fragment {

    public final String URL_FETCHER = "ejfwoijoiejfaeoiwjf";

    public WebViewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v =  inflater.inflate(R.layout.fragment_web_view, container, false);
        if(getArguments() != null && getArguments().getString(URL_FETCHER)!=null) {
            String url = getArguments().getString(URL_FETCHER);
            ((WebView) v.findViewById(R.id.mainWebView)).setWebViewClient(new WebViewClient());
            if(!url.toLowerCase().contains("http://")&&!url.toLowerCase().contains("https://"))
                url = "https://" + url ;
            ((WebView) v.findViewById(R.id.mainWebView)).loadUrl(url);
        } else {
            ((WebView) v.findViewById(R.id.mainWebView)).loadUrl("https://www.google.com");
        }
        return v;
    }

    public void navigateToUrl(String url){
        try {
            if (!url.contains("http://") && !url.contains("https://"))
                url = "https://" + url;
            WebView wv = (WebView) getView().findViewById(R.id.mainWebView);
            wv.setWebViewClient(new WebViewClient());
            wv.loadUrl(url);
        } catch (Exception e){
            Log.e("Hey", e.toString());
        }
    }

}
