package com.lyricat.crosswordtogo.screens;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ToggleButton;

import com.google.gson.Gson;
import com.lyricat.crosswordtogo.R;
import com.lyricat.crosswordtogo.model.Crossword;
import com.lyricat.crosswordtogo.model.Square;
import com.lyricat.crosswordtogo.utils.AppConstants;
import com.lyricat.crosswordtogo.utils.LogUtil;

public class EditCrosswordActivity extends Activity {

	String crosswordName_; 
	Crossword crossword_;
	private CrosswordAdapter crosswordAdapter_;

	// UI controls
	private EditText userWordInput_;
	private Spinner numberSpinner_;
	private Spinner directionSpinner_;
	private ToggleButton directionToggle_;
	private Button writeButton_;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_edit_crossword);
		crosswordName_ = getIntent().getStringExtra(AppConstants.CROSSWORD_NAME_EXTRA);

		Gson gson = new Gson();
		SharedPreferences settings = getSharedPreferences(AppConstants.CROSSWORDS_STORAGE, 0);
		String crosswordJson = settings.getString(crosswordName_, " ");
		crossword_ = gson.fromJson(crosswordJson, Crossword.class);

		if (!crossword_.squaresGenerated()) { 
			crossword_.generateSquareLists(); // maybe should assume this is done before EditCrosswordActivity
		}

		crosswordAdapter_ = new CrosswordAdapter(this, crossword_);

		// Set up (store) the user input EditText
		userWordInput_ = (EditText) findViewById(R.id.edit_answer);

		// Set up the word number spinner
		numberSpinner_ = (Spinner) findViewById(R.id.number_spinner);	
		List<CharSequence> wordNumbers = new ArrayList<CharSequence>();
		for (int i = 1; i <= crossword_.getMaxWordNumber(); i++) {
			wordNumbers.add(String.valueOf(i));
		}
		ArrayAdapter<CharSequence> numberSpinnerAdapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
		numberSpinnerAdapter.addAll(wordNumbers);
		numberSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		numberSpinner_.setAdapter(numberSpinnerAdapter);


		directionToggle_ = (ToggleButton) findViewById(R.id.across_down_toggle);	


		// Set up the direction spinner		
		/*
		directionSpinner_ = (Spinner) findViewById(R.id.across_down_spinner);
		ArrayAdapter<CharSequence> directionSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.across_down_array, android.R.layout.simple_spinner_item);
		directionSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		directionSpinner_.setAdapter(directionSpinnerAdapter);
		 */

		// Set up the Write button		
		writeButton_ = (Button) findViewById(R.id.answer_button);
		writeButton_.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// Perform action on click
				String inputedText = userWordInput_.getText().toString();
				String numberValue = numberSpinner_.getSelectedItem().toString();
				//String acrossValue = directionSpinner_.getSelectedItem().toString();
				int currentNumber = Integer.valueOf(numberValue);

				if (!directionToggle_.isChecked()) {
					try {
						crossword_.applyStringToAcrossWord(currentNumber, inputedText.toUpperCase());
						crosswordAdapter_.notifyDataSetChanged();
						userWordInput_.setText("");
					} catch (Exception e) {
						// TODO: Toast the error
					}
				}  else {
					try {
						crossword_.applyStringToDownWord(currentNumber, inputedText.toUpperCase());
						crosswordAdapter_.notifyDataSetChanged();
						userWordInput_.setText("");
					} catch (Exception e) {
						// TODO: Toast the error
					}
				}
			}
		});

		// Set up the crossword view
		GridView crosswordView = (GridView) findViewById(R.id.crossword_view);
		crosswordView.setNumColumns(crossword_.getNumCols());
		crosswordView.setAdapter(crosswordAdapter_);

		crosswordView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Square sq = crossword_.getSquareAt(position);
				int squareNumber = sq.getNumber();
				if (squareNumber != 0) {
					numberSpinner_.setSelection(squareNumber - 1);
				}
			}});
	}



	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		saveCrossword();
	}



	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		saveCrossword();
	}

	private void saveCrossword() { 
		SharedPreferences crosswordStorage = getSharedPreferences(AppConstants.CROSSWORDS_STORAGE, 0);
		SharedPreferences.Editor editor = crosswordStorage.edit();
		Gson gson = new Gson();
		editor.putString(crosswordName_, gson.toJson(crossword_));
		editor.commit();
	}
}

