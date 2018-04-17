package sk.upjs.ics.minigolf.dataaccess;

import android.net.Uri;

public enum UriEnum {

    GAMES(100,
            Contract.PATH_GAMES,
            Contract.Game.TABLE_NAME,
            Contract.Game.CONTENT_URI),

    GAMES_ID(101,
            Contract.PATH_GAMES_ID,
            Contract.Game.TABLE_NAME,
            Contract.Game.CONTENT_URI),

    GAMES_ID_PLAYERS(102,
            Contract.PATH_PLAYER_TO_GAME,
            Contract.GamePlayer.TABLE_NAME,
            Contract.GamePlayer.CONTENT_URI),

    PLAYERS(200,
            Contract.PATH_PLAYERS,
            Contract.Player.TABLE_NAME,
            Contract.Player.CONTENT_URI),

    PLAYERS_ID(201,
            Contract.PATH_PLAYERS_ID,
            Contract.Player.TABLE_NAME ,
            Contract.Player.CONTENT_URI),

    PLAYERS_ID_SCORES(202,
            Contract.PATH_SCORE_TO_PLAYER,
                 Contract.PlayerScore.TABLE_NAME,
                 Contract.PlayerScore.CONTENT_URI);

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
