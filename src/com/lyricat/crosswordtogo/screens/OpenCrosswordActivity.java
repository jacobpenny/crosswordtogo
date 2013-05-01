package com.lyricat.crosswordtogo.screens;

import java.util.ArrayList;
import java.util.Map;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.lyricat.crosswordtogo.R;
import com.lyricat.crosswordtogo.utils.AppConstants;
import com.google.gson.Gson;

public class OpenCrosswordActivity extends ListActivity {

	ArrayList<String> cwNames_;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		SharedPreferences cwStorage = getSharedPreferences(AppConstants.CROSSWORDS_STORAGE, 0);

		cwNames_ = new ArrayList<String>();
		Map<String,?> keys = cwStorage.getAll();
		for (Map.Entry<String,?> entry : keys.entrySet()) {
			cwNames_.add(entry.getKey());     
		}
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, cwNames_);
		setListAdapter(adapter);
	}


	@Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        String selectedName = cwNames_.get(position);
        
        Intent i = new Intent((Activity) OpenCrosswordActivity.this, EditCrosswordActivity.class);
		i.putExtra(AppConstants.CROSSWORD_NAME_EXTRA, selectedName);

		((Activity) OpenCrosswordActivity.this).startActivity(i);
		((Activity) OpenCrosswordActivity.this).finish();
        
    }



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

}
