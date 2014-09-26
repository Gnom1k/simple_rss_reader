package boiko.android.simplerssreader.activity;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import boiko.android.simplerssreader.R;

public class DetailedRssEntryActivity extends Activity {
    private WebView webView;
    /*
     * Sorry for using WebView, although it was not recommended. Lack of time.
     * TODO: get rid of WebView. Use standard tools.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailed_rss_layout);
        String extra = (String) getIntent().getExtras().get("link");
        
        webView = (WebView) findViewById(R.id.fullDescWebView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(extra);
    }

}
