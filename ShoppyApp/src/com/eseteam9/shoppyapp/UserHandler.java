package com.eseteam9.shoppyapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UserHandler extends AbstractHandler{
    public static final String TABLE_NAME = "users";
    
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_KEY = "key";
	
	public static void onCreate(SQLiteDatabase db) {
		String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT,"
				+ KEY_KEY + " TEXT" + ")";
		db.execSQL(CREATE_TABLE);
	}
	
	public static void dropTable(SQLiteDatabase db) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
	}
	
    public static void add(User user) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, user.name);
        values.put(KEY_KEY, user.key);

        db.insert(TABLE_NAME, null, values);
        db.close();
    }
    
    public static User get(int id) {
        SQLiteDatabase db = dbHandler.getReadableDatabase();
 
        Cursor cursor = db.query(TABLE_NAME, new String[] { KEY_ID,
                KEY_NAME, KEY_KEY }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();
 
        User user = new User(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));

        db.close();
        return user;
    }
}