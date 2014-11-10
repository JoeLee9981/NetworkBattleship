package com.example.joe.networkbattleship;

import java.util.UUID;

/*
    Model layer enum used to track the state of the game
 */
enum GameMode {
    SETUP,
    PLAYER_A_TURN,
    PLAYER_B_TURN,
    PLAYER_A_WON,
    PLAYER_B_WON
}

enum Status {
    DONE,
    WAITING,
    PLAYING
}

enum TileStatus {
    HIT,
    MISS,
    SHIP,
    NONE
}

class GameDetail {
    UUID id;
    String name;
    String player1;
    String player2;
    String winner;
}

class PlayerBoard {
    int x;
    int y;
    TileStatus status;
}

class Game {
    UUID id;
    String name;
    Status status;
}

