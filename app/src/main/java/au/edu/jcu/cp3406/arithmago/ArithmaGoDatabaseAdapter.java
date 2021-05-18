package au.edu.jcu.cp3406.arithmago;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class ArithmaGoDatabaseAdapter {
    myDbHelper dbHelper;

    public ArithmaGoDatabaseAdapter(Context context) {
        dbHelper = new myDbHelper(context);
    }

    public void insertData(String username, int score, String level) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues leaderboardRecord = new ContentValues();
        leaderboardRecord.put("USERNAME", username);
        leaderboardRecord.put("SCORE", score);
        leaderboardRecord.put("LEVEL", level);
        database.insert(myDbHelper.TABLE_NAME, null, leaderboardRecord);
    }

    public Leaderboard getRecords(String selectedLevel) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        String[] columns = {myDbHelper.RECORD_ID, myDbHelper.USERNAME, myDbHelper.SCORE, myDbHelper.LEVEL};
        String[] levelToSortBy = {selectedLevel};
        Cursor cursor = database.query(myDbHelper.TABLE_NAME, columns, myDbHelper.LEVEL + " =?", levelToSortBy, null, null, myDbHelper.SCORE + " DESC ," + myDbHelper.USERNAME);
        StringBuilder stringBuilder = new StringBuilder();
        Leaderboard leaderboard = new Leaderboard();
        while (cursor.moveToNext()) {
            String username = cursor.getString(cursor.getColumnIndex(myDbHelper.USERNAME));
            int score = cursor.getInt(cursor.getColumnIndex(myDbHelper.SCORE));
            String level = cursor.getString(cursor.getColumnIndex(myDbHelper.LEVEL));
            Record record = new Record(username, score, level);
            leaderboard.addRecord(record);
        }
        cursor.close();
        return leaderboard;
    }

    static class myDbHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "arithmago_test_01";
        private static final String TABLE_NAME = "SCORES";
        private static final int DATABASE_Version = 1;
        private static final String RECORD_ID = "_id";
        private static final String USERNAME = "Username";
        private static final String SCORE = "Score";
        private static final String LEVEL = "Level";
        private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + RECORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + USERNAME + " VARCHAR(255) ," + SCORE + " INTEGER ," + LEVEL + " VARCHAR(225));";
        private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
        private final Context context;

        public myDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_Version);
            this.context = context;
        }

        public void onCreate(SQLiteDatabase database) {

            try {
                database.execSQL(CREATE_TABLE);
            } catch (Exception e) {
                Toast.makeText(context, "" + e, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                Message.message(context, "OnUpgrade");
                db.execSQL(DROP_TABLE);
                onCreate(db);
            } catch (Exception e) {
                Toast.makeText(context, "" + e, Toast.LENGTH_SHORT).show();
            }
        }
    }
}