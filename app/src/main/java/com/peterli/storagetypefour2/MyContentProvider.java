package com.peterli.storagetypefour2;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;

public class MyContentProvider extends ContentProvider {
    DBHelper mDBHelper;
    SQLiteDatabase mDatabase;
    Context mContext;

    private static final UriMatcher mMatcher;

    private static final String AUTHORITY = "com.peter.provider";

    public static final int Student_Code = 1;
    public static final int Teacher_Code = 2;

    static {
        mMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        mMatcher.addURI(AUTHORITY, "Student", Student_Code);
        mMatcher.addURI(AUTHORITY, "Teacher", Teacher_Code);
    }

    @Override
    public boolean onCreate() {
        mContext = getContext();
        mDBHelper = new DBHelper(getContext());
        mDatabase = mDBHelper.getReadableDatabase();
        mDatabase.execSQL("delete from Student");
        mDatabase.execSQL("insert into Student(name,age) values(\"小明\",17)");
        mDatabase.execSQL("insert into Student(name,age) values(\"小红\",18)");
        mDatabase.execSQL("delete from Teacher");
        mDatabase.execSQL("insert into Teacher(name,age) values(\"张老师\",54)");
        mDatabase.execSQL("insert into Teacher(name,age) values(\"王老师\",47)");
        return true;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        String table = getTableName(uri);
        mDatabase.insert(table, null, values);
        mContext.getContentResolver().notifyChange(uri, null);
        return uri;
    }

    private String getTableName(Uri uri) {
        String table = null;
        switch (mMatcher.match(uri)) {
            case Student_Code:
                table = "Student";
                break;
            case Teacher_Code:
                table = "Teacher";
                break;
        }
        return table;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        String table = getTableName(uri);
        return mDatabase.query(table, projection, selection, selectionArgs, null, sortOrder, null);
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(@NonNull Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
