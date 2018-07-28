package sk.upjs.ics.minigolf;

import android.content.res.Resources;
import android.os.Bundle;

import sk.upjs.ics.minigolf.models.Game;

// Sharing game instance between newgame - course - gamesummary
public enum GameHolder {

    INSTANCE;

    private Game game;

    public Game getGame() {
        return game;
    }

    public Game initNewGame(Resources resources) {
        game = Game.createFreshGame(resources);
        return game;
    }

    public Game initExistingGame(Bundle savedInstanceState) {
        game = Game.fromBundle(savedInstanceState);
        return game;
    }
}
