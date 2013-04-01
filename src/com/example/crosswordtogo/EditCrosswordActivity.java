package com.example.crosswordtogo;

import android.app.Activity;
import android.content.ContentResolver;
import android.os.Bundle;

public class EditCrosswordActivity extends Activity {
	
	ContentResolver contentResolver_;
	private static CrosswordAdapter crosswordAdapter_;
	
	private final String crosswordName_; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_crossword);

		contentResolver_ = this.getContentResolver();

		crosswordName_ = getIntent().getStringExtra("crosswordName");

	}
	
}
