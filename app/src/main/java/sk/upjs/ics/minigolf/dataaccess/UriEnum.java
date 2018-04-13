package sk.upjs.ics.minigolf.dataaccess;

import android.net.Uri;

/**
 * Created by Tomas on 14.6.2017.
 */

public enum UriEnum {

    // Note: even numbers means we want to find by id

    GAME(1, Contract.PATH_GAME,
            Contract.Game.TABLE_NAME,
            Contract.Game.CONTENT_URI),
    GAME_SINGLE(2, Contract.PATH_GAME,
            Contract.Game.TABLE_NAME + "/#",
            Contract.Game.CONTENT_URI),

    PLAYER(3, Contract.PATH_PLAYER,
            Contract.Player.TABLE_NAME,
            Contract.Player.CONTENT_URI),
    PLAYER_SINGLE(4, Contract.PATH_PLAYER,
            Contract.Player.TABLE_NAME + "/#",
            Contract.Player.CONTENT_URI),

    PLAYERTOGAME(5, Contract.PATH_PLAYERTOGAME,
            Contract.PlayerToGame.TABLE_NAME,
            Contract.PlayerToGame.CONTENT_URI);

    public int code;
    public String path;
    public String table;
    public Uri contentUri;

    UriEnum(int code, String path, String table, Uri contentUri) {
        this.code = code;
        this.path = path;
        this.table = table;
        this.contentUri = contentUri;
    }
}
