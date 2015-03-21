package com.example.bobby.gamesbacklogmanager.Activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import com.example.bobby.gamesbacklogmanager.Adapters.GameListItemAdapter;
import com.example.bobby.gamesbacklogmanager.Model.Game;
import com.example.bobby.gamesbacklogmanager.R;
import com.example.bobby.gamesbacklogmanager.SQLite.DBHelper;
import com.example.bobby.gamesbacklogmanager.SQLite.Dao.GameDao;


public class MainActivity extends ActionBarActivity {
    ArrayList<Game> games = new ArrayList<>();

    //will hardcode games until we're gonna work with localstorage
    Game game1;
    Game game2;
    Date curDate;

    ListView gameListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        DBHelper dbHelper = new DBHelper(MainActivity.this);
//        dbHelper.onCreate(dbHelper.getWritableDatabase());
//        GameDao gameDao = new GameDao(MainActivity.this);
//        gameDao.deleteGame(null);

        gameListView = (ListView) findViewById(R.id.gameList);
        games = getGames();

//        game1 = new Game(1, "Monster Hunter 4 (hardcoded)", "3DS", curDate, GameStatus.PLAYING, "This detail page is hardcoded for now, I will make it dynamic when we're going to work with localstorage.");
//        game2 = new Game(2, "Heroes of the Storm (hardcoded)", "PC", curDate, GameStatus.WANT_TO_PLAY, "Betakey pls");
//        games.add(game1);
//        games.add(game2);
    
        gameListView.setAdapter(new GameListItemAdapter(games, this));

        gameListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, GameDetailsActivity.class);
                Game selectedGame = (Game) parent.getAdapter().getItem(position);
                intent.putExtra("selectedGame", selectedGame);
                startActivity(intent);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_pick_random_game){
            pickRandomGame();
        } else if (id == R.id.action_add_game){
            Intent intent = new Intent(MainActivity.this, AddGameActivity.class);
            startActivity(intent);
        }

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    private ArrayList<Game> getGames(){
        GameDao gameDao = new GameDao(MainActivity.this);
        ArrayList<Game> gameArrayList = gameDao.getGames();
        return gameArrayList;
    }

    private void pickRandomGame(){
        Random rand = new Random();
        int randomNum = rand.nextInt(games.size());
        Game selectedGame = games.get(randomNum);

        Intent intent = new Intent(MainActivity.this, GameDetailsActivity.class);
        intent.putExtra("selectedGame",selectedGame);
        startActivity(intent);
    }

    private Date getSimpleCurrentDate(){
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date today = new Date();
        try
        {
            curDate = format.parse(format.format(today));
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        return curDate;
    }

}
