package com.example.bobby.gamesbacklogmanager.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.bobby.gamesbacklogmanager.Model.Game;
import com.example.bobby.gamesbacklogmanager.R;

import java.util.ArrayList;

/**
 * Created by Bobby on 12-3-2015.
 */
public class GameListItemAdapter extends BaseAdapter {

    ArrayList<Game> gameArrayList;
    Context context;

    public GameListItemAdapter(ArrayList<Game> list, Context context){
        this.gameArrayList = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return gameArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return gameArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return gameArrayList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService((Context.LAYOUT_INFLATER_SERVICE));
        View row = inflater.inflate(R.layout.single_game_item, parent, false);
        TextView title = (TextView) row.findViewById(R.id.gameTitle);
        TextView platform = (TextView) row.findViewById(R.id.gamePlatform);
        TextView status = (TextView) row.findViewById(R.id.gameStatus);
        TextView date = (TextView) row.findViewById(R.id.gameDate);

        Game game = gameArrayList.get(position);

        title.setText(game.getTitle());
        platform.setText(game.getPlatform());
        status.setText(game.getGameStatus().getStatus());
        date.setText(game.getDateAdded().toString());


        return row;
    }
}
