package com.example.joe.networkbattleship;

import java.util.UUID;

/**
 * Created by Joe on 11/1/2014.
 */
public class GameState {

    /*
        View layer state used by the list view
            to encapsulate the data of a game state
     */
    public UUID id;
    public String gameName;
    public String player1;
    public String player2;
    public Status status;
    public String gameTurn;
    public int missilesA;
    public int missilesB;
    public String winner;

    @Override
    public String toString() {
        String stringStatus = "";

        if(status == Status.DONE) {
            stringStatus = "DONE";
        }
        else if(status == Status.PLAYING) {
            stringStatus = "PLAYING";
        }
        else if(status == Status.WAITING) {
            stringStatus = "WAITING";
        }

        return String.format("%s\n" +
                "  Player 1: %s\n" +
                "  Player 2: %s\n" +
                "    Status: %s" +
                "    Winner: %s",
                gameName, player1, player2,
                stringStatus, winner);
    }
}
