package com.example.crosswordtogo.logic;

import android.content.UriMatcher;
import android.net.Uri;
import android.provider.BaseColumns;

public class ContentDescriptor {
	public static final String AUTHORITY = "com.example.crosswordtogo.providers.CrosswordProvider";
    private static final Uri BASE_URI = Uri.parse("content://" + AUTHORITY);
    public static final UriMatcher URI_MATCHER = buildUriMatcher();
 
    private ContentDescriptor(){};
 
    private static  UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = AUTHORITY;
 
        matcher.addURI(authority, Crossword.PATH, Crossword.PATH_TOKEN);
        matcher.addURI(authority, Crossword.PATH_FOR_ID, Crossword.PATH_FOR_ID_TOKEN);
        
        matcher.addURI(authority, Blocks.PATH, Blocks.PATH_TOKEN);
        
        matcher.addURI(authority, Words.PATH, Words.PATH_TOKEN);
 
        return matcher;
    }
 
    public static class Crossword {
        public static final String NAME = "crossword";
 
        public static final String PATH = "crosswords";
        public static final int PATH_TOKEN = 100;
        public static final String PATH_FOR_ID = "crosswords/*";
        public static final int PATH_FOR_ID_TOKEN = 200;
 
        public static final Uri CONTENT_URI = BASE_URI.buildUpon().appendPath(PATH).build();
 
        public static class Cols {
            public static final String ID = BaseColumns._ID; // convention
            public static final String NAME = "crossword_name";
            public static final String NUMROWS  = "crossword_numrows";
            public static final String NUMCOLS = "crossword_numcols";
            public static final String IS_TEMPLATE = "crossword_istemplate";
            
        }
 
    }
    
    public static class Blocks {
        public static final String NAME = "block";
 
        public static final String PATH = "blocks";
        public static final int PATH_TOKEN = 300;
        public static final String PATH_FOR_ID = "blocks/*";
        public static final int PATH_FOR_ID_TOKEN = 400;
 
        public static final Uri CONTENT_URI = BASE_URI.buildUpon().appendPath(PATH).build();
 
 
        public static class Cols {
            public static final String ID = BaseColumns._ID;
            public static final String INDEX = "block_index";
            public static final String CROSSWORD_NAME  = "block_crossword_name";
        }
 
    }
    
    public static class Words {
        public static final String NAME = "word";
 
        public static final String PATH = "words";
        public static final int PATH_TOKEN = 500;
        public static final String PATH_FOR_ID = "words/*";
        public static final int PATH_FOR_ID_TOKEN = 600;
 
        public static final Uri CONTENT_URI = BASE_URI.buildUpon().appendPath(PATH).build();
 
 
        public static class Cols {
            public static final String ID = BaseColumns._ID; 
            public static final String ISACROSS = "word_is_across"; // to be used as a boolean
            public static final String NUMBER  = "word_number";
            public static final String WORDTEXT  = "word_text";
            public static final String CROSSWORD_NAME  = "word_crossword_name";
        }
 
    }
}