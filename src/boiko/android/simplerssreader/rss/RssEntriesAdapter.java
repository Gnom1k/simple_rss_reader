package boiko.android.simplerssreader.rss;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import boiko.android.simplerssreader.R;

public class RssEntriesAdapter extends BaseAdapter {
	
    private final List<RssEntry> entries;
    private final LayoutInflater inflator;
 
    public RssEntriesAdapter(Context context, List<RssEntry> entries) {
        this.entries = entries;
        inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

	@Override
	public int getCount() {
		return entries.size();
	}

	@Override
	public Object getItem(int position) {
		return entries.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null){
			view = inflator.inflate(R.layout.rss_entry_layout, parent, false);
		}
		TextView title = 		(TextView) view.findViewById(R.id.rss_title);
		TextView date = 		(TextView) view.findViewById(R.id.rss_date);
		TextView description =	(TextView) view.findViewById(R.id.rss_description);
		TextView link = 		(TextView) view.findViewById(R.id.rss_link);
		
		title.setText(getRssEntry(position).getTitle());
		date.setText(getRssEntry(position).getPubDate());
		description.setText(getRssEntry(position).getDescription());
		link.setText("Read more >>");
		
		return view;
	}
	
	public RssEntry getRssEntry(int position){
		return (RssEntry) getItem(position);
	}
}
