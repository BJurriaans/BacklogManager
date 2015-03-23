package com.example.bobby.gamesbacklogmanager.SQLite.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;

import com.example.bobby.gamesbacklogmanager.Model.Game;
import com.example.bobby.gamesbacklogmanager.Model.GameStatus;
import com.example.bobby.gamesbacklogmanager.SQLite.DBHelper;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Bobby on 21-3-2015.
 */
public class GameDao {
//Note from Bobby: I'm not going to provide a public open and close method because I think that the activity layer should not access the database layer.
//Accessing methods from the Dao layer is ok of course.

    private SQLiteDatabase db;
    private DBHelper dbHelper;

    public GameDao(Context context){
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
        dbHelper.close();
    }

    public ArrayList<Game> getGames(){
        if(!db.isOpen()) {
            db = dbHelper.getWritableDatabase();
        }

        ArrayList<Game> games = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DBHelper.GAME_TABLE_NAME, null);

        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            Game curGame = cursorToGame(cursor);
            games.add(curGame);
            cursor.moveToNext();
        }

        cursor.close();

        if (db.isOpen()) {
            dbHelper.close();
        }

        return games;
    }

    public void insertGame(Game game){

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = format.format(game.getDateAdded());

        if(!db.isOpen()) {
            db = dbHelper.getWritableDatabase();
        }
        ContentValues values = new ContentValues();

        values.put(DBHelper.GAME_COLUMN_TITLE, game.getTitle());
        values.put(DBHelper.GAME_COLUMN_PLATFORM, game.getPlatform());
        values.put(DBHelper.GAME_COLUMN_STATUS, game.getGameStatus().getStatus());
        values.put(DBHelper.GAME_COLUMN_DATE_ADDED, dateString);
        values.put(DBHelper.GAME_COLUMN_NOTES, game.getNotes());

        long insertId = db.insert(DBHelper.GAME_TABLE_NAME, null, values);

        if (db.isOpen()) {
            dbHelper.close();
        }

    }

    public void updateGame(Game game){
        if(!db.isOpen()) {
            db = dbHelper.getWritableDatabase();
        }

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = format.format(game.getDateAdded());

        ContentValues values = new ContentValues();
        values.put(DBHelper.GAME_COLUMN_TITLE, game.getTitle());
        values.put(DBHelper.GAME_COLUMN_PLATFORM, game.getPlatform());
        values.put(DBHelper.GAME_COLUMN_STATUS, game.getGameStatus().getStatus());
        values.put(DBHelper.GAME_COLUMN_DATE_ADDED, dateString);
        values.put(DBHelper.GAME_COLUMN_NOTES, game.getNotes());

        db.update(DBHelper.GAME_TABLE_NAME, values, DBHelper.GAME_COLUMN_ID + " =?", new String[]{Long.toString(game.getId())});

        if (db.isOpen()) {
            dbHelper.close();
        }
    }

    public void deleteGame(Game game){
        if (!db.isOpen()){
            db = dbHelper.getWritableDatabase();
        }

        db.delete(DBHelper.GAME_TABLE_NAME, DBHelper.GAME_COLUMN_ID + " =?", new String[] {Long.toString(game.getId())});
        //db.delete(DBHelper.GAME_TABLE_NAME, DBHelper.GAME_COLUMN_ID + " =?", new String[] {"1","2","3","4","5","6","7","8"});
        //db.execSQL("DROP TABLE game");

        if(db.isOpen()){
            dbHelper.close();
        }
    }

    private Game cursorToGame(Cursor cursor){
        Game game = new Game();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        try{
            game.setId(cursor.getLong(cursor.getColumnIndexOrThrow(DBHelper.GAME_COLUMN_ID)));
            game.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.GAME_COLUMN_TITLE)));
            game.setPlatform(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.GAME_COLUMN_PLATFORM)));

            String dateString = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.GAME_COLUMN_DATE_ADDED));
            //Date date = format.parse(dateString);
            game.setGameStatus(GameStatus.getGameStatusByValue(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.GAME_COLUMN_STATUS))));
            game.setDateAdded(format.parse(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.GAME_COLUMN_DATE_ADDED))));
            game.setNotes(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.GAME_COLUMN_NOTES)));

            return game;
        } catch (ParseException ex) {
            ex.printStackTrace();
            return null;
        } catch (CursorIndexOutOfBoundsException ex){
            ex.printStackTrace();
            return null;
        }
    }
}
