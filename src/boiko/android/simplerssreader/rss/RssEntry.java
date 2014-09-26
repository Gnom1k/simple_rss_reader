package boiko.android.simplerssreader.rss;

public class RssEntry {

    private int id;
	private String title;
	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String pubDate;
	private String description;
	private String link;
	
	public RssEntry(String title, String pubDate, String description, String link) {
		this.title = title;
		this.pubDate = pubDate;
		this.description = description;
		this.link = link;
	}

	public RssEntry() {}

    public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPubDate() {
		return pubDate;
	}

	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	@Override
	public String toString() {
		return "RssEntry [title=" + title + ", pubDate=" + pubDate
				+ ", description=" + description + ", link=" + link + "]";
	}
	
	
}
