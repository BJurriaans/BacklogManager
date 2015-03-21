package com.example.bobby.gamesbacklogmanager.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Bobby on 12-3-2015.
 */
public class Game implements Serializable {
    private long id;
    private String title;
    private String platform;
    private Date dateAdded;
    private GameStatus gameStatus;
    private String notes = "";

    public Game(){}

    public Game(int id, String title, String platform, Date dateAdded, GameStatus gameStatus, String notes) {
        this.id = id;
        this.title = title;
        this.platform = platform;
        this.dateAdded = dateAdded;
        this.gameStatus = gameStatus;
        this.notes = notes;
    }

    public long getId(){
        return id;
    }

    public void setId(long id){
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
