package boiko.android.simplerssreader.rss;

import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
  
public class RssReader {  
	
    private ArrayList<RssEntry> rssEntries = new ArrayList<>();
  
    private static RssReader instance = null;  
  
    private URL rssURL;  
  
    public RssReader() {}  
  
    public static RssReader getInstance() {
       if (instance == null)
          instance = new RssReader();
       return instance;
    }
  
    public void setURL(URL url) {
       rssURL = url;
    }

    public ArrayList<RssEntry> readFeed() {
       try {
          DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
          Document doc = builder.parse(rssURL.openStream());

          NodeList items = doc.getElementsByTagName("item");

          for (int i = 0; i < items.getLength(); i++) {
             Element item = (Element)items.item(i);
             RssEntry entry = new RssEntry(
             getValue(item, "title"),
             getValue(item, "pubDate"),
             getValue(item, "description"),
             getValue(item, "link"));
             rssEntries.add(entry);
          }
       } catch (Exception e) {
          e.printStackTrace();  
       }
       return rssEntries;
    }

    public String getValue(Element parent, String nodeName) {
       return parent.getElementsByTagName(nodeName).item(0).getFirstChild().getNodeValue();
    }
}