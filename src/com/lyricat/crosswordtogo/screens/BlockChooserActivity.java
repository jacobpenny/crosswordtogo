package com.lyricat.crosswordtogo.screens;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import com.google.gson.Gson;
import com.lyricat.crosswordtogo.R;
import com.lyricat.crosswordtogo.model.Crossword;
import com.lyricat.crosswordtogo.model.Square;
import com.lyricat.crosswordtogo.utils.AppConstants;

public class BlockChooserActivity extends Activity {

	private int numRows_;
	private int numCols_;
	private CrosswordAdapter crosswordAdapter_;
	private Crossword crossword_;
	final Context context_ = this;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_block_chooser);

		numRows_ = getIntent().getIntExtra("numRows", 5);
		numCols_ = getIntent().getIntExtra("numCols", 5);

		crossword_ = Crossword.createBlank(numRows_, numCols_);
		crosswordAdapter_ = new CrosswordAdapter(this, crossword_);

		
		GridView cwView_ = (GridView) findViewById(R.id.block_chooser_crossword);
		cwView_.setNumColumns(numCols_);
		cwView_.setAdapter(crosswordAdapter_);

		cwView_.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Square sq = crossword_.getSquareAt(position);
				sq.toggleBlockness();
				crosswordAdapter_.notifyDataSetChanged();
			}});


		Button doneButton = (Button) findViewById(R.id.done_button);
		doneButton.setOnClickListener( new OnClickListener() {
			@Override
			public void onClick(View v) {    
				AlertDialog.Builder alert = new AlertDialog.Builder(context_);
				alert.setTitle("Name your new crossword");
				final EditText input = new EditText(context_);
				alert.setView(input);

				alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int whichButton) {
						String name = input.getEditableText().toString();
						//commitCrossword(name);
						Intent i = new Intent(context_, EditCrosswordActivity.class);
						i.putExtra(AppConstants.CROSSWORD_NAME_EXTRA, name);
						Gson gson = new Gson();
						
						SharedPreferences crosswordStorage = getSharedPreferences(AppConstants.CROSSWORDS_STORAGE, 0);
						SharedPreferences.Editor editor = crosswordStorage.edit();
						editor.putString(name, gson.toJson(crossword_));
						editor.commit();

						((Activity) context_).startActivity(i);
						((Activity)context_).finish();
					}
				});
				alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int whichButton) {
						dialog.cancel();
					}
				}); 
				AlertDialog alertDialog = alert.create();
				alertDialog.show(); 
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