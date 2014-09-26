package boiko.android.simplerssreader.asynctask;

import java.net.URL;
import java.util.List;

import android.os.AsyncTask;
import boiko.android.simplerssreader.rss.RssEntry;
import boiko.android.simplerssreader.rss.RssReader;

public class ReadRssAsyncTask extends AsyncTask<Void, Void, List<RssEntry>> {

	@Override
	protected List<RssEntry> doInBackground(Void... params) {
		List<RssEntry> entriesInAsync = null;
		try {
			RssReader reader = RssReader.getInstance();
			reader.setURL(new URL("http://www.npr.org/rss/rss.php?id=1006"));
			entriesInAsync = reader.readFeed();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entriesInAsync;
	}

}
