package edu.temple.browser;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;


/**
 * A simple {@link Fragment} subclass.
 */
public class WebViewFragment extends Fragment {

    public final String URL_FETCHER = "ejfwoijoiejfaeoiwjf";
    private final String URL_SAVER = "greasgregs";
    private final String FLAG_SAVER = "dagergarga";
    private String oldUrl = "https://www.google.com";
    private boolean readArguments = false;

    public WebViewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_web_view, container, false);
        readArguments = savedInstanceState != null ? savedInstanceState.getBoolean(FLAG_SAVER, false)
                : readArguments;
        if (getArguments() != null && !readArguments) {
            ((WebView) v.findViewById(R.id.mainWebView)).setWebViewClient(new WebViewClient());
            oldUrl = getArguments().getString(URL_FETCHER);
            readArguments = true;
        } else if (savedInstanceState != null) {
            oldUrl = savedInstanceState.getString(URL_SAVER);
            ((WebView) v.findViewById(R.id.mainWebView)).setWebViewClient(new WebViewClient());
            Log.d("very interesting", oldUrl);
        }
        ((WebView) v.findViewById(R.id.mainWebView)).loadUrl(oldUrl);
        return v;
    }

    public void navigateToUrl(String url) {
        try {
            if (!url.contains("http://") && !url.contains("https://"))
                url = "https://" + url;
            WebView wv = (WebView) getView().findViewById(R.id.mainWebView);
            oldUrl = url;
            wv.setWebViewClient(new WebViewClient());
            wv.loadUrl(url);
        } catch (Exception e) {
            Log.e("Hey", e.toString());
        }
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        Log.d("Saved", "---------------------------");
        bundle.putString(URL_SAVER, oldUrl);
        bundle.putBoolean(FLAG_SAVER, readArguments);
    }


}
