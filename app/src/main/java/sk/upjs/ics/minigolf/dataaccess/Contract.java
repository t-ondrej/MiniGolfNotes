package sk.upjs.ics.minigolf.dataaccess;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Tomas on 16.6.2017.
 */

public final class Contract {

    public static final String AUTHORITY = "sk.upjs.ics.minigolf.dataaccess.MinigolfContentProvider";

    private Contract() {
        // Placeholder
    }

    /** COLUMNS **/
    interface GameColumns extends BaseColumns {
        String TABLE_NAME = "game";
        String TIMESTAMP = "timestamp";
        String LONGITUDE = "longitude";
        String LATITUDE = "latitude";
        String PHOTOURI = "photo_uri";
    }

    interface PlayerColumns extends BaseColumns {
        String TABLE_NAME = "player";
        String NAME = "name";
    }

    interface PlayerToGameColumns {
        String TABLE_NAME = "player_to_game";
        String IDPLAYER = "id_player";
        String IDGAME = "id_game";
    }

    /** PATHS **/
    public static final String PATH_GAME = "game";
    public static final String PATH_PLAYER = "player";
    public static final String PATH_PLAYERTOGAME = "player_to_game";

    /** TABLES **/
    public static final class Game implements GameColumns {
        public static final Uri CONTENT_URI = new Uri.Builder()
                .scheme(ContentResolver.SCHEME_CONTENT)
                .authority(AUTHORITY)
                .appendPath(PATH_GAME)
                .build();

        public static Uri buildGameUri(String gameId) {
            return CONTENT_URI.buildUpon().appendPath(gameId).build();
        }

        public static String getGameId(Uri uri) {
            return uri.getLastPathSegment();
        }
    }

    public static final class Player implements PlayerColumns {
        public static final Uri CONTENT_URI = new Uri.Builder()
                .scheme(ContentResolver.SCHEME_CONTENT)
                .authority(AUTHORITY)
                .appendPath(PATH_PLAYER)
                .build();

        public static Uri buildPlayerUri(String playerId) {
            return CONTENT_URI.buildUpon().appendPath(playerId).build();
        }

        public static String getPlayerId(Uri uri) {
            return uri.getLastPathSegment();
        }
    }

    public static final class PlayerToGame implements PlayerToGameColumns {
        public static final Uri CONTENT_URI = new Uri.Builder()
                .scheme(ContentResolver.SCHEME_CONTENT)
                .authority(AUTHORITY)
                .appendPath(PATH_PLAYERTOGAME)
                .build();
    }
}
