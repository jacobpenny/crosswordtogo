package com.example.crosswordtogo.screens;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;

import com.example.crosswordtogo.R;

public class TemplateChooserActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_template_chooser);
		
	}

	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}
}
