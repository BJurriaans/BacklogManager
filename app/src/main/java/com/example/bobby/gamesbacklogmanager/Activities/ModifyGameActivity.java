package com.example.bobby.gamesbacklogmanager.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bobby.gamesbacklogmanager.Model.Game;
import com.example.bobby.gamesbacklogmanager.Model.GameStatus;
import com.example.bobby.gamesbacklogmanager.R;
import com.example.bobby.gamesbacklogmanager.SQLite.Dao.GameDao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ModifyGameActivity extends ActionBarActivity {

    EditText titleInput;
    EditText platformInput;
    Spinner statusSpinner;
    EditText notesInput;
    Button modifyButton;

    Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_game);

        Intent intent = getIntent();
        game = (Game) intent.getSerializableExtra("currentGame");

        ArrayList<String> statusArray = new ArrayList<>();
        for (GameStatus status : GameStatus.values()){
            statusArray.add(status.getStatus());
        }

        titleInput = (EditText) findViewById(R.id.modify_input_title);
        platformInput = (EditText) findViewById(R.id.modify_input_platform);
        statusSpinner = (Spinner) findViewById(R.id.modify_input_status);
        notesInput = (EditText) findViewById(R.id.modify_input_notes);

        ArrayAdapter statusAdapter = new ArrayAdapter(this, R.layout.custom_simple_spinner_item, statusArray);
        statusSpinner.setAdapter(statusAdapter);

        titleInput.setText(game.getTitle());
        platformInput.setText(game.getPlatform());
        setSpinnerPosition(statusAdapter);
        notesInput.setText(game.getNotes());

        modifyButton = (Button) findViewById(R.id.modify_button_modify);
        modifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doModifyGame();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_modify_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void doModifyGame(){
        Date curDate = getSimpleCurrentDate();
        String title = titleInput.getText().toString();
        String platform = platformInput.getText().toString();
        GameStatus gameStatus = GameStatus.getGameStatusByValue(statusSpinner.getSelectedItem().toString());
        String notes = notesInput.getText().toString();

        if(title.equals("")){
            setErrorText(titleInput, getString(R.string.title_is_required));
            showToast(getString(R.string.title_field_is_empty));
        } else if (platform.equals("")){
            setErrorText(platformInput, getString(R.string.platform_is_required));
            showToast(getString(R.string.plaftorm_field_is_empty));
        } else {
            game.setTitle(title);
            game.setPlatform(platform);
            game.setGameStatus(gameStatus);
            game.setNotes(notes);

            GameDao gameDao = new GameDao(ModifyGameActivity.this);
            gameDao.updateGame(game);

            showToast(getString(R.string.game_has_been_modified));
            Intent intent = new Intent(ModifyGameActivity.this, GameDetailsActivity.class);
            intent.putExtra("selectedGame", game);
            startActivity(intent);
        }
    }

    private Date getSimpleCurrentDate(){
        Date curDate = null;
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

    private void showToast(String message){
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }

    private void setSpinnerPosition(ArrayAdapter adapter){
        if (!game.getGameStatus().getStatus().equals(null)){
            int spinnerPosition = adapter.getPosition(game.getGameStatus().getStatus());
            statusSpinner.setSelection(spinnerPosition);
        }
    }

    private void setErrorText(EditText editText, String message){
        //Had to make this helper method because text is black, which is not readable in EditText.onError
        int RGB = android.graphics.Color.argb(255,255,255,255);
        ForegroundColorSpan fgcspan = new ForegroundColorSpan(RGB);
        SpannableStringBuilder ssbuilder = new SpannableStringBuilder(message);
        ssbuilder.setSpan(fgcspan, 0, message.length(), 0);
        editText.setError(ssbuilder);
    }
}
