package sk.upjs.ics.minigolf.dataaccess;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Tomas on 13.6.2017.
 */

public class DbOpenHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MiniGolfNotes";
    public static final int DATABASE_VERSION = 1;

    interface Tables {
        String GAME = "game";
        String PLAYER = "player";
        String PLAYER_TO_GAME = "player_to_game";

        String GAME_JOIN_PLAYER = "player_to_game PTG " +
                "JOIN game G ON PTG.id_game = G._id " +
                "JOIN player P ON PTG.id_player = P._id";
    }

    public DbOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createPlayerTableSql());
        db.execSQL(createGameTableSql());
        db.execSQL(createPlayerToGameTableSql());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private String createPlayerTableSql() {
        String sqlTemplate = "CREATE TABLE %s ("
                + "%s INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "%s TEXT"
                + ")";

        return String.format(sqlTemplate,
                Contract.Player.TABLE_NAME,
                Contract.Player._ID,
                Contract.Player.NAME);
    }

    private String createGameTableSql() {
        String sqlTemplate = "CREATE TABLE %s ("
                + "%s INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "%s DATETIME,"
                + "%s TEXT,"
                + "%s TEXT,"
                + "%s TEXT"
                + ")";

        return String.format(sqlTemplate,
                Contract.Game.TABLE_NAME,
                Contract.Game._ID,
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
                Contract.PlayerToGame.TABLE_NAME,
                Contract.PlayerToGame.IDPLAYER,
                Contract.PlayerToGame.IDGAME,
                Contract.PlayerToGame.IDPLAYER, Contract.Player.TABLE_NAME, Contract.Player._ID,
                Contract.PlayerToGame.IDGAME, Contract.Game.TABLE_NAME, Contract.Game._ID
                );
    }
}
