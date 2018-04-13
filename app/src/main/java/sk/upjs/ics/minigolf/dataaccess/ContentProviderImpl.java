package sk.upjs.ics.minigolf.dataaccess;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import static sk.upjs.ics.minigolf.dataaccess.Constants.*;


public class ContentProviderImpl extends ContentProvider {

    private DbOpenHelper databaseHelper;
    private UriMatcherWrapper mUriMatcher;
    private static final int SINGLE_QUERY = 0;

    @Override
    public boolean onCreate() {
        this.databaseHelper = new DbOpenHelper(getContext());
        this.mUriMatcher = new UriMatcherWrapper();

        return true;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override // stlpce, whereClause, whereArgs, sortOrder
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        final SQLiteDatabase db = databaseHelper.getReadableDatabase();
        UriEnum uriEnum = mUriMatcher.matchUri(uri);

        if (uriEnum.code % 2 == SINGLE_QUERY)
            selection += "_ID =" + uri.getLastPathSegment();

        Cursor cursor = db.query(uriEnum.table, projection, selection, selectionArgs, NO_GROUP_BY, NO_HAVING, NO_SORT_ORDER);

        // Cursor will listen to this content resolver
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = databaseHelper.getWritableDatabase();

        UriEnum uriEnum = mUriMatcher.matchUri(uri);

        long id = -1L;

        if (uriEnum.table != null) {
            try {
                id = db.insertOrThrow(uriEnum.table, NO_NULL_COLUMN_HACK, values);
            } catch (SQLiteConstraintException exception) {
                // Leaving this here as it's handy to to breakpoint on this throw when debugging a
                // bootstrap file issue.
                throw exception;
            }
        }

        return Uri.withAppendedPath(uriEnum.contentUri, String.valueOf(id));
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = databaseHelper.getWritableDatabase();

        String id = uri.getLastPathSegment();
        String[] whereArgs = { id };
        UriEnum uriEnum = mUriMatcher.matchUri(uri);

        int affectedRows = db.delete(uriEnum.table, "_id = ?", whereArgs);

        getContext().getContentResolver().notifyChange(uriEnum.contentUri, NO_CONTENT_OBSERVER);

        return affectedRows;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
