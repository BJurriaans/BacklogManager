package com.example.bobby.gamesbacklogmanager.Activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import java.util.ArrayList;
import java.util.Date;

import com.example.bobby.gamesbacklogmanager.Adapters.GameListItemAdapter;
import com.example.bobby.gamesbacklogmanager.Model.Game;
import com.example.bobby.gamesbacklogmanager.Model.GameStatus;
import com.example.bobby.gamesbacklogmanager.R;


public class MainActivity extends ActionBarActivity {
    ArrayList<Game> gameArray = new ArrayList<>();
    //will hardcode games until we're gonna work with localstorage
    Game game1 = new Game(1, "Monster Hunter 4 (hardcoded)", "3DS", new Date(), GameStatus.PLAYING, "This detail page is hardcoded for now, I will make it dynamic when we're going to work with localstorage.");
    Game game2 = new Game(2, "Heroes of the Storm (hardcoded)", "PC", new Date(), GameStatus.WANT_TO_PLAY, "Betakey pls");

    ListView gameListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameListView = (ListView) findViewById(R.id.gameList);
        gameArray.add(game1);
        gameArray.add(game2);
    
        gameListView.setAdapter(new GameListItemAdapter(gameArray, this));

        gameListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, GameDetailsActivity.class);
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

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }
}
