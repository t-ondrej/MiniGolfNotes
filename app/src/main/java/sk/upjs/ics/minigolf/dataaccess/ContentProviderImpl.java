package sk.upjs.ics.minigolf.dataaccess;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import sk.upjs.ics.minigolf.SelectionBuilder;

import static sk.upjs.ics.minigolf.dataaccess.Constants.*;
import static sk.upjs.ics.minigolf.dataaccess.Contract.*;
import static sk.upjs.ics.minigolf.dataaccess.DbOpenHelper.*;

/**
 * Source: https://github.com/google/iosched/tree/master/lib/src/main/java/com/google/samples/apps/iosched/provider
 */
public class ContentProviderImpl extends ContentProvider {

    private DbOpenHelper mOpenHelper;
    private UriMatcher mUriMatcher;

    /** URI CODES **/
    public static final int GAMES = 100;
    public static final int GAMES_ID = 101;
    public static final int GAMES_ID_PLAYERS = 102;
    public static final int PLAYERS = 200;
    public static final int PLAYERS_ID = 201;
    public static final int PLAYERS_ID_SCORES = 202;

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = AUTHORITY;

        matcher.addURI(authority, "games", GAMES);
        matcher.addURI(authority, "games/*", GAMES_ID);
        matcher.addURI(authority, "games/*/players", GAMES_ID_PLAYERS);

        matcher.addURI(authority, "players", PLAYERS);
        matcher.addURI(authority, "players/*", PLAYERS_ID);
        matcher.addURI(authority, "players/*/scores", PLAYERS_ID_SCORES);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        this.mOpenHelper = new DbOpenHelper(getContext());
        this.mUriMatcher = buildUriMatcher();

        return true;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        final SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        final int match = mUriMatcher.match(uri);
        // Most cases are handled with simple SelectionBuilder
        final SelectionBuilder builder = buildExpandedSelection(uri, match);
        return builder.where(selection, selectionArgs).query(db, projection, sortOrder);
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = mUriMatcher.match(uri);

        switch (match) {
            case GAMES: {
                long newId = db.insertOrThrow(Game.TABLE_NAME, NO_NULL_COLUMN_HACK, values);
                getContext().getContentResolver().notifyChange(uri, null);
                return Game.buildGameUri(newId);
            }
            case GAMES_ID_PLAYERS: {
                long newId = db.insertOrThrow(GamePlayer.TABLE_NAME, NO_NULL_COLUMN_HACK, values);
                getContext().getContentResolver().notifyChange(uri, null);
                return Player.buildPlayerUri(newId);
             }
            case PLAYERS: {
                long newId = db.insertOrThrow(Player.TABLE_NAME, NO_NULL_COLUMN_HACK, values);
                getContext().getContentResolver().notifyChange(uri, null);
                return Player.buildPlayerUri(newId);
            }
            case PLAYERS_ID_SCORES: {
                long newId = db.insertOrThrow(Player.TABLE_NAME, NO_NULL_COLUMN_HACK, values);
                getContext().getContentResolver().notifyChange(uri, null);
                return Player.buildPlayerUri(newId);
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        int numInserted;
        String table = "";

        final int match = mUriMatcher.match(uri);

        switch (match) {
            case PLAYERS:
                table = Player.TABLE_NAME;
                break;
            case GAMES_ID_PLAYERS:
                table = GamePlayer.TABLE_NAME;
                break;
        }

        SQLiteDatabase sqlDB = mOpenHelper.getWritableDatabase();
        sqlDB.beginTransaction();

        try {
            for (ContentValues cv : values) {
                long newID = sqlDB.insertOrThrow(table, NO_NULL_COLUMN_HACK, cv);
                if (newID <= 0) {
                    throw new SQLException("Failed to insert row into " + uri);
                }
            }

            sqlDB.setTransactionSuccessful();
            getContext().getContentResolver().notifyChange(uri, null);
            numInserted = values.length;

        } finally {
            sqlDB.endTransaction();
        }

        return numInserted;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final SelectionBuilder builder = buildSimpleSelection(uri);
        int retVal = builder.where(selection, selectionArgs).delete(db);
        getContext().getContentResolver().notifyChange(Game.CONTENT_URI, null); // TODO: notify content uri dynamically
        return retVal;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private SelectionBuilder buildSimpleSelection(Uri uri) {
        final SelectionBuilder builder = new SelectionBuilder();
        final int match = mUriMatcher.match(uri);

        switch (match) {
            case GAMES: {
                return builder.table(Game.TABLE_NAME);
            }
            case GAMES_ID: {
                final String gameId = Game.getGameId(uri);
                return builder.table(Tables.GAME)
                        .where(Game._ID + "=?", gameId);
            }
            case GAMES_ID_PLAYERS: {
                final String gameId = Game.getGameId(uri);
                return builder.table(Tables.PLAYER_TO_GAME)
                        .where(Game._ID + "=?", gameId);
            }
            case PLAYERS: {
                return builder.table(Tables.PLAYER);
            }
            case PLAYERS_ID: {
                final String playerId = Player.getPlayerId(uri);
                return builder.table(Tables.PLAYER)
                        .where(Player._ID + "=?", playerId);
            }
            case PLAYERS_ID_SCORES: {
                final String playerId = Player.getPlayerId(uri);
                return builder.table(Tables.SCORE_TO_PLAYER)
                        .where(Player._ID + "=?", playerId);
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    private SelectionBuilder buildExpandedSelection(Uri uri, int match) {
        final SelectionBuilder builder = new SelectionBuilder();

        switch (match) {
            case GAMES: {
                return builder.table(Tables.GAME);
            }
            case GAMES_ID: {
                final String gameId = uri.getLastPathSegment();
                return builder.table(Tables.GAME_JOIN_PLAYERS)
                        .where("G." + Game._ID + "=?", gameId);
            }
            case GAMES_ID_PLAYERS: {
                final String gameId = uri.getPathSegments().get(1);
                return builder.table(Tables.GAME_JOIN_PLAYERS)
                        .where("game." + Game._ID + "=?", gameId); // TODO: qualified
            }
            case PLAYERS: {
                return builder.table(Player.TABLE_NAME);
            }
            case PLAYERS_ID: {
                final String playerId = uri.getLastPathSegment();
                return builder.table(Tables.PLAYER)
                        .where(Player._ID + "=?", playerId);
            }
            case PLAYERS_ID_SCORES: {
                final String playerId = uri.getLastPathSegment();
                return builder.table(Tables.SCORE_TO_PLAYER)
                        .mapToTable(PlayerScore._ID, Tables.SCORE_TO_PLAYER)
                        .mapToTable(PlayerScore.SCORE, Tables.SCORE_TO_PLAYER)
                        .mapToTable(PlayerScore.HOLE, Tables.SCORE_TO_PLAYER)
                        .where(Player._ID + "=?", playerId);
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }
}
