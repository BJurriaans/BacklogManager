package com.example.bobby.gamesbacklogmanager.Activities;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bobby.gamesbacklogmanager.Dialogs.ConfirmDeleteDialog;
import com.example.bobby.gamesbacklogmanager.Model.Game;
import com.example.bobby.gamesbacklogmanager.R;
import com.example.bobby.gamesbacklogmanager.SQLite.Dao.GameDao;

import java.text.SimpleDateFormat;

public class GameDetailsActivity extends ActionBarActivity implements ConfirmDeleteDialog.ConfirmDeleteDialogListener {

    Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_details);

        TextView title = (TextView) findViewById(R.id.details_value_title);
        TextView platform = (TextView) findViewById(R.id.details_value_platform);
        TextView status = (TextView) findViewById(R.id.details_value_status);
        TextView date = (TextView) findViewById(R.id.details_value_date);
        TextView notes = (TextView) findViewById(R.id.details_value_notes);

        game = (Game) getIntent().getSerializableExtra("selectedGame");
        String dateString = new SimpleDateFormat("dd/MM/yyyy").format(game.getDateAdded());

        title.setText(game.getTitle());
        platform.setText(game.getPlatform());
        status.setText(game.getGameStatus().getStatus());
        date.setText(dateString);
        notes.setText(game.getNotes());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_delete_game){
            DialogFragment dialog = new ConfirmDeleteDialog();
            dialog.show(this.getFragmentManager(), "ConfirmDeleteDialog");
        } else if (id == R.id.action_modify_game){
            Intent intent = new Intent(GameDetailsActivity.this, ModifyGameActivity.class);
            intent.putExtra("currentGame", game);
            startActivity(intent);
        }

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    public void confirmDeleteGame(){

    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        GameDao gameDao = new GameDao(GameDetailsActivity.this);
        gameDao.deleteGame(game);

        showGameDeletedToast();
        Intent intent = new Intent(GameDetailsActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }

    public void showGameDeletedToast(){
        Context context = getApplicationContext();
        String text = getString(R.string.game_deleted);
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}
