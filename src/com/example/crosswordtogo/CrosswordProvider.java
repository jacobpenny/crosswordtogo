package com.example.crosswordtogo;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class CrosswordProvider extends ContentProvider {

	public static final String AUTHORITY = "com.example.crosswordtogo.providers.CrosswordProvider";
	private MainDatabaseHelper crosswordDb_;
	private static final String DATABASE_NAME = "crosswords.db";
	private SQLiteDatabase db;

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = crosswordDb_.getWritableDatabase();
        int token = ContentDescriptor.URI_MATCHER.match(uri);
        switch(token){
            case ContentDescriptor.Crossword.PATH_TOKEN:{
                long id = db.insert(ContentDescriptor.Crossword.NAME, null, values);
                getContext().getContentResolver().notifyChange(uri, null);
                return ContentDescriptor.Crossword.CONTENT_URI.buildUpon().appendPath(String.valueOf(id)).build();
            }
            case ContentDescriptor.Blocks.PATH_TOKEN:{
                long id = db.insert(ContentDescriptor.Blocks.NAME, null, values);
                getContext().getContentResolver().notifyChange(uri, null);
                return ContentDescriptor.Blocks.CONTENT_URI.buildUpon().appendPath(String.valueOf(id)).build();
            }
            case ContentDescriptor.Words.PATH_TOKEN:{
                long id = db.insert(ContentDescriptor.Words.NAME, null, values);
                getContext().getContentResolver().notifyChange(uri, null);
                return ContentDescriptor.Words.CONTENT_URI.buildUpon().appendPath(String.valueOf(id)).build();
            }
            
            default: {
                throw new UnsupportedOperationException("URI: " + uri + " not supported.");
            }
        }
	}

	@Override
	public boolean onCreate() {
		crosswordDb_ = new MainDatabaseHelper(getContext());
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
		      String[] selectionArgs, String sortOrder) {
		SQLiteDatabase db = crosswordDb_.getReadableDatabase();
        final int match = ContentDescriptor.URI_MATCHER.match(uri);
        switch(match){
            // retrieve restaurant list
            case ContentDescriptor.Crossword.PATH_TOKEN : {
                SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
                builder.setTables(ContentDescriptor.Crossword.NAME);
                return builder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
            }
            case ContentDescriptor.Crossword.PATH_FOR_ID_TOKEN : {
                SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
                builder.setTables(ContentDescriptor.Crossword.NAME);
                return builder.query(db, null, ContentDescriptor.Crossword.Cols.ID + " = " + uri.getPathSegments().get(1), null, null, null, null);
            }
            case ContentDescriptor.Blocks.PATH_TOKEN : {
                SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
                builder.setTables(ContentDescriptor.Blocks.NAME);
                return builder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
            }
            case ContentDescriptor.Words.PATH_TOKEN : {
                SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
                builder.setTables(ContentDescriptor.Words.NAME);
                return builder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
            }
            
            default: return null;
        }
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
		      String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

}
