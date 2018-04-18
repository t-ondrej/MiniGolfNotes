package sk.upjs.ics.minigolf.models;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import sk.upjs.ics.minigolf.dataaccess.Contract;

import static sk.upjs.ics.minigolf.dataaccess.Constants.AUTOGENERATED_ID;

public class Player {

    private Long id;
    private String name;
    private int[] scores;
    private Game game;

    public Player() {
        // Placeholder
    }

    public Player(String name) {
        this.id = -1L;
        this.name = name;
    }

    public Player(Long id, String name, int[] scores) {
        this.id = id;
        this.name = name;
        this.scores = scores;
    }

    public static Player fromBundle(Bundle bundle) {
        Player player = new Player(bundle.getString("name"));

        if (bundle.containsKey("id"))
            player.setId(bundle.getLong("id"));

        if (bundle.containsKey("scores"))
            player.setScores(bundle.getIntArray("scores"));

        return player;
    }

    public Bundle toBundle() {
        Bundle bundle = new Bundle();

        if (id != null)
            bundle.putLong("id", id);

        bundle.putString("name", name);
        bundle.putIntArray("scores", scores);

        return bundle;
    }

    public ContentValues toContentValues() {
        ContentValues contentValues = new ContentValues();

        String[] stringScores = new String[scores.length];
        for (int i = 0; i < scores.length; i++) {
            stringScores[i] = Integer.toString(scores[i]);
        }

        contentValues.put(Contract.Player._ID, AUTOGENERATED_ID);
        contentValues.put(Contract.Player.NAME, getName());
        contentValues.put(Contract.Player.SCORES, TextUtils.join(",", stringScores));
        return contentValues;
    }

    // Assumes that cursor is pointing to valid data
    public static Player fromCursor(Cursor cursor) {
        long id = cursor.getLong(cursor.getColumnIndex(Contract.Player._ID));
        String name = cursor.getString(cursor.getColumnIndex(Contract.Player.NAME));
        String score = cursor.getString(cursor.getColumnIndex(Contract.Player.SCORES));

        String[] strScores = score.split(",");
        int[] scores = new int[strScores.length];
        for (int i = 0; i < strScores.length; i++) {
            scores[i] = Integer.parseInt(strScores[i]);
        }

        return new Player(id, name, scores);
    }

    public static List<Player> allFromCursor(Cursor cursor) {
        List<Player> players = new ArrayList<>();

        if (cursor.moveToFirst()){
            do {

                players.add(Player.fromCursor(cursor));

            } while(cursor.moveToNext());
        }

        return players;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        int totalScore = 0;

        for (int scoree : scores)
            totalScore += scoree;

        return totalScore;
    }

    public String getScoreString() {
        int totalScore = 0;

        for (int scoree : scores)
            totalScore += scoree;

        return "(" + Integer.toString(totalScore) + ")";
    }

    public void createScoreArray(int holeCount) {
        this.scores = new int[holeCount];
    }

    public int getScoreAtHole(int holeIdx) {
        return this.scores[holeIdx];
    }

    public void setScoreAtHole(int holeIdx, int score) {
        this.scores[holeIdx] = score;
    }

    public void setScores(int[] scores) {
        this.scores = scores;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}