package sk.upjs.ics.minigolf.models;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Tomas on 14.6.2017.
 */

public class Game implements PlayerManager {

    private int hitCountMax;
    private int holeCount;
    private boolean saveLocation;
    private long timestamp;
    private List<Player> players;
    private int currentHole;

    public Game() {
        players = new ArrayList<>();
        timestamp = System.currentTimeMillis();
    }

    public Game(int hitCountMax, int holeCount, boolean saveLocation, List<Player> players) {
        this(hitCountMax, holeCount, saveLocation, System.currentTimeMillis(), players);

        for (Player player : players)
            player.createScoreArray(holeCount);
    }

    public Game(int hitCountMax, int holeCount, boolean saveLocation, long timestamp, List<Player> players) {
        this.hitCountMax = hitCountMax;
        this.holeCount = holeCount;
        this.saveLocation = saveLocation;
        this.players = players;
        this.timestamp = timestamp;
    }

    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putInt("hitCountMax", hitCountMax);
        bundle.putInt("holeCount", holeCount);
        bundle.putBoolean("saveLocation", saveLocation);
        bundle.putLong("timestamp", timestamp);

        for (int i = 0; i < players.size(); i++) {
            bundle.putBundle("Player " + i, players.get(i).toBundle());
        }

        return bundle;
    }

    public static Game fromBundle(Bundle bundle) {
        List<Player> players = new ArrayList<>();

        for (String string : bundle.keySet()) {
            if (string.startsWith("Player")) {
                players.add(Player.fromBundle(bundle.getBundle(string)));
            }
        }

        return new Game(bundle.getInt("hitCountMax"),
                bundle.getInt("holeCount"),
                bundle.getBoolean("saveLocation"),
                bundle.getLong("timestamp"),
                players);
    }

    @Override
    public int getPlayerCount() {
        return players.size();
    }

    @Override
    public void addPlayer(Player player) {
        players.add(player);
        player.setGame(this);
        player.createScoreArray(this.holeCount);
    }

    @Override
    public Player findPlayerByPosition(int idx) {
        return players.get(idx);
    }

    @Override
    public Player findPlayerByName(String name) {
        Player player = null;
        for (Player playerr : players)
            if (playerr.getName().equals(name)) {
                player = playerr;
                break;
            }

        return player;
    }

    @Override
    public void removePlayerByPosition(int position) {
        players.remove(position);
    }

    public List<Integer> getPossibleScores() {
        List<Integer> scores = new ArrayList<>();
        for (int i = 0; i <= hitCountMax; i++) {
            scores.add(i);
        }

        return scores;
    }

    public int getHitCountMax() {
        return hitCountMax;
    }

    public int getHoleCount() {
        return holeCount;
    }

    public boolean isSaveLocation() {
        return saveLocation;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setHitCountMax(int hitCountMax) {
        this.hitCountMax = hitCountMax;
    }

    public void setHoleCount(int holeCount) {
        this.holeCount = holeCount;

        for (Player player : players) {
            player.createScoreArray(this.holeCount);
        }

    }

    public void setSaveLocation(boolean saveLocation) {
        this.saveLocation = saveLocation;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
