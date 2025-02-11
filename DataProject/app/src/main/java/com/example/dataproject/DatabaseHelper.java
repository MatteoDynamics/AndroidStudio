package com.example.dataproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import org.json.JSONObject;
import java.util.HashMap;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "dataproject.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_HISTORY = "game_history";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_DATE = "game_date";
    private static final String COLUMN_SCORES = "scores";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_HISTORY_TABLE = "CREATE TABLE " + TABLE_HISTORY + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_DATE + " TEXT,"
                + COLUMN_SCORES + " TEXT" + ")";
        db.execSQL(CREATE_HISTORY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY);
        onCreate(db);
    }

    // Metoda zapisująca historię gry do bazy
    public void insertGameHistory(HashMap<Integer, Integer> scores, long gameDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATE, String.valueOf(gameDate));
        try {
            JSONObject jsonObject = new JSONObject(scores);
            values.put(COLUMN_SCORES, jsonObject.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        db.insert(TABLE_HISTORY, null, values);
        db.close();
    }
}
