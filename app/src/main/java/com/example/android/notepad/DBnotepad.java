package com.example.android.notepad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBnotepad extends SQLiteOpenHelper {

    private static final String DB_NAME = "myNotepad_DB";
    private static final int DB_VERSION = 1;

    private static final String TABLE_NOTE = "notes";

    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_CONTENT = "content";

    public DBnotepad(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String create_table = "CREATE TABLE " + TABLE_NOTE + " ( "
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_TITLE + " TEXT NOT NULL, "
                + KEY_CONTENT + " TEXT NOT NULL)";

        db.execSQL(create_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String delete_query = "DROP TABLE " + TABLE_NOTE + " IF EXITS";
        db.execSQL(delete_query);

        onCreate(db);

    }

    public void addItem(Item item) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, item.getTitle());
        values.put(KEY_CONTENT, item.getContent());

        db.insert(TABLE_NOTE, null, values);
    }

    public ArrayList<Item> getAllNotes() {
        ArrayList<Item> items = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String selection_query = "SELECT * FROM " + TABLE_NOTE;

        Cursor cursor = db.rawQuery(selection_query, null);

        if (cursor.moveToFirst()) {

            do {
                int id = cursor.getInt(cursor.getColumnIndex(KEY_ID));
                String title = cursor.getString(cursor.getColumnIndex(KEY_TITLE));
                String content = cursor.getString(cursor.getColumnIndex(KEY_CONTENT));

                Item item = new Item(id, title, content);

                items.add(item);

            } while (cursor.moveToNext());
        }

        return items;
    }

    public Item getNoteById(int id) {
        Item item = null;

        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {KEY_ID, KEY_TITLE, KEY_CONTENT};
        String selection = KEY_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};

        Cursor cursor = db.query(TABLE_NOTE, projection, selection, selectionArgs,
                null, null, null);
        if (cursor.moveToFirst()) {

            int id_note = cursor.getInt(cursor.getColumnIndex(KEY_ID));
            String title = cursor.getString(cursor.getColumnIndex(KEY_TITLE));
            String content = cursor.getString(cursor.getColumnIndex(KEY_CONTENT));

            item = new Item(id_note, title, content);

        }

        return item;
    }

    public void updateNote(Item item) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, item.getTitle());
        values.put(KEY_CONTENT, item.getContent());

        String selection = KEY_ID + " = ?";
        String[] selectionArgs = {String.valueOf(item.getId())};

        db.update(TABLE_NOTE, values, selection, selectionArgs);

    }

    public void deleteNote(int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        String selection = KEY_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};

        db.delete(TABLE_NOTE, selection, selectionArgs);
    }

    public void deleteAllNotes() {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NOTE, null, null);
    }

}
