package com.example.administrator.myapplication;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class MyContentProvider extends ContentProvider {
    public static final Uri CONTENT_URI=Uri.parse("content://com.example.administrator.myapplication/elements");
    private static final int ALLROWS=1;
    private static final int SINGLE_ROW=2;
    private static final UriMatcher uriMatcher;
    static{
        uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI("com.example.administrator.myapplication","elements",ALLROWS);
        uriMatcher.addURI("com.example.administrator.myapplication","elements/#",SINGLE_ROW);
    }
    private MySqliteOpenHelper mySqliteOpenHelper;
    public MyContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        switch (uriMatcher.match(uri)) {
            case ALLROWS:
                return "vnd.android.cursor.dir/vnd.example.elemental";
            case SINGLE_ROW:
                return "vnd.android.cursor.item/vnd.example.elemental";

            default:
                throw new UnsupportedOperationException("Not yet implemented");
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        SQLiteDatabase db=mySqliteOpenHelper.getWritableDatabase();
        long id=db.insert(MySqliteOpenHelper.DATABASE_TABLE,null,values);
        if(id>-1){
            Uri insertedId= ContentUris.withAppendedId(CONTENT_URI,id);
            getContext().getContentResolver().notifyChange(insertedId,null);
            return insertedId;
        }else{
            return null;
        }
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        mySqliteOpenHelper=new MySqliteOpenHelper(this.getContext(),MySqliteOpenHelper.DATABASE_NAME,null,MySqliteOpenHelper.VERSION);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        //throw new UnsupportedOperationException("Not yet implemented");
        SQLiteDatabase db=mySqliteOpenHelper.getWritableDatabase();
        SQLiteQueryBuilder builder=new SQLiteQueryBuilder();
        builder.setTables(MySqliteOpenHelper.DATABASE_TABLE);
        switch (uriMatcher.match(uri)){
            case SINGLE_ROW:
                String rowId=uri.getPathSegments().get(1);
                builder.appendWhere("id="+rowId);
                default:break;
        }
        Cursor cursor=builder.query(db,projection,selection,selectionArgs,sortOrder,null,null);
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    private static final class MySqliteOpenHelper extends SQLiteOpenHelper{
        private static final  String DATABASE_NAME="orc";
        private static final  String DATABASE_TABLE="customer";
        private static final  String CREATE_TABLE="create table "+DATABASE_TABLE+
                                                                       "(id integer primary key autoincrement,name text not null);";
        private static final int VERSION=1;
        public MySqliteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
           db.execSQL(CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
           db.execSQL("drop table if exists "+DATABASE_TABLE);
            onCreate(db);
        }
    }
}
