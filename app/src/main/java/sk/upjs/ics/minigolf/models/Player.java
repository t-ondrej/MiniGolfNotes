package sk.upjs.ics.minigolf.models;

import android.os.Bundle;

/**
 * Created by Tomas on 7.6.2017.
 */

public class Player {

    private Long id;
    private String name;
    private int score;
    private int[] scores;
    private Game game;

    public Player() {
        // Placeholder
    }

    public Player(String name) {
        this.name = name;
    }

    public Player(Long id, String name, int score) {
        this.id = id;
        this.name = name;
        this.score = score;
    }

    public static Player fromBundle(Bundle bundle) {
        Player player = new Player(bundle.getString("name"));

        if (bundle.containsKey("id"))
            player.setId(bundle.getLong("id"));

        if (bundle.containsKey("score"))
            player.setScore(bundle.getInt("score"));

        if (bundle.containsKey("scores"))
            player.setScores(bundle.getIntArray("scores"));

        return player;
    }

    public Bundle toBundle() {
        Bundle bundle = new Bundle();

        if (id != null)
            bundle.putLong("id", id);

        bundle.putString("name", name);
        bundle.putInt("score", score);
        bundle.putIntArray("scores", scores);

        return bundle;
    }

    public String getNameWithScore() {
        return name + " (" + score + ")";
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
        return score;
    }

    public void setScore(int score) {
        this.score = score;
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
        this.score += score;
    }

    public void setScores(int[] scores) {
        this.scores = scores;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}