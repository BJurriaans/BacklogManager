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

    public static GameStatus getGameStatusByValue(String value){

        switch (value){
            case "Want to play" :
                return WANT_TO_PLAY;
            case "Playing" :
                return PLAYING;
            case "Stalled" :
                return STALLED;
            case "Dropped" :
                return DROPPED;
            default :
                return null;
        }
    }
}