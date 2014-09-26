package boiko.android.simplerssreader.sqlite;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import boiko.android.simplerssreader.rss.RssEntry;

public class SqliteManager extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "RssEntryDb";
    private static final String TABLE_NAME = "rssentries";
    private static final String[] ALL_COLUMNS = {"id","title","pubDate","description","link"};
    
    String CREATE_RSSENTRIES_TABLE = "CREATE TABLE rssentries ( " +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "title TEXT, "+
            "pubDate TEXT,"+
            "description TEXT,"+
            "link TEXT )";
    
    public SqliteManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_RSSENTRIES_TABLE);
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS rssentries");
        this.onCreate(db);
    }
    
    public void resetRssEntriesTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS rssentries");
        db.execSQL(CREATE_RSSENTRIES_TABLE);
    }
    
    public void putAllEntries(List<RssEntry> list) {
        SQLiteDatabase db = this.getWritableDatabase();
        for (RssEntry entry : list){
            ContentValues values = new ContentValues();
            values.put("title", entry.getTitle());
            values.put("pubDate", entry.getPubDate());
            values.put("description", entry.getDescription());
            values.put("link", entry.getLink());  
            db.insert(TABLE_NAME, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values
        }
        db.close();
    }
    
    public List<RssEntry> getAllEntries() {
        List<RssEntry> entries = new ArrayList<RssEntry>();
        String query = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        RssEntry entry = null;
        if (cursor.moveToFirst()) {
            do {
                entry = new RssEntry();
                entry.setId(Integer.parseInt(cursor.getString(0)));
                entry.setTitle(cursor.getString(1));
                entry.setPubDate(cursor.getString(2));
                entry.setDescription(cursor.getString(3));
                entry.setLink(cursor.getString(4));
                entries.add(entry);
            } while (cursor.moveToNext());
        }
        db.close();
        return entries;
    }
    
    //Won't use this methods, but left it here in case of improving code
    public void addRssEntry(RssEntry rssEntry){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", rssEntry.getTitle());
        values.put("pubDate", rssEntry.getPubDate());
        values.put("description", rssEntry.getDescription());
        values.put("link", rssEntry.getLink());
        
        db.insert(TABLE_NAME, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values
        db.close(); 
    }
    
    public RssEntry getRssEntry(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =
                db.query(TABLE_NAME, // a. table
                ALL_COLUMNS, // b. column names
                " id = ?", // c. selections
                new String[] { String.valueOf(id) }, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
     
        // if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();
        
        RssEntry entry = new RssEntry();
        entry.setId(Integer.parseInt(cursor.getString(0)));
        entry.setTitle(cursor.getString(1));
        entry.setPubDate(cursor.getString(2));
        entry.setDescription(cursor.getString(3));
        entry.setLink(cursor.getString(4));
        db.close();
        return entry;
    }

}
