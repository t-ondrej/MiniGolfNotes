package sk.upjs.ics.minigolf.dataaccess;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbOpenHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MiniGolfNotes";
    public static final int DATABASE_VERSION = 1;

    interface Tables {
        String GAME = "game";
        String PLAYER = "player";
        String PLAYER_TO_GAME = "player_to_game";
        String SCORE_TO_PLAYER = "score_to_player";

        String GAME_JOIN_PLAYERS = "game " +
                "LEFT OUTER JOIN player_to_game on player_to_game.id_game = game._id " +
                "LEFT OUTER JOIN player on player._id = player_to_game.id_player ";

        String SCORE_JOIN_PLAYER = "score S " +
                "JOIN player P on S.id_player = P._id";
    }

    interface Qualified {
        String GAMES_GAME_ID = Tables.GAME + "." + Contract.Game._ID;
    }

    public DbOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createPlayerTableSql());
        db.execSQL(createGameTableSql());
        db.execSQL(createPlayerToGameTableSql());
        db.execSQL(createScoreToPlayerTableSql());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Placeholder
    }

    private String createPlayerTableSql() {
        String sqlTemplate = "CREATE TABLE %s ("
                + "%s INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "%s TEXT,"
                + "%s TEXT"
                + ")";

        return String.format(sqlTemplate,
                Contract.Player.TABLE_NAME,
                Contract.Player._ID,
                Contract.Player.NAME,
                Contract.Player.SCORES);
    }

    private String createGameTableSql() {
        String sqlTemplate = "CREATE TABLE %s ("
                + "%s INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "%s INTEGER,"
                + "%s INTEGER,"
                + "%s INTEGER,"
                + "%s REAL,"
                + "%s REAL,"
                + "%s TEXT"
                + ")";

        return String.format(sqlTemplate,
                Contract.Game.TABLE_NAME,
                Contract.Game._ID,
                Contract.Game.HITCOUNTMAX,
                Contract.Game.HOLECOUNT,
                Contract.Game.TIMESTAMP,
                Contract.Game.LONGITUDE,
                Contract.Game.LATITUDE,
                Contract.Game.PHOTOURI);
    }

    private String createPlayerToGameTableSql() {
        String sqlTemplate = "CREATE TABLE %s ("
                + "%s INTEGER,"
                + "%s INTEGER,"
                + "FOREIGN KEY(%s) REFERENCES %s(%s),"
                + "FOREIGN KEY(%s) REFERENCES %s(%s)"
                + ")";

        return String.format(sqlTemplate,
                Contract.GamePlayer.TABLE_NAME,
                Contract.GamePlayer.IDPLAYER,
                Contract.GamePlayer.IDGAME,
                Contract.GamePlayer.IDPLAYER, Contract.Player.TABLE_NAME, Contract.Player._ID,
                Contract.GamePlayer.IDGAME, Contract.Game.TABLE_NAME, Contract.Game._ID
                );
    }

    private String createScoreToPlayerTableSql() {
        String sqlTemplate = "CREATE TABLE %s ("
                + "%s INTEGER,"
                + "%s INTEGER,"
                + "%s INTEGER,"
                + "FOREIGN KEY(%s) REFERENCES %s(%s)"
                + ")";

        return String.format(sqlTemplate,
                Contract.PlayerScore.TABLE_NAME,
                Contract.PlayerScore.SCORE,
                Contract.PlayerScore.HOLE,
                Contract.PlayerScore.IDPLAYER,
                Contract.PlayerScore.IDPLAYER, Contract.Player.TABLE_NAME, Contract.Player._ID
        );
    }
}