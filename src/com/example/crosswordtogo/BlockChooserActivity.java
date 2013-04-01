package com.example.crosswordtogo;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

public class BlockChooserActivity extends Activity {

	ContentResolver contentResolver_;
	private int numRows_;
	private int numCols_;
	private static CrosswordAdapter crosswordAdapter_;
	private static Crossword crossword_;
	static boolean firstRun_ = true;
	final Context context_ = this;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_block_chooser);

		contentResolver_ = this.getContentResolver();

		numRows_ = getIntent().getIntExtra("numRows", 5);
		numCols_ = getIntent().getIntExtra("numCols", 5);

		if(firstRun_) {
			crossword_ = Crossword.createBlank(numRows_, numCols_);
			crosswordAdapter_ = new CrosswordAdapter(this, crossword_);
			Log.d("ONCREATE", "flag is true");
			firstRun_ = false;
		}

		GridView cwView_ = (GridView) findViewById(R.id.block_chooser_crossword);
		cwView_.setNumColumns(numCols_);
		cwView_.setAdapter(crosswordAdapter_);

		cwView_.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				Log.d("Click!", String.valueOf(view.getId()));
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
					public void onClick(DialogInterface dialog, int whichButton) {
						String name = input.getEditableText().toString();
						commitCrossword(name);
						Intent i = new Intent((Activity) context_, EditCrosswordActivity.class);
						
						//Spinner rowSpinner = (Spinner) getActivity().findViewById(R.id.dialog_dimensions_rows);
						i.putExtra("crosswordName", name);

						((Activity) context_).startActivity(i);
				               
					}
				});
				alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						dialog.cancel();
					}
				}); 
				AlertDialog alertDialog = alert.create();
				alertDialog.show();
        
			}  
		});
	}

	private void commitCrossword(String name) {
		ContentValues cwVals = new ContentValues();
		cwVals.put(ContentDescriptor.Crossword.Cols.NAME, name);
		cwVals.put(ContentDescriptor.Crossword.Cols.NUMROWS, numRows_);
		cwVals.put(ContentDescriptor.Crossword.Cols.NUMCOLS, numCols_);
		Uri cw = contentResolver_.insert(ContentDescriptor.Crossword.CONTENT_URI, cwVals);
		Log.d("New ID : ", cw.getPathSegments().get(1));

		List<Integer> blockList = crossword_.getBlockList();
		for (int i : blockList) {
			ContentValues blockVal = new ContentValues();
			blockVal.put(ContentDescriptor.Blocks.Cols.INDEX, i);
			blockVal.put(ContentDescriptor.Blocks.Cols.CROSSWORD_ID, cw.getPathSegments().get(1));
			contentResolver_.insert(ContentDescriptor.Blocks.CONTENT_URI, blockVal);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}
}