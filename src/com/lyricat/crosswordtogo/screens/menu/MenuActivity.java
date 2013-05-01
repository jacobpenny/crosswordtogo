package com.lyricat.crosswordtogo.screens.menu;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

import com.lyricat.crosswordtogo.R;
import com.lyricat.crosswordtogo.screens.AppOptionsActivity;
import com.lyricat.crosswordtogo.screens.OpenCrosswordActivity;

public class MenuActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		
		findViewById(R.id.new_crossword).setOnClickListener(
				new OnClickListener() {
					public void onClick(View v) {
						// Create new fragment and transaction
						Fragment newCrosswordFragment = new NewCrosswordDialogFragment();
						FragmentTransaction transaction = getFragmentManager().beginTransaction();

						// Replace whatever is in the fragment_container view with this fragment,
						// and add the transaction to the back stack
						transaction.add(newCrosswordFragment, null);
						//transaction.replace(R.id.fragment_container, newFragment);
						//transaction.addToBackStack(null);

						// Commit the transaction
						transaction.commit();
						/*
						NewCrosswordDialogFragment dialog = new NewCrosswordDialogFragment();
						dialog.show(getFragmentManager(), null);
						*/
					}
				});

		findViewById(R.id.open_crossword).setOnClickListener(
				new OnClickListener() {
					public void onClick(View v) {
						Intent i = new Intent(MenuActivity.this, OpenCrosswordActivity.class);
						startActivity(i);
					}
				});
		
		findViewById(R.id.app_options).setOnClickListener(
				new OnClickListener() {
					public void onClick(View v) {
						Intent i = new Intent(MenuActivity.this, AppOptionsActivity.class);
						startActivity(i);
					}
				});
	}

	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

}
