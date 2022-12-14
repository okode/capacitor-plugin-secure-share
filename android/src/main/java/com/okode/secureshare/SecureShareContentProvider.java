package com.okode.secureshare;

import com.okode.secureshare.SecureShareHelper;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

public class SecureShareContentProvider extends ContentProvider {

    private static final String TAG = SecureShareContentProvider.class.getSimpleName();
    private static final String AUTHORITY_SUFFIX = ".providers.secureshare";
    private static final int SEC_SHARE_ID = 1;

    private String authority;
    private UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    @Override
    public boolean onCreate() {
        Log.d(TAG, "Creating content provider");
        authority = getContext().getPackageName() + AUTHORITY_SUFFIX;
        uriMatcher.addURI(authority, "secshare", SEC_SHARE_ID);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        SecureShareHelper ssHelper = new SecureShareHelper(this.getContext());
        Map<String, String> data = null;
        try {
            data = ssHelper.retrieve();
        } catch (SecureShareHelper.SecureShareException e) {
            data = new HashMap<>();
        }

        MatrixCursor result = new MatrixCursor(data.keySet().toArray(new String[data.keySet().size()]));

        switch (uriMatcher.match(uri)) {
            case SEC_SHARE_ID:
                Log.d(TAG, "Queried for secure shared data");
                MatrixCursor.RowBuilder row = result.newRow();
                for (Map.Entry<String, ?> entry : data.entrySet()) {
                    row.add(entry.getKey(), entry.getValue());
                }
                break;
            default:
                Log.e(TAG, "Content URI format not recognized. Provider won't do anything.");
                break;
        }
        return result;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
