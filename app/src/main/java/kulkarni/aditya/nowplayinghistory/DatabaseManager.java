package kulkarni.aditya.nowplayinghistory;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by maverick on 3/11/18.
 */

public class DatabaseManager extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "NowPlaying";

    private static final String CREATE_NOW_PLAYING_TABLE = "CREATE TABLE " + Constants.NOW_PLAYING_TABLE
            + " (" + Constants.SONG_TITLE + " VARCHAR )";

    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_NOW_PLAYING_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertSong(HashMap<String, String> map) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Constants.NOW_PLAYING_TABLE + " WHERE " + Constants.SONG_TITLE + " LIKE '" + map.get(Constants.SONG_TITLE) + "'", null);
        int flag = 0;
        if (cursor.getCount() > 0) {
            flag = 1;
        }
        ContentValues values = new ContentValues();
        values.put(Constants.SONG_TITLE, map.get(Constants.SONG_TITLE));

        if (flag == 0) {
            db.insert(Constants.NOW_PLAYING_TABLE, null, values);
        }
        cursor.close();
    }

    public ArrayList<String> getAllSongs() {
        ArrayList<String> messagesList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(Constants.NOW_PLAYING_TABLE, new String[]{"*"}, null, null, null, null, null);
        for (cursor.moveToLast(); !cursor.isBeforeFirst(); cursor.moveToPrevious()) {
            messagesList.add(cursor.getString(cursor.getColumnIndex(Constants.SONG_TITLE)));
        }
        cursor.close();
        db.close();
        return messagesList;
    }
}
