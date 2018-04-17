package sk.upjs.ics.minigolf.dataaccess;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public final class Contract {

    public static final String AUTHORITY = "sk.upjs.ics.minigolf";
        private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    private Contract() {
        // Placeholder
    }

    /** COLUMNS **/
    interface GameColumns extends BaseColumns {
        String TABLE_NAME = "game";
        String HITCOUNTMAX = "hitcountmax";
        String HOLECOUNT = "holecount";
        String TIMESTAMP = "timestamp";
        String LONGITUDE = "longitude";
        String LATITUDE = "latitude";
        String PHOTOURI = "photo_uri";
    }

    interface GamePlayerColumns extends BaseColumns {
        String TABLE_NAME = "player_to_game";
        String IDPLAYER = "id_player";
        String IDGAME = "id_game";
    }

    interface PlayerColumns extends BaseColumns {
        String TABLE_NAME = "player";
        String NAME = "name";
        String SCORES = "scores"; // TODO
    }

    interface PlayerScoreColumns extends BaseColumns {
        String TABLE_NAME = "score_to_player";
        String SCORE = "points";
        String HOLE = "hole";
        String IDPLAYER = "id_player";
    }

    /** PATHS **/
    public static final String PATH_GAMES = "games";
    public static final String PATH_GAMES_ID = "games" + "/#";
    public static final String PATH_PLAYERS = "players";
    public static final String PATH_PLAYERS_ID = "players" + "/#";
    public static final String PATH_PLAYER_TO_GAME = "player_to_game";
    public static final String PATH_SCORE_TO_PLAYER = "score_to_player";

    /** TABLES **/
    public static final class Game implements GameColumns {
        public static final Uri CONTENT_URI = new Uri.Builder()
                .scheme(ContentResolver.SCHEME_CONTENT)
                .authority(AUTHORITY)
                .appendPath(PATH_GAMES)
                .build();

        public static Uri buildGameUri(long gameId) {
            return CONTENT_URI.buildUpon().appendPath(Long.toString(gameId)).build();
        }

        public static Uri buildPlayersUri(long gameId) {
            return CONTENT_URI.buildUpon().appendPath(Long.toString(gameId)).appendPath(PATH_PLAYERS).build();
        }

        public static String getGameId(Uri uri) {
            return uri.getLastPathSegment();
        }
    }

    public static final class GamePlayer implements GamePlayerColumns {
        public static final Uri CONTENT_URI = new Uri.Builder()
                .scheme(ContentResolver.SCHEME_CONTENT)
                .authority(AUTHORITY)
                .appendPath(PATH_PLAYER_TO_GAME)
                .build();
    }

    public static final class Player implements PlayerColumns {
        public static final Uri CONTENT_URI = new Uri.Builder()
                .scheme(ContentResolver.SCHEME_CONTENT)
                .authority(AUTHORITY)
                .appendPath(PATH_PLAYERS)
                .build();

        public static Uri buildPlayerUri(long playerId) {
            return CONTENT_URI.buildUpon().appendPath(Long.toString(playerId)).build();
        }

        public static Uri buildScoresUri(long playerId) {
            return CONTENT_URI.buildUpon().appendPath(Long.toString(playerId)).appendPath("scores").build();
        }

        public static String getPlayerId(Uri uri) {
            return uri.getLastPathSegment();
        }
    }

    public static final class PlayerScore implements PlayerScoreColumns {
        public static final Uri CONTENT_URI = new Uri.Builder()
                .scheme(ContentResolver.SCHEME_CONTENT)
                .authority(AUTHORITY)
                .appendPath(PATH_SCORE_TO_PLAYER)
                .build();
    }
}