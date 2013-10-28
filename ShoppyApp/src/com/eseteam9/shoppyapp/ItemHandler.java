package com.eseteam9.shoppyapp;

import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

public class ItemHandler extends LocalDatabaseHandler{
    public static final String TABLE_NAME = "items";
    
    private static final String KEY_ID = "id";
    private static final String KEY_LIST_ID = "listid";
    private static final String KEY_NAME = "name";
    private static final String KEY_QUANTITY = "quantity";
    private static final String KEY_BOUGHT = "bought";
    private static final String KEY_TIMESTAMP = "timestamp";
    
    public ItemHandler(Context context) {
    	super(context);
    }
    
    public static void createTable(SQLiteDatabase db) {
    	String CREATE_TABLE = "CREATE TABLE "+ TABLE_NAME + "("
    			+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_LIST_ID
    			+ " INTEGER,"+ KEY_NAME + " TEXT," + KEY_QUANTITY + " TEXT,"
    			+ KEY_BOUGHT + " INTEGER," + KEY_TIMESTAMP
    			+ " DATETIME DEFAULT CURRENT_TIMESTAMP" + ")";
    	db.execSQL(CREATE_TABLE);
    }
    
	public static void dropTable(SQLiteDatabase db) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
	}
    
    public void add(Item item) {
    	SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, item.name);
        values.put(KEY_LIST_ID, item.listId);
        values.put(KEY_QUANTITY, item.quantity);
        values.put(KEY_BOUGHT, 0);

        db.insert(TABLE_NAME, null, values);
        db.close();
    }
    
    public void delete(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + "=" + id, null);
        db.close();
    }
    
    public Item[] getListItems(int listId) {
        //List<Item> items = new ArrayList<Item>();
        
        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE " + KEY_LIST_ID + " = " + listId + " ORDER BY " + KEY_BOUGHT;
        
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
     
        Item returnArray[] = new Item[cursor.getCount()];
        
        if (cursor.moveToFirst()) {
        	int i = 0;
            do {
            	returnArray[i] = parseItem(cursor);
            	i++;
            } while (cursor.moveToNext());
        }

        db.close();
        return returnArray;
    }
    
    public void checked(int id, boolean status) {
    	  SQLiteDatabase db = this.getWritableDatabase();
    	  //db.update(TABLE_SHOPPING_LISTS, null, S_KEY_ID+ "="+ id, null);	
    	  SQLiteStatement stmt = db.compileStatement("UPDATE " + TABLE_NAME + " SET " + KEY_BOUGHT + " = " + (status ? 1 : 0) + " WHERE "+ KEY_ID +" = "+ id );
    	  stmt.execute();
    	  db.close();
    }
    
    public int getCount() {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);
        
        int returnInt = cursor.getCount();
        db.close();
        return returnInt;
    }
    
    public int getCountUnbought() {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE " + KEY_BOUGHT + " = " + 0;
        Cursor cursor = db.rawQuery(selectQuery, null);

        int returnInt = cursor.getCount();
        db.close();
        return returnInt;
    }
    
    private Item parseItem(Cursor c) {
    	return new Item(c.getInt(0),
    			c.getInt(1),
    			c.getString(2),
    			c.getString(3),
                c.getInt(4) == 1,
                new Date(c.getLong(5)));
    }
}