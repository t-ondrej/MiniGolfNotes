package sk.upjs.ics.minigolf.dataaccess;

import android.content.UriMatcher;
import android.net.Uri;
import android.util.SparseArray;

public class UriMatcherWrapper {

    private UriMatcher mUriMatcher;
    private SparseArray<UriEnum> mEnumsMap = new SparseArray<>();

    public UriMatcherWrapper() {
        this.mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        buildUriMatcher();
    }

    public UriEnum matchUri(Uri uri){
        final int code = mUriMatcher.match(uri);

        try {
            return matchCode(code);
        } catch (UnsupportedOperationException e){
            throw new UnsupportedOperationException("Unknown uri " + uri);
        }
    }

    private void buildUriMatcher() {
        final String authority = Contract.AUTHORITY;
        UriEnum[] uris = UriEnum.values();

        for (UriEnum uri : uris)
            mUriMatcher.addURI(authority, uri.path, uri.code);

        buildEnumsMap();
    }

    private void buildEnumsMap() {
        UriEnum[] uris = UriEnum.values();

        for (UriEnum uri : uris)
            mEnumsMap.put(uri.code, uri);
    }

    private UriEnum matchCode(int code){
        UriEnum scheduleUriEnum = mEnumsMap.get(code);

        if (scheduleUriEnum != null){
            return scheduleUriEnum;
        } else {
            throw new UnsupportedOperationException("Unknown uri with code " + code);
        }
    }
}
