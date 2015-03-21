package com.example.bobby.gamesbacklogmanager.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.bobby.gamesbacklogmanager.Model.Game;

/**
 * Created by Bobby on 21-3-2015.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "BacklogManager.db";
    public static final String GAME_TABLE_NAME = "game";
    public static final String GAME_COLUMN_ID = "id";
    public static final String GAME_COLUMN_TITLE = "title";
    public static final String GAME_COLUMN_PLATFORM = "platform";
    public static final String GAME_COLUMN_STATUS = "status";
    public static final String GAME_COLUMN_DATE_ADDED = "date_added";
    public static final String GAME_COLUMN_NOTES = "notes";

    private static final String CREATE_TABLE_GAME = "CREATE TABLE " + GAME_TABLE_NAME +
            "(" +
            GAME_COLUMN_ID + " integer primary key autoincrement, " +
            GAME_COLUMN_TITLE + " text not null, " +
            GAME_COLUMN_PLATFORM + " text not null, " +
            GAME_COLUMN_STATUS + " text not null, " +
            GAME_COLUMN_DATE_ADDED + " text not null, " +
            GAME_COLUMN_NOTES + " text" +
            ");";

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_GAME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + GAME_TABLE_NAME);
        onCreate(db);
    }
}
