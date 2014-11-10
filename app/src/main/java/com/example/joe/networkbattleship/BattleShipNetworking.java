package com.example.joe.networkbattleship;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

/**
 * Created by Joe on 11/9/2014.
 */
public class BattleShipNetworking {

    public class GuessResponse {
        public boolean hit;
        public int shipSunk;
    }

    public class TurnResponse {
        public boolean isYourTurn;
        public String winner;
    }

    public static final String BASE_URL =  "http://battleship.pixio.com";

    public static PlayerBoard[] getBoards(int id, String playerName) {

        String payload = "{\"playerId\": " + playerName + "}";

        try {
            HttpClient client = new DefaultHttpClient();
            HttpPost request = new HttpPost(BASE_URL + "/api/games/" + id + "/board");
            request.setEntity(new StringEntity(payload));
            HttpResponse response = client.execute(request);

            InputStream responseContent = response.getEntity().getContent();
            Scanner responseScanner = new Scanner(responseContent).useDelimiter("\\A");

            String responseString = responseScanner.hasNext() ? responseScanner.next() : null;

            Gson gson = new Gson();
            PlayerBoard[] games = gson.fromJson(responseString, PlayerBoard[].class);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Game[] requestGameList() {
        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet(BASE_URL + "/api/games/");
            HttpResponse response = client.execute(request);

            InputStream responseContent = response.getEntity().getContent();
            Scanner responseScanner = new Scanner(responseContent).useDelimiter("\\A");

            String responseString = responseScanner.hasNext() ? responseScanner.next() : null;

            Gson gson = new Gson();
            Game[] games = gson.fromJson(responseString, Game[].class);

            return games;
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
        catch (ClientProtocolException e) {
            e.printStackTrace();
            return null;
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static GameDetail requestGameDetails(UUID identifier) {

        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet(BASE_URL + "/api/games/" + identifier.toString());
            HttpResponse response = client.execute(request);

            InputStream responseContent = response.getEntity().getContent();
            Scanner responseScanner = new Scanner(responseContent).useDelimiter("\\A");

            String responseString = responseScanner.hasNext() ? responseScanner.next() : null;

            Gson gson = new Gson();
            GameDetail gameDetail = gson.fromJson(responseString, GameDetail.class);

            return gameDetail;
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
        catch (ClientProtocolException e) {
            e.printStackTrace();
            return null;
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static UUID joinGame(UUID gameIdentifier, String playerName) {
        String payload = "{\"playerName\": " + playerName + "}";

        try {
            HttpClient client = new DefaultHttpClient();
            HttpPost request = new HttpPost(BASE_URL + "/api/games/" + gameIdentifier + "/join");
            request.setEntity(new StringEntity(payload));
            HttpResponse response = client.execute(request);

            InputStream responseContent = response.getEntity().getContent();
            Scanner responseScanner = new Scanner(responseContent).useDelimiter("\\A");

            String responseString = responseScanner.hasNext() ? responseScanner.next() : null;

            Gson gson = new Gson();
            Map<String, UUID> playerIdentifier = gson.fromJson(responseString, Map.class);
            return playerIdentifier.get("playerId");
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
        catch (ClientProtocolException e) {
            e.printStackTrace();
            return null;
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Map<String, UUID> createGame(String gameName, String playerName) {
        String payload = "{\"gameName\": " + gameName + ",\"playerName\": " + playerName + "}";

        try {
            HttpClient client = new DefaultHttpClient();
            HttpPost request = new HttpPost(BASE_URL + "/api/games/");
            request.setEntity(new StringEntity(payload));
            HttpResponse response = client.execute(request);

            InputStream responseContent = response.getEntity().getContent();
            Scanner responseScanner = new Scanner(responseContent).useDelimiter("\\A");

            String responseString = responseScanner.hasNext() ? responseScanner.next() : null;

            Gson gson = new Gson();
            Map<String, UUID> identifiers = gson.fromJson(responseString, Map.class);
            return identifiers;
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
        catch (ClientProtocolException e) {
            e.printStackTrace();
            return null;
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean makeGuess(UUID gameId, UUID playerId, int row, int col) {

        String payload = "{\"playerId\": " + playerId + ",\"xPos\": " + col + ",\"yPos\": " + row + "}";

        try {
            HttpClient client = new DefaultHttpClient();
            HttpPost request = new HttpPost(BASE_URL + "/api/games/" + gameId.toString() + "/guess");
            request.setEntity(new StringEntity(payload));
            HttpResponse response = client.execute(request);

            InputStream responseContent = response.getEntity().getContent();
            Scanner responseScanner = new Scanner(responseContent).useDelimiter("\\A");

            String responseString = responseScanner.hasNext() ? responseScanner.next() : null;

            Gson gson = new Gson();
            GuessResponse guess = gson.fromJson(responseString, GuessResponse.class);
            return guess.hit;
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        }
        catch (ClientProtocolException e) {
            e.printStackTrace();
            return false;
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isYourTurn(UUID gameId, UUID playerId) {

        String payload = "{\"playerId\": " + playerId + "}";

        try {
            HttpClient client = new DefaultHttpClient();
            HttpPost request = new HttpPost(BASE_URL + "/api/games/" + gameId.toString() + "/status");
            request.setEntity(new StringEntity(payload));
            HttpResponse response = client.execute(request);

            InputStream responseContent = response.getEntity().getContent();
            Scanner responseScanner = new Scanner(responseContent).useDelimiter("\\A");

            String responseString = responseScanner.hasNext() ? responseScanner.next() : null;

            Gson gson = new Gson();
            TurnResponse turn = gson.fromJson(responseString, TurnResponse.class);
            return turn.isYourTurn;
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        }
        catch (ClientProtocolException e) {
            e.printStackTrace();
            return false;
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String getWinner(UUID gameId, UUID playerId) {

        String payload = "{\"playerId\": " + playerId + "}";

        try {
            HttpClient client = new DefaultHttpClient();
            HttpPost request = new HttpPost(BASE_URL + "/api/games/" + gameId.toString() + "/status");
            request.setEntity(new StringEntity(payload));
            HttpResponse response = client.execute(request);

            InputStream responseContent = response.getEntity().getContent();
            Scanner responseScanner = new Scanner(responseContent).useDelimiter("\\A");

            String responseString = responseScanner.hasNext() ? responseScanner.next() : null;

            Gson gson = new Gson();
            TurnResponse turn = gson.fromJson(responseString, TurnResponse.class);
            return turn.winner;
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
        catch (ClientProtocolException e) {
            e.printStackTrace();
            return null;
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}
