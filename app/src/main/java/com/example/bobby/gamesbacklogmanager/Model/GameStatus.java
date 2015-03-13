package com.example.bobby.gamesbacklogmanager.Model;

/**
 * Created by Bobby on 12-3-2015.
 */
public enum GameStatus {
    WANT_TO_PLAY("Want to play"),
    PLAYING("Playing"),
    STALLED("Stalled"),
    DROPPED("Dropped");

    private final String status;

     GameStatus(String status){
        this.status = status;
    }

    public String getStatus(){
        return status;
    }
}
