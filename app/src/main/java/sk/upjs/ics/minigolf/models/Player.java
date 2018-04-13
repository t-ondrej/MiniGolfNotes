package sk.upjs.ics.minigolf.models;

import android.os.Bundle;

/**
 * Created by Tomas on 7.6.2017.
 */

public class Player {

    private Long id;
    private String name;
    private int score;

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

        return player;
    }

    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        if (id != null)
            bundle.putLong("id", id);
        bundle.putString("name", name);
        bundle.putInt("score", score);

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
}