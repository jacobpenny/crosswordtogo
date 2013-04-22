package com.example.crosswordtogo.logic;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class MainDatabaseHelper extends SQLiteOpenHelper {
	private static final String SQL_CREATE_CROSSWORDS = "CREATE TABLE " +
		    ContentDescriptor.Crossword.NAME + " ( " +                           
		    ContentDescriptor.Crossword.Cols.ID +  " integer primary key autoincrement, " +
		    ContentDescriptor.Crossword.Cols.NAME + " text unique, " +
		    ContentDescriptor.Crossword.Cols.IS_TEMPLATE + " integer, " +
		    ContentDescriptor.Crossword.Cols.NUMROWS + " integer not null, " +
		    ContentDescriptor.Crossword.Cols.NUMCOLS + " integer not null)";
	
	private static final String SQL_CREATE_BLOCKINDICES = "CREATE TABLE " +
		    ContentDescriptor.Blocks.NAME + " ( " +                           
		    ContentDescriptor.Blocks.Cols.ID + " integer primary key autoincrement, " +
		    ContentDescriptor.Blocks.Cols.INDEX + " integer not null, " +
		    ContentDescriptor.Blocks.Cols.CROSSWORD_NAME + " text not null)";
	
	private static final String SQL_CREATE_WORDS = "CREATE TABLE " +
			ContentDescriptor.Words.NAME + " ( " +                           
			ContentDescriptor.Words.Cols.ID + " integer primary key autoincrement, " +
			ContentDescriptor.Words.Cols.ISACROSS + " integer not null, " +
			ContentDescriptor.Words.Cols.NUMBER + " integer not null, " +
			ContentDescriptor.Words.Cols.WORDTEXT + " text, " +
			ContentDescriptor.Words.Cols.CROSSWORD_NAME + " text not null)";
	
	private static final String DATABASE_NAME = "crosswords.db";
	private static final int DATABASE_VERSION = 1;

	MainDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, 1);
		Log.d("QUERY!!", SQL_CREATE_CROSSWORDS);
		Log.d("QUERY!!", SQL_CREATE_BLOCKINDICES);
		Log.d("QUERY!!", SQL_CREATE_WORDS);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d("Creating databases", "1");
		db.execSQL(SQL_CREATE_CROSSWORDS);
		db.execSQL(SQL_CREATE_BLOCKINDICES);
		db.execSQL(SQL_CREATE_WORDS);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}


}