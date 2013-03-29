package com.example.crosswordtogo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class BlockChooserActivity extends Activity implements View.OnClickListener {

	private int numRows_;
	private int numCols_;
	private static CrosswordAdapter crosswordAdapter_;
	private static Crossword crossword_;
	static boolean firstRun_ = true;

	@Override
	public void onClick(View view) {

	}
	@Override

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_block_chooser);		
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
		/*

		
		cwView_.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	        	Square sq = crossword_.getSquareAt(position);
	        	sq.toggleBlockness();
	        	crosswordAdapter_.notifyDataSetChanged();
	        }
	    }); */

	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}
}