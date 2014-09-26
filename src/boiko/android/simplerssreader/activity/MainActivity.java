package boiko.android.simplerssreader.activity;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import boiko.android.simplerssreader.R;
import boiko.android.simplerssreader.asynctask.ReadRssAsyncTask;
import boiko.android.simplerssreader.rss.RssEntriesAdapter;
import boiko.android.simplerssreader.rss.RssEntry;
import boiko.android.simplerssreader.sqlite.SqliteManager;


/*
 * android.support.v4.app.FragmentActivity used for compatibility reasons
 * (TASK: Android 2.3 devices should show modern UI elements like action bar 
 * and fragments the same as Android 4.0+ devices)
 * TODO: different visualization on tablets and phones
 */
/**
 * The MainActivity class stores fragment with all 
 * the RSS entries and "Refresh" button in menu.
 */ 
public class MainActivity extends FragmentActivity {

    private ListView listView;
    private SqliteManager sqlManager = new SqliteManager(this);

    /*
     * One of the tasks is to store the state of the screen when rotating.
     * We can use fragments for it, according to documentation:
     * http://developer.android.com/guide/topics/resources/runtime-changes.html#RetainingAnObject
     * TODO:implement saving fragment on screen rotating
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_layout);
        listView = (ListView) findViewById(R.id.rsslist);
        Log.i("MAIN", "Launching RSS Reader");
        try {
            publishRss();
        } catch (InterruptedException | ExecutionException e) {
            Toast.makeText(this, "Can't load an RSS channel", Toast.LENGTH_LONG).show();
            Log.e("MAIN", "Something went wrong with the Thread");
            e.printStackTrace();
        } catch (TimeoutException e) {
            Toast.makeText(this, "Can't load an RSS channel", Toast.LENGTH_LONG).show();
            Log.e("MAIN", "Attempt to read RSS was too long");
            e.printStackTrace();
        }
    }

    public void publishRss() throws InterruptedException, ExecutionException, TimeoutException {
        ReadRssAsyncTask task = new ReadRssAsyncTask();
        task.execute();
        List<RssEntry> rssEntries = task.get(3, TimeUnit.SECONDS);
        /*
         * We reset our table on each request. I don't really understand if
         * we should store all the RSS entries we ever read, so I use this table
         * just as temporary cache - for example in cases of screen rotation;
         * 
         * For now it just demonstrates the ability of application to work with DB
         */
        sqlManager.resetRssEntriesTable();
        sqlManager.putAllEntries(rssEntries);
        final RssEntriesAdapter adapter = new RssEntriesAdapter(this, sqlManager.getAllEntries());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), DetailedRssEntryActivity.class);
                intent.putExtra("link", adapter.getRssEntry(position).getLink());
                startActivity(intent);
            }
        });
        Log.i("MAIN","Published RSS Contents to fragment");
    }
    
    /*
     * Menu is NOT compatible with API 9
     * I lacked time to implement this. 
     * TODO: Make menu compatible with API 9
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu_items, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void onRefresh(MenuItem item){
        try {
            publishRss();
        } catch (InterruptedException | ExecutionException e) {
            Toast.makeText(this, "Can't load an RSS channel", Toast.LENGTH_LONG).show();
            Log.e("MAIN", "Something went wrong with the Thread");
            e.printStackTrace();
        } catch (TimeoutException e) {
            Toast.makeText(this, "Can't load an RSS channel", Toast.LENGTH_LONG).show();
            Log.e("MAIN", "Attempt to read RSS was too long");
            e.printStackTrace();
        }
    }
}